package com.wx.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 回复用户的消息
 * @author lizy
 */
public class Reply implements Serializable {

	
	// 开发者微信号  
	@XStreamAlias("ToUserName")
    private String toUserName;
    // 发送方帐号（一个OpenID）  
	@XStreamAlias("FromUserName")
    private String fromUserName;
    // 消息创建时间 
	@XStreamAlias("CreateTime")
    private Date createTime;
    // 消息类型（text/music/news）
	@XStreamAlias("MsgType")
    private String msgType;
    
    //回复的消息内容,长度不超过2048字节 (文本消息专有)
	@XStreamAlias("Content")
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
