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


    public static List<HeaderImageModel> getHeaderImageItems2(String xml) {
        List<HeaderImageModel> itemsList = new ArrayList<HeaderImageModel>();

        if (!StringUtil.isBlank(xml) && !StringUtil.isEmpty(xml)) {
            LogUtil.d(TAG, "getHeaderImageItems2 debug, XML != NULL");
            try {
                org.jsoup.nodes.Document document = Jsoup.parse(xml,"",new Parser(new XmlTreeBuilder()));
                if (document != null) {
                    LogUtil.d(TAG, "getHeaderImageItems2 debug, Document != NULL");
                    Elements items = document.select("item");
                    if (items != null && items.size() > 0) {
                        LogUtil.d(TAG, "getHeaderImageItems2 debug, Elements != NULL, COUNT = " + items.size());
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
                            //LogUtil.d(TAG, "getHeaderImageItems2 debug, url = " + url);
                            //LogUtil.d(TAG, "getHeaderImageItems2 debug, length = " + lengthStr);
                            //LogUtil.d(TAG, "getHeaderImageItems2 debug, type = " + type);
                            HeaderImageModel model = new HeaderImageModel(null, title, link, description, url, length, type, guid, pubDate);
                            itemsList.add(model);
                        }
                    }
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "getHeaderImageItems2 error", e);
                e.printStackTrace();
            }
        }

        // return item list
        return itemsList;
    }

    /*
    public static List<HeaderImageModel> getHeaderImageItems(String rss_feed_xml) {
        List<HeaderImageModel> itemsList = new ArrayList<HeaderImageModel>();

        // check if RSS XML fetched or not
        if (rss_feed_xml != null) {
            // successfully fetched rss xml
            // parse the xml
            try {
                Document doc = getDomElement(rss_feed_xml);
                NodeList nodeList = doc.getElementsByTagName("item");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element item = (Element) nodeList.item(i);
                    String title = getValue(item, Constants.RSSFEED.HEADER_IMAGE_TAG_TITLE);

                    HeaderImageModel model = new HeaderImageModel(null, "", "", "", "", null, "", "", "");
                    itemsList.add(model);
                }
            } catch (Exception e) {
                // Check log for errors
                e.printStackTrace();
            }
        }

        // return item list
        return itemsList;
    }
    */

    /**
     * Getting XML DOM element
     *
     * @param xml string
     */
    /*
    private static Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = (Document) db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }

        return doc;
    }
    */

    /**
     * Getting node value
     *
     * @param elem element
     */
    /*
    private static String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child
                        .getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE || (child.getNodeType() == Node.CDATA_SECTION_NODE)) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
    */

    /**
     * Getting node value
     *
     * @param item node
     * @param str  string
     */
    /*
    private static String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return getElementValue(n.item(0));
    }
    */
}
