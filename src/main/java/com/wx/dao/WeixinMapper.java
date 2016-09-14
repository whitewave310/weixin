package com.wx.dao;

import com.wx.model.Weixin;

public interface WeixinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Weixin record);

    int insertSelective(Weixin record);

    Weixin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Weixin record);

    int updateByPrimaryKey(Weixin record);
}