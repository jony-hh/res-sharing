package com.jony.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import com.jony.convert.UserOperateConvert;
import com.jony.dto.UserThumbCountDTO;
import com.jony.dto.UserThumbDTO;
import com.jony.entity.UserThumb;
import com.jony.enums.LikedStatusEum;
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
    private RedisTemplate redisTemplate;
    @Resource
    private UserThumbMapper userThumbMapper;

    public void likes(Long contentId, Long userId) {
        String likedKey = RedisThumbKeyUtil.getLikedKey(contentId, userId);
        redisTemplate.opsForHash().increment(RedisThumbKeyUtil.MAP_KEY_USER_LIKED_COUNT, contentId.toString(), 1);
        redisTemplate.opsForHash().put(RedisThumbKeyUtil.MAP_KEY_USER_LIKED, likedKey, LikedStatusEum.LIKE.getCode());
    }


    public void unLikes(Long contentId, Long userId) {
        String likedKey = RedisThumbKeyUtil.getLikedKey(contentId, userId);
        redisTemplate.opsForHash().increment(RedisThumbKeyUtil.MAP_KEY_USER_LIKED_COUNT, contentId.toString(), -1);
        redisTemplate.opsForHash().put(RedisThumbKeyUtil.MAP_KEY_USER_LIKED, likedKey,LikedStatusEum.UNLIKE.getCode());
    }


    public LikedStatusEum likeStatus(UserThumbDTO userThumbDTO) {

        Long contentId = userThumbDTO.getContentId();
        Long userId = userThumbDTO.getUserId();

        // 1、先走redis
        if (redisTemplate.opsForHash().hasKey(RedisThumbKeyUtil.MAP_KEY_USER_LIKED, RedisThumbKeyUtil.getLikedKey(contentId, userId))) {
            String o = redisTemplate.opsForHash().get(RedisThumbKeyUtil.MAP_KEY_USER_LIKED, RedisThumbKeyUtil.getLikedKey(contentId, userId)).toString();
            if ("1".equals(o)) {
                unLikes(contentId, userId);
                return LikedStatusEum.UNLIKE;
            }
            if ("0".equals(o)) {
                likes(contentId, userId);
                return LikedStatusEum.LIKE;
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
            userThumb.setStatus(LikedStatusEum.LIKE.getCode());
            userThumbMapper.insert(userThumb);
            likes(contentId, userId);
            return LikedStatusEum.LIKE;
        }
        if (userLikes.getStatus() == 1) {
            unLikes(contentId, userId);
            userLikes.setStatus(LikedStatusEum.UNLIKE.getCode());
            userThumbMapper.updateById(userLikes);
            return LikedStatusEum.UNLIKE;
        }

        if (userLikes.getStatus() == 0) {
            likes(contentId, userId);
            userLikes.setStatus(LikedStatusEum.LIKE.getCode());
            userThumbMapper.updateById(userLikes);
            return LikedStatusEum.LIKE;
        }
        return LikedStatusEum.ERROR;
    }


    /**
     * feat： 【用于数据持久化】从redis里获取点赞记录
     *
     * @return List
     */
    public List<UserThumbDTO> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> scan = redisTemplate.opsForHash().scan(RedisThumbKeyUtil.MAP_KEY_USER_LIKED, ScanOptions.NONE);
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
            redisTemplate.opsForHash().delete(RedisThumbKeyUtil.MAP_KEY_USER_LIKED, key);
        }
        return list;
    }


    /**
     * feat： 【用于数据持久化】从redis里获取不同内容的点赞数
     *
     * @return List
     */
    public List<UserThumbCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisThumbKeyUtil.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        List<UserThumbCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            Long key = (Long) map.getKey();
            Integer value = (Integer) map.getValue();
            UserThumbCountDTO userThumbCountDTO = new UserThumbCountDTO(key, value);
            list.add(userThumbCountDTO);
            redisTemplate.opsForHash().delete(RedisThumbKeyUtil.MAP_KEY_USER_LIKED_COUNT, key);
        }
        return list;
    }


}