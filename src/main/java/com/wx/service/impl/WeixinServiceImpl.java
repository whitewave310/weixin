package com.wx.service.impl;

import com.wx.dao.WeixinMapper;
import com.wx.model.Weixin;
import com.wx.service.WeixinService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lijing on 2016/9/8.
 */
@Service("weixinService")
@Transactional
public class WeixinServiceImpl implements WeixinService{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private <T> T getMapper(Class<T> clazz) {
        return this.sqlSessionTemplate.getMapper(clazz);
    }

    public boolean saveContent(String fromUserName,String content) {
        boolean result=false;
        Weixin weixin=new Weixin();
        weixin.setContent(content);
        weixin.setFromusername(fromUserName);
        if(getMapper(WeixinMapper.class).insert(weixin)>0){
            result=true;
        }
        return result;
    }
}
