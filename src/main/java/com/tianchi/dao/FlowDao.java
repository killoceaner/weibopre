package com.tianchi.dao;

import com.tianchi.onex.RowMessage;
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
    @Insert("INSERT INTO `weibo_train_data` (uid,mid,time,content,forward_count,comment_count,like_count)  VALUES (#{uid},#{mid},${time},#{content},${forward_count},${comment_count},${like_count})")
    public int insertLog(@Param("uid")String uid,@Param("mid")String mid , @Param("time")String time , @Param("content") String content ,
                         @Param("forward_count") int forward_count ,@Param("comment_count")int comment_count,@Param("like_count")int like_count);

    @Insert("INSERT INTO `tags` (tag,count) VALUES(#{Tag},1)")
    public int insertTag(@Param("Tag") String tag);

//    @Select("SELECT content FROM ${SourceTable} limit #{BeginId}, #{EndId}")
//    public List<String> selData(@Param("SourceTable") String SourceTable , @Param("BeginId")int BeginId ,
//                                            @Param("EndId") int EndId);

    @Update("UPDATE `tags` SET count = ${count} WHERE tag = ${Tag}")
    public int updateTags(@Param("Tag") String tag , @Param("count") int count );

    @Insert("INSERT INTO `wordcount` (word,count) VALUES(#{word},#{count})")
    public int insertWord(@Param("word")String word , @Param("count") Integer count );

    //取对应uid的所有博文的weight
    @Select("SELECT * from `train` where uid = #{uid} ")
    public List<Map<String,Object>> selWeight(@Param("uid") String uid);

    //根据mid取三个字段
    @Select("SELECT * from `train` where mid = #{mid}")

    public Map<String,Object> selArgs (@Param("mid") String mid );

    //更新相应的字段
    @Update("Update `weibo_predict_dataset` forward = #{forward} , comment_count = #{comment} , like_count = #{like} where uid = #{uid}")
    public int updateTrain(@Param("forward") int forward , @Param("comment") int comment , @Param("like") int like , @Param("uid")String uid);

    //循环取train里的content字段
    @Select("SELECT * from `weibo_predict_data` limit #{Begin} , #{limition}")
    public List<Map<String,Object>> selData(@Param("Begin") int Begin , @Param("limition")int limition);

    @Select("SELECT uid , mid , forward_count,comment_count,like_count FROM `final_result` limit #{Begin} , #{limition}")
    public List<RowMessage> selFinalData(@Param("Begin") int Begin , @Param("limition") int limition);
}
