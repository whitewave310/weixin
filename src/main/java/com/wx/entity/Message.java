package com.wx.entity;

import java.io.Serializable;

/**
 * 微信消息（服务端接收到的）
 * @author lizy
 */
public  class Message implements Serializable {

    public static final String TEXT = "text";

    public static final String LOCATION = "location";

    public static final String EVENT = "event";

    public  static final String IMAGE= "image";

    // 开发者微信号
    private String toUserName;
    // 发送方帐号（一个OpenID）  
    private String fromUserName;
    // 消息创建时间    
    private Integer createTime;
    // 消息类型（text/image/location/link）  
    private String msgType;
    // 消息id，64位整型  
    private String msgId;
    // 消息内容 (文本消息专有)
    private String content;

    //地理位置纬度 Location_X(地理位置专有)
    private Double locationX;
    //地理位置经度 Location_Y(地理位置专有)
    private Double locationY;
    // 地图缩放大小  (地理位置专有)
    private String scale;
    // 地理位置信息  (地理位置专有)
    private String label;

    private String event;

    private String picUrl;

    private String mediaId;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getToUserName() {
        return toUserName;
    }
    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
    public String getFromUserName() {
        return fromUserName;
    }
    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    public String getMsgId() {
        return msgId;
    }
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getLocationX() {
        return locationX;
    }

    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    public Double getLocationY() {
        return locationY;
    }

    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }
}
