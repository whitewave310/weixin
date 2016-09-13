package com.wx.controller;

import com.wx.entity.Message;
import com.wx.entity.Reply;
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
import java.util.Map;

/**
 * Created by lijing on 2016/9/8.
 */
@RestController
public class WeixinController {
    private static final String TOKEN = "whitewave310";

    @Autowired
    private WeixinService weixinService;


    /**
     * ����token����signature��֤�Ƿ�Ϊ΢�ŷ���˷��͵���Ϣ
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
                reply.setContent(message.getContent());
            }
            if (message.getMsgType().equals(message.LOCATION)){
                String l="你离我有"+wgs84PointsDistance(message.getLocationX(),message.getLocationY())+"米";
                reply.setContent(l);
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

    public static int wgs84PointsDistance(Double y,Double x){
        Integer r = 6378137;
        double x1 = 112.556602 * Math.PI / 180;
        double x2 = x * Math.PI / 180;
        double y1 = 37.864891 * Math.PI / 180;
        double y2 = y * Math.PI / 180;
        double dx = Math.abs(x1 - x2);
        double dy = Math.abs(y1 - y2);
        double p = Math.pow(Math.sin(dx / 2), 2) + Math.cos(x1) * Math.cos(x2) * Math.pow(Math.sin(dy / 2), 2);
        double d= r * 2 * Math.asin(Math.sqrt(p));
        Integer i=(int)d;
        return i;

    }
}
