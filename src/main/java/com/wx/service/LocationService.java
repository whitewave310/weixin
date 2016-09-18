package com.wx.service;

import com.wx.model.Location;

/**
 * Created by lijing on 2016/9/18.
 */
public interface LocationService {
    boolean updateLocation(Double x,Double y);
    Location getLocation();
}
