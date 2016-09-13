package com.wx.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信消息（服务端接收到的）
 * @author lizy
 */
public  class Message implements Serializable {

    public static final String TEXT = "text";

    public static final String EVENT = "event";

    public static final String LOCATION = "location";

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

    //事件类型，subscribe(订阅)、unsubscribe(取消订阅)、CLICK(自定义菜单点击事件) （事件推送专有）
    private String event;
    //地理位置纬度
    private Double latitude;
    //地理位置经度
    private Double longitude;
    //地理位置精度
    private Double precision;



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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }
}
