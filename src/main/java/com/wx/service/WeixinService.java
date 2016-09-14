package com.wx.service;

import com.wx.model.Weixin;

import java.util.List;

/**
 * Created by lijing on 2016/9/8.
 */
public interface WeixinService {
    boolean saveContent(String fromUserName,String content);
    List<Weixin> showWeixin();
    List<Weixin> showWeixinByName(String fromUserName);
}
