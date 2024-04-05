package com.jony.utils;

public class RedisThumbOrStarKeyUtil {

    /**
     * 保存不同用户点赞数据的key
     */
    public static final String MAP_KEY_USER_THUMB = "MAP_KEY_USER_THUMB";

    /**
     * 保存不同内容被点赞总数的key
     */
    public static final String MAP_KEY_USER_THUMB_COUNT = "MAP_KEY_USER_THUMB_COUNT";

    /**
     * 保存不同内容被点赞总数的key【总数】
     */
    public static final String MAP_KEY_THUMB_COUNT = "MAP_KEY_THUMB_COUNT";

    /**
     * 保存不同用户收藏数据的key
     */
    public static final String MAP_KEY_USER_STAR = "MAP_KEY_USER_STAR";

    /**
     * 保存不同内容被收藏总数的key
     */
    public static final String MAP_KEY_USER_STAR_COUNT = "MAP_KEY_USER_STAR_COUNT";

    /**
     * 拼接被点赞的内容id和点赞的人的id作为key。
     *
     * @param contentId 被点赞内容的id
     * @param userId 点赞的人id
     * @return
     */
    public static String getLikedKey(Long contentId, Long userId) {
        return contentId + "::" + userId;
    }
}