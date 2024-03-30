package com.jony.utils;

public class RedisThumbKeyUtil {
    /**
     * 保存用户点赞数据的key
     *
     * @date 2021/9/26 14:44
     */
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
    /**
     * 保存用户被点赞数量的key
     *
     * @date 2021/9/26 14:44
     */
    public static final String MAP_KEY_USER_LIKED_COUNT = "MAP_USER_LIKED_COUNT";

    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 222222::333333
     *
     * @param contentId 被点赞内容的id
     * @param userId 点赞的人id
     * @return
     */
    public static String getLikedKey(Long contentId, Long userId) {
        return contentId + "::" + userId;
    }
}