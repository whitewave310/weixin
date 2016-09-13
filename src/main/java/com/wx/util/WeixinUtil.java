package com.wx.util;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wx.entity.Message;
import com.wx.entity.Reply;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.math.BigInteger;
import java.security.MessageDigest;
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
        if(msgType.equals(Message.EVENT)){
            message.setEvent(map.get("Event"));
            message.setLatitude( Double.parseDouble(map.get("Latitude")));
            message.setLongitude(Double.parseDouble(map.get("Longitude")));
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

}
