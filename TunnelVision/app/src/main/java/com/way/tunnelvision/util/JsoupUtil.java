package com.way.tunnelvision.util;

import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.HeaderImageModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/3/24.
 */
public class JsoupUtil {
    private final static String TAG = JsoupUtil.class.getName();

    private JsoupUtil() {
        throw new AssertionError();
    }

    /**
     * 将XML文件转化成实体类
     * @param xml
     * @return
     */
    public static List<HeaderImageModel> getHeaderImageItems(String xml) {
        List<HeaderImageModel> itemsList = new ArrayList<HeaderImageModel>();

        if (!StringUtil.isBlank(xml) && !StringUtil.isEmpty(xml)) {
            LogUtil.d(TAG, "getHeaderImageItems debug, XML != NULL");
            try {
                org.jsoup.nodes.Document document = Jsoup.parse(xml,"",new Parser(new XmlTreeBuilder()));
                if (document != null) {
                    LogUtil.d(TAG, "getHeaderImageItems debug, Document != NULL");
                    Elements items = document.select("item");
                    if (items != null && items.size() > 0) {
                        LogUtil.d(TAG, "getHeaderImageItems debug, Elements != NULL, COUNT = " + items.size());
                        for (Element item : items) {
                            String title = item.select(Constants.RSSFEED.HEADER_IMAGE_TAG_TITLE).text();
                            String link = item.select(Constants.RSSFEED.HEADER_IMAGE_TAG_LINK).text();
                            String description = item.select(Constants.RSSFEED.HEADER_IMAGE_TAG_DESCRIPTION).text();
                            String url = item.select(Constants.RSSFEED.HEADER_IMAGE_TAG_ENCLOSURE).attr(Constants.RSSFEED.HEADER_IMAGE_TAG_ENCLOSURE_URL);
                            String lengthStr = item.select(Constants.RSSFEED.HEADER_IMAGE_TAG_ENCLOSURE).attr(Constants.RSSFEED.HEADER_IMAGE_TAG_ENCLOSURE_LENGTH);
                            String type = item.select(Constants.RSSFEED.HEADER_IMAGE_TAG_ENCLOSURE).attr(Constants.RSSFEED.HEADER_IMAGE_TAG_ENCLOSURE_TYPE);
                            String guid = item.select(Constants.RSSFEED.HEADER_IMAGE_TAG_GUID).text();
                            String pubDate = item.select(Constants.RSSFEED.HEADER_IMAGE_TAG_PUBDATE).text();
                            Long length = 0L;
                            if (!StringUtil.isBlank(lengthStr) && !StringUtil.isEmpty(lengthStr)) {
                                length = Long.parseLong(lengthStr);
                            }
                            //LogUtil.d(TAG, "getHeaderImageItems debug, url = " + url);
                            //LogUtil.d(TAG, "getHeaderImageItems debug, length = " + lengthStr);
                            //LogUtil.d(TAG, "getHeaderImageItems debug, type = " + type);
                            HeaderImageModel model = new HeaderImageModel(null, title, link, description, url, length, type, guid, pubDate, 2);
                            itemsList.add(model);
                        }
                    }
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "getHeaderImageItems error", e);
                e.printStackTrace();
            }
        }

        // return item list
        return itemsList;
    }


}
