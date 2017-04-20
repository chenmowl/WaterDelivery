package com.eme.waterdelivery.tools;

import android.text.TextUtils;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by dijiaoliang on 17/4/20.
 */

public class XmlUtil {

    public static Map<String,Set<String>> handleXml(String xml) {
        // 由android.util.Xml创建一个XmlPullParser实例
        XmlPullParser xpp = Xml.newPullParser();
        Map<String,Set<String>> map=new HashMap<>();
        try{
            //设置xmlpullparser的一些参数
//            xmlPullParser.setInput(new StringReader(xml));
            xpp.setInput(ConvertUtils.string2InputStream(xml, "UTF-8"), "UTF-8");
            //获取pull解析器对应事件类型
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tag = xpp.getName();
                if (eventType == XmlPullParser.START_TAG) {
                    int i = xpp.next();
                    if (i == XmlPullParser.TEXT) {
                        if (!TextUtils.isEmpty(xpp.getText()) && !xpp.getText().startsWith("\n")&& !xpp.getText().startsWith("\r")) {
                            Set<String> set = null;
                            String text = xpp.getText();
                            if(map.containsKey(tag)){
                                set = map.get(tag);
                                set.add(text);
                            }else{
                                set = new HashSet<>();
                                set.add(text);
                                map.put(tag, set);
                            }
                        }
                    }
                }
                eventType = xpp.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
