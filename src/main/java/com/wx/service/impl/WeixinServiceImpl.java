package com.wx.service.impl;

import com.wx.dao.WeixinMapper;
import com.wx.model.Weixin;
import com.wx.service.WeixinService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Weixin> showWeixin() {
        List<Weixin> weixinList=getMapper(WeixinMapper.class).getAll();
        return weixinList;
    }

    public List<Weixin> showWeixinByName(String fromUserName) {
        List<Weixin> weixinList=getMapper(WeixinMapper.class).getWeixinByName(fromUserName);
        return weixinList;
    }

    public boolean deleteWeixinByName(String fromUserName) {
        boolean result=false;
        if(getMapper(WeixinMapper.class).deleteByName(fromUserName)>0){
            result=true;
        }
        return result;
    }
}
