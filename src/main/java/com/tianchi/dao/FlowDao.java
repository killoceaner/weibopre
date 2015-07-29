package com.tianchi.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * Created by houxiang on 15/7/28.
 */
public interface FlowDao {
    @Insert("INSERT INTO `Tianchi` (uid,mid,time,content,forward_count,comment_count,like_count)  VALUES (#{uid},#{mid},${time},#{content},${forward_count},${comment_count},${like_count})")
    public int insertLog(@Param("uid")String uid,@Param("mid")String mid , @Param("time")String time , @Param("content") String content ,
                         @Param("forward_count") int forward_count ,@Param("comment_count")int comment_count,@Param("like_count")int like_count);
}
