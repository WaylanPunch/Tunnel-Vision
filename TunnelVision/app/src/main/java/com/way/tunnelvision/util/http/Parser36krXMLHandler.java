package com.way.tunnelvision.util.http;

import com.way.tunnelvision.model.FeedEntity.Feed36kr;
import com.way.tunnelvision.model.FeedEntity.Feed36krItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/1/19.
 */
public class Parser36krXMLHandler extends DefaultHandler {

    private boolean isParentTitle = true;
    private Feed36kr feed36kr = null;
    private List<Feed36krItem> feed36krItems = null;
    private Feed36krItem feed36krItem = null;

    private String nodeName;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        feed36kr = new Feed36kr();
        feed36krItems = new ArrayList<Feed36krItem>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        nodeName = localName;
        if (isParentTitle) {
            if (localName.equals("item")) {
                isParentTitle = false;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (isParentTitle) {
            if (nodeName.equals("title")) {
                feed36kr.setTitle(ch.toString());
            } else if (nodeName.equals("description")) {
                feed36kr.setTitle(ch.toString());
            } else if (nodeName.equals("link")) {
                feed36kr.setTitle(ch.toString());
            } else if (nodeName.equals("generator")) {
                feed36kr.setTitle(ch.toString());
            }
        } else {
            if (nodeName.equals("item")){
                feed36krItem = new Feed36krItem();
            }
            if (nodeName.equals("title")) {
                feed36krItem.setTitle(ch.toString());
            } else if (nodeName.equals("link")) {
                feed36krItem.setTitle(ch.toString());
            } else if (nodeName.equals("description")) {
                feed36krItem.setTitle(ch.toString());
            } else if (nodeName.equals("pubDate")) {
                feed36krItem.setTitle(ch.toString());
            } else if (nodeName.equals("source")) {
                feed36krItem.setTitle(ch.toString());
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (!isParentTitle) {
            if (nodeName.equals("item")){
                feed36krItems.add(feed36krItem);
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        feed36kr.setFeed36krItems(feed36krItems);
    }
}
