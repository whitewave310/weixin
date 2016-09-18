package com.wx.service.impl;

import com.wx.dao.LocationMapper;
import com.wx.model.Location;
import com.wx.service.LocationService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lijing on 2016/9/18.
 */
@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService{
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private <T> T getMapper(Class<T> clazz) {
        return this.sqlSessionTemplate.getMapper(clazz);
    }

    public boolean updateLocation(Double x, Double y) {
        boolean result=false;
        Location location=new Location();
        location.setId(1);
        location.setLon(x);
        location.setLat(y);
        if(getMapper(LocationMapper.class).updateByPrimaryKey(location)>0){
            result=true;
        }
        return result;
    }

    public Location getLocation() {
        return getMapper(LocationMapper.class).selectByPrimaryKey(1);
    }
}
