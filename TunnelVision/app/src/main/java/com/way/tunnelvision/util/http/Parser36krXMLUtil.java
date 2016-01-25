package com.way.tunnelvision.util.http;

import android.util.Log;

import com.way.tunnelvision.model.FeedEntity.Feed36kr;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by pc on 2016/1/20.
 */
public class Parser36krXMLUtil {
    private final static String TAG = Parser36krXMLUtil.class.getName();

    public static Feed36kr parseXMLWithSAX(String xmlData) {
        Feed36kr feed36kr = null;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            Parser36krXMLHandler handler = new Parser36krXMLHandler();
            // 将ContentHandler的实例设置到XMLReader中
            xmlReader.setContentHandler(handler);
            // 开始执行解析
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
            //xmlReader.get
            feed36kr = handler.getFeed36kr();
        } catch (Exception e) {
            Log.e(TAG, "parseXMLWithSAX error", e);
            e.printStackTrace();
        }
        return feed36kr;
    }
}
