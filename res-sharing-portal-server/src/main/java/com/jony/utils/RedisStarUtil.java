package com.jony.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yitter.idgen.YitIdHelper;
import com.jony.convert.UserOperateConvert;
import com.jony.dto.UserOperateCountDTO;
import com.jony.dto.UserStarDTO;
import com.jony.entity.UserStar;
import com.jony.enums.ThumbOrStarStatusEum;
import com.jony.mapper.UserStarMapper;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RedisStarUtil {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private UserStarMapper userStarMapper;

    public void star(Long contentId, Long userId) {
        String likedKey = RedisThumbOrStarKeyUtil.getLikedKey(contentId, userId);
        redisTemplate.opsForHash().increment(RedisThumbOrStarKeyUtil.MAP_KEY_USER_STAR_COUNT, contentId.toString(), 1);
        redisTemplate.opsForHash().put(RedisThumbOrStarKeyUtil.MAP_KEY_USER_STAR, likedKey, ThumbOrStarStatusEum.STAR.getCode());
    }


    public void unStar(Long contentId, Long userId) {
        String likedKey = RedisThumbOrStarKeyUtil.getLikedKey(contentId, userId);
        redisTemplate.opsForHash().increment(RedisThumbOrStarKeyUtil.MAP_KEY_USER_STAR_COUNT, contentId.toString(), -1);
        redisTemplate.opsForHash().put(RedisThumbOrStarKeyUtil.MAP_KEY_USER_STAR, likedKey, ThumbOrStarStatusEum.UN_STAR.getCode());
    }


    public ThumbOrStarStatusEum starStatus(UserStarDTO userStarDTO) {

        Long contentId = userStarDTO.getContentId();
        Long userId = userStarDTO.getUserId();

        // 1、先走redis
        if (redisTemplate.opsForHash().hasKey(RedisThumbOrStarKeyUtil.MAP_KEY_USER_STAR, RedisThumbOrStarKeyUtil.getLikedKey(contentId, userId))) {
            String o = redisTemplate.opsForHash().get(RedisThumbOrStarKeyUtil.MAP_KEY_USER_STAR, RedisThumbOrStarKeyUtil.getLikedKey(contentId, userId)).toString();
            if ("1".equals(o)) {
                unStar(contentId, userId);
                return ThumbOrStarStatusEum.UN_STAR;
            }
            if ("0".equals(o)) {
                star(contentId, userId);
                return ThumbOrStarStatusEum.STAR;
            }
        }
        // 2、缓存没有则走mysql
        LambdaQueryWrapper<UserStar> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserStar::getContentId, contentId);
        wrapper.eq(UserStar::getUserId, userId);
        UserStar star = userStarMapper.selectOne(wrapper);
        if (star == null) {

            // 生成点赞记录
            long newId = YitIdHelper.nextId();
            UserStar userStar = UserOperateConvert.INSTANCE.toUserStar(userStarDTO);
            userStar.setId(newId);
            userStar.setStatus(ThumbOrStarStatusEum.STAR.getCode());
            userStarMapper.insert(userStar);
            star(contentId, userId);
            return ThumbOrStarStatusEum.STAR;
        }
        if (star.getStatus() == 1) {
            unStar(contentId, userId);
            star.setStatus(ThumbOrStarStatusEum.UN_STAR.getCode());
            userStarMapper.updateById(star);
            return ThumbOrStarStatusEum.UN_STAR;
        }

        if (star.getStatus() == 0) {
            star(contentId, userId);
            star.setStatus(ThumbOrStarStatusEum.STAR.getCode());
            userStarMapper.updateById(star);
            return ThumbOrStarStatusEum.STAR;
        }
        return ThumbOrStarStatusEum.ERROR;
    }


    /**
     * feat： 【用于数据持久化】从redis里获取点赞记录
     *
     * @return List
     */
    public List<UserStarDTO> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> scan = redisTemplate.opsForHash().scan(RedisThumbOrStarKeyUtil.MAP_KEY_USER_STAR, ScanOptions.NONE);
        List<UserStarDTO> list = new ArrayList<>();
        while (scan.hasNext()) {
            Map.Entry<Object, Object> entry = scan.next();
            String key = (String) entry.getKey();
            String[] split = key.split("::");
            Long infoId = Long.getLong(split[0]);
            Long likeUserId = Long.getLong(split[1]);
            Integer value = (Integer) entry.getValue();
            // 组装成 UserLike 对象
            UserStarDTO userStarDTO = new UserStarDTO(infoId, likeUserId, null, value);
            list.add(userStarDTO);
            // 存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisThumbOrStarKeyUtil.MAP_KEY_USER_STAR, key);
        }
        return list;
    }


    /**
     * feat： 【用于数据持久化】从redis里获取不同内容的点赞数
     *
     * @return List
     */
    public List<UserOperateCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisThumbOrStarKeyUtil.MAP_KEY_USER_STAR_COUNT, ScanOptions.NONE);
        List<UserOperateCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            Long key = (Long) map.getKey();
            Integer value = (Integer) map.getValue();
            UserOperateCountDTO userOperateCountDTO = new UserOperateCountDTO(key, value);
            list.add(userOperateCountDTO);
            redisTemplate.opsForHash().delete(RedisThumbOrStarKeyUtil.MAP_KEY_USER_STAR_COUNT, key);
        }
        return list;
    }


}