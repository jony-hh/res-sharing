package com.jony.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import com.jony.convert.UserOperateConvert;
import com.jony.dto.UserThumbDTO;
import com.jony.entity.UserThumb;
import com.jony.enums.ThumbOrStarStatusEum;
import com.jony.mapper.UserThumbMapper;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RedisThumbUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserThumbMapper userThumbMapper;

    public void likes(Long contentId, Long userId) {
        String likedKey = RedisThumbOrStarKeyUtil.getLikedKey(contentId, userId);
        redisTemplate.opsForHash().increment(RedisThumbOrStarKeyUtil.MAP_KEY_USER_THUMB_COUNT, contentId.toString(), 1);
        redisTemplate.opsForHash().put(RedisThumbOrStarKeyUtil.MAP_KEY_USER_THUMB, likedKey, ThumbOrStarStatusEum.THUMB.getCode());
    }


    public void unLikes(Long contentId, Long userId) {
        String likedKey = RedisThumbOrStarKeyUtil.getLikedKey(contentId, userId);
        redisTemplate.opsForHash().increment(RedisThumbOrStarKeyUtil.MAP_KEY_USER_THUMB_COUNT, contentId.toString(), -1);
        redisTemplate.opsForHash().put(RedisThumbOrStarKeyUtil.MAP_KEY_USER_THUMB, likedKey,ThumbOrStarStatusEum.UN_THUMB.getCode());
    }


    public ThumbOrStarStatusEum likeStatus(UserThumbDTO userThumbDTO) {

        Long contentId = userThumbDTO.getContentId();
        Long userId = userThumbDTO.getUserId();

        // 1、先走redis
        if (redisTemplate.opsForHash().hasKey(RedisThumbOrStarKeyUtil.MAP_KEY_USER_THUMB, RedisThumbOrStarKeyUtil.getLikedKey(contentId, userId))) {
            String o = redisTemplate.opsForHash().get(RedisThumbOrStarKeyUtil.MAP_KEY_USER_THUMB, RedisThumbOrStarKeyUtil.getLikedKey(contentId, userId)).toString();
            if ("1".equals(o)) {
                unLikes(contentId, userId);
                return ThumbOrStarStatusEum.UN_THUMB;
            }
            if ("0".equals(o)) {
                likes(contentId, userId);
                return ThumbOrStarStatusEum.THUMB;
            }
        }
        // 2、缓存没有则走mysql
        LambdaQueryWrapper<UserThumb> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserThumb::getContentId, contentId);
        wrapper.eq(UserThumb::getUserId, userId);
        UserThumb userLikes = userThumbMapper.selectOne(wrapper);
        if (userLikes == null) {

            // 生成点赞记录
            long newId = YitIdHelper.nextId();
            UserThumb userThumb = UserOperateConvert.INSTANCE.toUserThumb(userThumbDTO);
            userThumb.setId(newId);
            userThumb.setStatus(ThumbOrStarStatusEum.THUMB.getCode());
            userThumbMapper.insert(userThumb);
            likes(contentId, userId);
            return ThumbOrStarStatusEum.THUMB;
        }
        if (userLikes.getStatus() == 1) {
            unLikes(contentId, userId);
            userLikes.setStatus(ThumbOrStarStatusEum.UN_THUMB.getCode());
            userThumbMapper.updateById(userLikes);
            return ThumbOrStarStatusEum.UN_THUMB;
        }

        if (userLikes.getStatus() == 0) {
            likes(contentId, userId);
            userLikes.setStatus(ThumbOrStarStatusEum.THUMB.getCode());
            userThumbMapper.updateById(userLikes);
            return ThumbOrStarStatusEum.THUMB;
        }
        return ThumbOrStarStatusEum.ERROR;
    }


    /**
     * feat： 【用于数据持久化】从redis里获取点赞记录
     *
     * @return List
     */
    public List<UserThumbDTO> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> scan = redisTemplate.opsForHash().scan(RedisThumbOrStarKeyUtil.MAP_KEY_USER_THUMB, ScanOptions.NONE);
        List<UserThumbDTO> list = new ArrayList<>();
        while (scan.hasNext()) {
            Map.Entry<Object, Object> entry = scan.next();
            String key = (String) entry.getKey();
            String[] split = key.split("::");
            Long infoId = Long.getLong(split[0]);
            Long likeUserId = Long.getLong(split[1]);
            Integer value = (Integer) entry.getValue();
            // 组装成 UserLike 对象
            UserThumbDTO userLikeDetail = new UserThumbDTO(infoId, likeUserId, null, value);
            list.add(userLikeDetail);
            // 存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisThumbOrStarKeyUtil.MAP_KEY_USER_THUMB, key);
        }
        return list;
    }


    /**
     * feat： 【用于数据持久化】从redis里获取不同内容的点赞数
     *
     * @return List
     */
    public Integer getLikedCountFromRedis(String id) {
        Object original = redisUtil.hGet(RedisThumbOrStarKeyUtil.MAP_KEY_THUMB_COUNT, id);
        Object variational = redisUtil.hGet(RedisThumbOrStarKeyUtil.MAP_KEY_USER_THUMB_COUNT, id);
        Integer count = (Integer) original + (Integer) variational;
        return count;
    }


}