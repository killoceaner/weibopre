package com.tianchi.onex;

import java.util.List;

/**
 * Created by houxiang on 15/7/29.
 */
public class Record {
    private int id;
    private String uid ;
    private String mid ;
    private String content;
    private int forward_count;
    private int comment_count;
    private int like_count;
    private String tags;
    private double weight ;

    public Record(){

    }
    public Record( String uid, String mid , String content ){

        this.uid = uid ;
        this.mid = mid;
        this.content = content ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getForward_count() {
        return forward_count;
    }

    public void setForward_count(int forward_count) {
        this.forward_count = forward_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return this.uid+"\t"+this.mid+"\t"+forward_count+","+comment_count+","+like_count+"\n";
    }

//    public  void setArgs(int forward_count , int comment_count , int like_count){
//        this.forward_count = forward_count;
//        this.like_count = like_count ;
//        this.comment_count = comment_count ;
//    }

    public void setArgs(List<Integer> args){
        this.forward_count = args.get(0);
        this.comment_count = args.get(1);
        this.like_count = args.get(2);
    }
}
