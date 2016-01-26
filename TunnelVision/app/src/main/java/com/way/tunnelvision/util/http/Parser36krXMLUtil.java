package com.way.tunnelvision.util.http;

import android.util.Log;

import com.rsslibj.elements.Channel;
import com.rsslibj.elements.Item;
import com.rsslibj.elements.RSSReader;
import com.way.tunnelvision.model.FeedEntity.Feed36kr;
import com.way.tunnelvision.model.FeedEntity.Feed36krItem;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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

    public static Feed36kr parseXMLWithRSSLibJ(String xmlData){
        Feed36kr feed36kr = null;
        List<Feed36krItem> feed36krItems = null;
        try{
            StringReader stringReader = new StringReader(xmlData);
            RSSReader rssReader = new RSSReader();
            rssReader.setReader(stringReader);
            Channel channel = rssReader.getChannel();
            if(null != channel) {
                feed36kr = new Feed36kr();
                feed36kr.setTitle(channel.getTitle());
                Log.d(TAG, "parseXMLWithRSSLibJ debug, Title = " + channel.getTitle());
                feed36kr.setDescription(channel.getDescription());
                Log.d(TAG, "parseXMLWithRSSLibJ debug, Description = " + channel.getDescription());
                feed36kr.setLink(channel.getLink());
                Log.d(TAG, "parseXMLWithRSSLibJ debug, Link = " + channel.getLink());
                feed36kr.setGenerator(channel.getDcSource());
                Log.d(TAG, "parseXMLWithRSSLibJ debug, Source = " + channel.getDcSource());

                List<Item> itemList = channel.getItems();
                if (itemList != null && itemList.size() > 0) {
                    feed36krItems = new ArrayList<Feed36krItem>();
                    for (Item item : itemList) {
                        Feed36krItem feed36krItem = new Feed36krItem();
                        feed36krItem.setTitle(item.getTitle());
                        feed36krItem.setLink(item.getLink());
                        feed36krItem.setDescription(item.getDescription());
                        //feed36krItem.setPubDate(item.);
                        //String id = MD5Util.encodeMD5(item.getDescription());
//                    String link = item.getLink();
//                    String title = item.getTitle();
//                    String description = item.getDescription();
//                    Document document = new Document(source, id, link, title, description);
//                    morphia.save(document);

                        feed36krItems.add(feed36krItem);
                    }
                    feed36kr.setFeed36krItems(feed36krItems);
                }
            }
        }catch (Exception e){
            Log.e(TAG, "parseXMLWithRSSLibJ error", e);
            e.printStackTrace();
        }
        return feed36kr;
    }
}
