package com.wx.dao;

import com.wx.model.Weixin;

import java.util.List;

public interface WeixinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Weixin record);

    int insertSelective(Weixin record);

    Weixin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Weixin record);

    int updateByPrimaryKey(Weixin record);

    List<Weixin> getAll();

    List<Weixin> getWeixinByName(String fromUserName);

    int deleteByName(String fromUserName);
}