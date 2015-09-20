package com.tianchi.onex;

import java.util.Date;

/**
 * Created by houxiang on 15/7/28.
 */
public class RowMessage {
    private String uid;
    private String mid;

    private int forward_count;
    private int comment_count;
    private int like_count;

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

    public String toString(){
        return uid+"\t"+mid+"\t"+this.forward_count+"\t"+this.comment_count+"\t"+this.like_count+"\n";
    }
}
