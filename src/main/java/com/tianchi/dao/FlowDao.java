package com.tianchi.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.*;
import java.util.Objects;

/**
 * Created by houxiang on 15/7/28.
 */
public interface FlowDao {
    @Insert("INSERT INTO `Tianchi` (uid,mid,time,content,forward_count,comment_count,like_count)  VALUES (#{uid},#{mid},${time},#{content},${forward_count},${comment_count},${like_count})")
    public int insertLog(@Param("uid")String uid,@Param("mid")String mid , @Param("time")String time , @Param("content") String content ,
                         @Param("forward_count") int forward_count ,@Param("comment_count")int comment_count,@Param("like_count")int like_count);

    @Insert("INSERT INTO `tags` (tag,count) VALUES(#{Tag},1)")
    public int insertTag(@Param("Tag") String tag);

    @Select("SELECT content FROM ${SourceTable} limit #{BeginId}, #{EndId}")
    public List<String> selData(@Param("SourceTable") String SourceTable , @Param("BeginId")int BeginId ,
                                            @Param("EndId") int EndId);

    @Update("UPDATE `tags` SET count = ${count} WHERE tag = ${Tag}")
    public int updateTags(@Param("Tag") String tag , @Param("count") int count );

    @Insert("INSERT INTO `wordcount` (word,count) VALUES(#{word},#{count})")
    public int insertWord(@Param("word")String word , @Param("count") Integer count );

}
