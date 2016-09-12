package com.wx.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信消息（服务端接收到的）
 * @author lizy
 */
public  class Message implements Serializable {

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
}
