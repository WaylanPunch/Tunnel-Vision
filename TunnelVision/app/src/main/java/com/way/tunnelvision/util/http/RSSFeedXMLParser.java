package com.way.tunnelvision.util.http;

import android.util.Log;

import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.model.feedentity.RSSFeed;
import com.way.tunnelvision.model.feedentity.RSSFeedItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/1/20.
 */
public class RSSFeedXMLParser {
    private final static String TAG = RSSFeedXMLParser.class.getName();

    private RSSFeedXMLParser() {
        throw new AssertionError();
    }

//    private static String RSS_TITLE = "rss channel title";
//    private static String RSS_DESCRIPTION = "rss channel description";
//    private static String RSS_LINK = "rss channel link";
//    private static String RSS_GENERATOR = "rss channel generator";
//    private static String MEDIA_CONTENT = "media|content";
//    private static String IMAGE = "image";
//    private static String CATEGORY = "category";
//    private static String PUB_DATE = "pubDate";
//    private static String URL = "url";

    public static RSSFeed parseXMLWithJSoup(String xmlData) {
        RSSFeed rssFeed = null;
        try {
            Document doc = Jsoup.parse(xmlData, "", Parser.xmlParser());
            if (null != doc) {
                rssFeed = new RSSFeed();
                String rssTitleText = "";
                if(!doc.select(Constants.RSS.RSS_CHANNEL_TITLE).isEmpty()) {
                    rssTitleText = doc.select(Constants.RSS.RSS_CHANNEL_TITLE).first().text();
                }
                rssFeed.setTitle(rssTitleText);
                Log.d(TAG, "parseXMLWithJSoup debug, Channel Title = " + rssTitleText);
                String rssDescriptionText = "";
                if(!doc.select(Constants.RSS.RSS_CHANNEL_DESCRIPTION).isEmpty()) {
                    rssDescriptionText = doc.select(Constants.RSS.RSS_CHANNEL_DESCRIPTION).first().text();
                }
                rssFeed.setDescription(rssDescriptionText);
                Log.d(TAG, "parseXMLWithJSoup debug, Channel Description = " + rssDescriptionText);
                String rssLinkText = "";
                if(!doc.select(Constants.RSS.RSS_CHANNEL_LINK).isEmpty()) {
                    rssLinkText = doc.select(Constants.RSS.RSS_CHANNEL_LINK).first().text();
                }
                rssFeed.setLink(rssLinkText);
                Log.d(TAG, "parseXMLWithJSoup debug, Channel Link = " + rssLinkText);
                String rssGeneratorText = "";
                if(!doc.select(Constants.RSS.RSS_CHANNEL_GENERATOR).isEmpty()) {
                    rssGeneratorText = doc.select(Constants.RSS.RSS_CHANNEL_GENERATOR).first().text();
                }
                rssFeed.setGenerator(rssGeneratorText);
                Log.d(TAG, "parseXMLWithJSoup debug, Channel Generator = " + rssGeneratorText);
                String rssPubDateText = "";
                if(!doc.select(Constants.RSS.RSS_CHANNEL_PUBDATE).isEmpty()) {
                    rssPubDateText = doc.select(Constants.RSS.RSS_CHANNEL_PUBDATE).first().text();
                }
                rssFeed.setPubDate(rssPubDateText);
                Log.d(TAG, "parseXMLWithJSoup debug, Channel PubDate = " + rssPubDateText);
                String rssLanguageText = "";
                if(!doc.select(Constants.RSS.RSS_CHANNEL_LANGUAGE).isEmpty()) {
                    rssLanguageText = doc.select(Constants.RSS.RSS_CHANNEL_LANGUAGE).first().text();
                }
                rssFeed.setLanguage(rssLanguageText);
                Log.d(TAG, "parseXMLWithJSoup debug, Channel Language = " + rssLanguageText);

                Elements items = doc.select(Constants.RSS.RSS_ITEM);
                if (null != items && items.size() > 0) {
                    Log.d(TAG, "parseXMLWithJSoup debug, Items Count = " + items.size());
                    List<RSSFeedItem> rssFeedItems = new ArrayList<RSSFeedItem>();
                    for(Element item : items){
                        RSSFeedItem rssFeedItem = new RSSFeedItem();
                        String itemTitleText = "";
                        if (!item.select(Constants.RSS.RSS_ITEM_TITLE).isEmpty()) {
                            itemTitleText = item.select(Constants.RSS.RSS_ITEM_TITLE).first().text();
                        }
                        rssFeedItem.setTitle(itemTitleText);
                        String itemLinkText = "";
                        if (!item.select(Constants.RSS.RSS_ITEM_LINK).isEmpty()) {
                            itemLinkText = item.select(Constants.RSS.RSS_ITEM_LINK).first().text();
                        }
                        rssFeedItem.setLink(itemLinkText);
                        String itemDescriptionText = "";
                        if (!item.select(Constants.RSS.RSS_ITEM_DESCRIPTION).isEmpty()) {
                            itemDescriptionText = item.select(Constants.RSS.RSS_ITEM_DESCRIPTION).first().text();
                        }
                        rssFeedItem.setDescription(itemDescriptionText);
                        String itemPubDateText = "";
                        if (!item.select(Constants.RSS.RSS_ITEM_PUBDATE).isEmpty()) {
                            itemPubDateText = item.select(Constants.RSS.RSS_ITEM_PUBDATE).first().text();
                        }
                        rssFeedItem.setPubDate(itemPubDateText);
                        String itemSourceText = "";
                        if (!item.select(Constants.RSS.RSS_ITEM_SOURCE).isEmpty()) {
                            itemSourceText = item.select(Constants.RSS.RSS_ITEM_SOURCE).first().text();
                        }
                        rssFeedItem.setSource(itemSourceText);
                        String itemCategoryText = "";
                        if (!item.select(Constants.RSS.RSS_ITEM_CATEGORY).isEmpty()) {
                            itemCategoryText = item.select(Constants.RSS.RSS_ITEM_CATEGORY).first().text();
                        }
                        rssFeedItem.setCategory(itemCategoryText);
                        String itemGuidText = "";
                        if (!item.select(Constants.RSS.RSS_ITEM_GUID).isEmpty()) {
                            itemGuidText = item.select(Constants.RSS.RSS_ITEM_GUID).first().text();
                        }
                        rssFeedItem.setGuid(itemGuidText);
                        String itemAuthorText = "";
                        if (!item.select(Constants.RSS.RSS_ITEM_AUTHOR).isEmpty()) {
                            itemAuthorText = item.select(Constants.RSS.RSS_ITEM_AUTHOR).first().text();
                        }
                        rssFeedItem.setAuthor(itemAuthorText);
                        String itemLink_3gText = "";
                        if (!item.select(Constants.RSS.RSS_ITEM_LINK_3G).isEmpty()) {
                            itemLink_3gText = item.select(Constants.RSS.RSS_ITEM_LINK_3G).first().text();
                        }
                        rssFeedItem.setLink_3g(itemLink_3gText);
                        rssFeedItems.add(rssFeedItem);
                    }
                    rssFeed.setRSSFeedItems(rssFeedItems);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "parseXMLWithJSoup error", e);
            e.printStackTrace();
        }
        return rssFeed;
    }
}
