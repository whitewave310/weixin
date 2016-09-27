package com.wx.util;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wx.entity.Message;
import com.wx.entity.Reply;
import com.wx.model.Location;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信相关工具类
 */
public class WeixinUtil {

    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;
                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
    /**
	 * sha1加密算法
	 */
	public static String sha1(String key) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(key.getBytes());
			String pwd = new BigInteger(1, md.digest()).toString(16);
			return pwd;
		} catch (Exception e) {
			e.printStackTrace();
			return key;
		}
	}

    /**
     * 解析request中的xml 并将数据存储到一个Map中返回
     * @param request
     */
    public static Map<String, String> parseXml(HttpServletRequest request){
        Map<String, String> map = new HashMap<String, String>();
        try {
            InputStream inputStream = request.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            List<Element> elementList = root.elements();
            for (Element e : elementList)
                //遍历xml将数据写入map
                map.put(e.getName(), e.getText());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 存储数据的Map转换为对应的Message对象
     * @param map 存储数据的map
     * @return 返回对应Message对象
     */
    public static  Message  mapToMessage(Map<String,String> map){
        if(map == null) return null;
        String msgType = map.get("MsgType");
        Message message = new Message();
        message.setToUserName(map.get("ToUserName"));
        message.setFromUserName(map.get("FromUserName"));
        message.setCreateTime(Integer.parseInt(map.get("CreateTime")));
        message.setMsgType(msgType);
        message.setMsgId(map.get("MsgId"));
        if(msgType.equals(Message.TEXT)){
            message.setContent(map.get("Content"));
        }
        if(msgType.equals(Message.LOCATION)){
            message.setLocationX(Double.parseDouble(map.get("Location_X")));
            message.setLocationY(Double.parseDouble(map.get("Location_Y")));
            message.setScale(map.get("Scale"));
            message.setLabel(map.get("Label"));
        }
        if (msgType.equals(Message.EVENT)){
            message.setEvent(map.get("Event"));
        }
        if (msgType.equals(Message.IMAGE)){
            message.setPicUrl(map.get("PicUrl"));
            message.setMediaId(map.get("MediaId"));
        }
        return message;
    }

    /**
     * 将回复消息对象转换成xml字符串
     * @param reply 回复消息对象
     * @return 返回符合微信接口的xml字符串
     */
    public static String replyToXml(Reply reply){
        xstream.autodetectAnnotations(true);
        xstream.alias("xml", reply.getClass());
        return xstream.toXML(reply);
    }

//    根据经度纬度算两点距离
    public static String wgs84PointsDistance(Location myLocation,Location userLocation){
        Integer r = 6378137;
        double x1 = myLocation.getLon() * Math.PI / 180;
        double x2 = userLocation.getLon() * Math.PI / 180;
        double y1 = myLocation.getLat() * Math.PI / 180;
        double y2 = userLocation.getLat() * Math.PI / 180;
        double dx = Math.abs(x1 - x2);
        double dy = Math.abs(y1 - y2);
        double p = Math.pow(Math.sin(dx / 2), 2) + Math.cos(x1) * Math.cos(x2) * Math.pow(Math.sin(dy / 2), 2);
        double d= r * 2 * Math.asin(Math.sqrt(p));
        DecimalFormat df=new DecimalFormat("0.00");
        String s=df.format(d/1000);
        return s;

    }
    //    准确性高的根据经度纬度算两点距离
    public static String wgs84Distance(Location myLocation,Location userLocation){
        double lon1 = myLocation.getLon();
        double lat1 = myLocation.getLat();
        double lon2 = userLocation.getLon();
        double lat2 = userLocation.getLat();
        int a = 6378137;
        double b = 6356752.3142;
        double f = 1 / 298.257223563;
        double L=Math.toRadians(lon2 - lon1);
        double U1 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat1)));
        double U2 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat2)));
        double sinU1 = Math.sin(U1);
        double cosU1 = Math.cos(U1);
        double sinU2 = Math.sin(U2);
        double cosU2 = Math.cos(U2);
        double lambda = L;
        double lambdaP;
        int iterLimit = 100;
        double cosSqAlpha;
        double sinSigma;
        double cos2SigmaM;
        double cosSigma;
        double sigma;
        do {
            double sinLambda = Math.sin(lambda);
            double cosLambda = Math.cos(lambda);
            sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda) + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
            if(sinSigma == 0)
                return "";
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);
            double sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
            cosSqAlpha = 1 - sinAlpha * sinAlpha;
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
            if (Double.isNaN(cos2SigmaM)){
                cos2SigmaM = 0;
            }
            double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
            lambdaP = lambda;
            lambda = L + (1 - C) * f * sinAlpha * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
        } while (Math.abs(lambda-lambdaP) > (1e-12) && --iterLimit>0);
        double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
        double d= b * A * (sigma - deltaSigma);
        DecimalFormat df=new DecimalFormat("0.00");
        String s=df.format(d/1000);
        return s;
    }
}
