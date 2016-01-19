package com.way.tunnelvision.util.http;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by pc on 2016/1/20.
 */
public class Parser36krXMLUtil {
    public void parseXMLWithSAX(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            Parser36krXMLHandler handler = new Parser36krXMLHandler();
            // 将ContentHandler的实例设置到XMLReader中
            xmlReader.setContentHandler(handler);
            // 开始执行解析
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
            //xmlReader.get
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
