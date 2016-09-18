package com.wx.controller;

import com.wx.entity.Message;
import com.wx.entity.Reply;
import com.wx.model.Location;
import com.wx.model.Weixin;
import com.wx.service.LocationService;
import com.wx.service.WeixinService;
import com.wx.util.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lijing on 2016/9/8.
 */
@RestController
public class WeixinController {
    private static final String TOKEN = "whitewave310";

    @Autowired
    private WeixinService weixinService;

    @Autowired
    private LocationService locationService;


    /**
     * TOKEN验证
     */
    @RequestMapping(value = "wx", method = RequestMethod.GET)
    public String checkSignature(HttpServletRequest request){
        String result="error";
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (signature != null && timestamp != null && nonce != null&&echostr!=null ) {
            String[] strSet = new String[] { TOKEN, timestamp, nonce };
            java.util.Arrays.sort(strSet);
            String key = "";
            for (String string : strSet) {
                key = key + string;
            }
            String pwd = WeixinUtil.sha1(key);
            if(pwd.equals(signature)){
                result=echostr;
            }
        }
        return result;
    }

    @RequestMapping(value = "wx",method = RequestMethod.POST)
    public String replyMessage(HttpServletRequest request){
        String result="error";
        Map<String, String> requestMap = WeixinUtil.parseXml(request);
        Message message = WeixinUtil.mapToMessage(requestMap);
        if (message!=null){
            Reply reply=new Reply();
            if (message.getMsgType().equals(message.TEXT)){
                if (message.getContent().equals("数据库")){
                    List<Weixin> weixinList=weixinService.showWeixin();
                    String replyContent=" ";
                    for (Weixin weixin:weixinList){
                        replyContent=replyContent+weixin.getContent()+" ";
                    }
                    reply.setContent(replyContent);
                }else if(message.getContent().equals("我的数据库")){
                    List<Weixin> weixinList=weixinService.showWeixinByName(message.getFromUserName());
                    String replyContent=" ";
                    for (Weixin weixin:weixinList){
                        replyContent=replyContent+weixin.getContent()+" ";
                    }
                    reply.setContent(replyContent);
                } else if (message.getContent().equals("删除")){
                    weixinService.deleteWeixinByName(message.getFromUserName());
                    reply.setContent("删除成功");
                }else {
                    reply.setContent(message.getContent());
                    weixinService.saveContent(message.getFromUserName(),message.getContent());
                }
            }
            if (message.getMsgType().equals(message.LOCATION)){
                if(message.getFromUserName().equals("osCd_wGpK3zwitAsclc2LgGDjHq8")){
                    locationService.updateLocation(message.getLocationX(), message.getLocationY());
                    String l="经度:"+message.getLocationY() + "纬度:"+message.getLocationX();
                    reply.setContent(l);
                }else {
                    Location myLocation = locationService.getLocation();
                    Location userLocation = new Location();
                    userLocation.setLon(message.getLocationX());
                    userLocation.setLat(message.getLocationY());
                    String l = "你离我有" + WeixinUtil.wgs84PointsDistance(myLocation, userLocation) + "公里";
                    reply.setContent(l);
                }
            }
            reply.setToUserName(message.getFromUserName());
            reply.setFromUserName(message.getToUserName());
            reply.setCreateTime(new Date());
            reply.setMsgType("text");
            String back = WeixinUtil.replyToXml(reply);
            result=back;
        }
        return result;
    }
}
