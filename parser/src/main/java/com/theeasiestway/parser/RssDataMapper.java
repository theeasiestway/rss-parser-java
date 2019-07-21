package com.theeasiestway.parser;

import com.theeasiestway.enities.RssChannel;
import com.theeasiestway.enities.RssItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RssDataMapper {

    private static final String CHANNEL = "channel";
    private static final String ITEM = "item";

    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String DESCRIPTION = "description";
    private static final String PUB_DATE = "pubDate";

    private static final String LANGUAGE = "language";
    private static final String CATEGORY = "category";
    private static final String IMAGE = "image";
    private static final String LAST_BUILD = "lastBuildDate";
    private static final String TTL = "ttl";

    private static final String ENCLOSURE = "enclosure";
    private static final String URL = "url";
    private static final String LENGTH = "length";
    private static final String TYPE = "type";

    private static Replacer replacer;

    public static RssChannel map(BufferedInputStream inputStream) throws Exception {
        return map(inputStream, null, null);
    }

    public static RssChannel map(BufferedInputStream inputStream, Replacer replacer) throws Exception {
        return map(inputStream, replacer, null);
    }

    public static RssChannel map(BufferedInputStream inputStream, Callback onFinish) throws Exception {
        return map(inputStream, null, onFinish);
    }

    public static RssChannel map(BufferedInputStream inputStream, Replacer replacer, Callback onFinish) throws Exception {
        if (inputStream == null) return null;
        RssDataMapper.replacer = replacer;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element element = document.getDocumentElement();              // getting "rss" tag
            element.normalize();

            NodeList chNodeList = element.getElementsByTagName(CHANNEL);  // getting nodes of "channel" tag
            if (chNodeList.getLength() == 0) return null;

            Element channel = (Element) chNodeList.item(0);
            String chTitle = getTitle(channel);
            String chLink = getLink(channel);
            String chDescription = getDescription(channel);
            String chPubDate = getPubDate(channel);
            String chLanguage = getChannelLanguage(channel);
            String chCategory = getChannelCategory(channel);
            String chImageLink = getChannelImageLink(channel);
            String chLastBuild = getChannelLastBuildDate(channel);
            int chTTL = getChannelTTL(channel);

            RssChannel rssChannel = new RssChannel()
                    .setTitle(chTitle)
                    .setLink(chLink)
                    .setDescription(chDescription)
                    .setPubDate(chPubDate)
                    .setLanguage(chLanguage)
                    .setCategory(chCategory)
                    .setImageLink(chImageLink)
                    .setLastBuildDate(chLastBuild)
                    .setTtl(chTTL);

            NodeList itemsNodeList = element.getElementsByTagName(ITEM);
            int itemsCount = itemsNodeList.getLength();
            if (itemsCount == 0) return rssChannel;

            for (int i = 0; i < itemsCount; i++) {
                Element item = (Element) itemsNodeList.item(i);
                String itemTitle = getTitle(item);
                String itemLink = getLink(item);
                String itemDescription = getDescription(item);
                String itemPubDate = getPubDate(item);
                RssItem.Enclosure itemEnclosure = getItemEnclosure(item);

                RssItem rssItem = new RssItem()
                        .setTitle(itemTitle)
                        .setLink(itemLink)
                        .setDescription(itemDescription)
                        .setPubDate(itemPubDate)
                        .setEnclosure(itemEnclosure);

                rssChannel.addItem(rssItem);
            }
            return rssChannel;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try { inputStream.close(); } catch (IOException e) { e.printStackTrace(); }
            RssDataMapper.replacer = null;
            if (onFinish != null) onFinish.callback();
        }
    }

    public static class Replacer {

        private String regexp;
        private String replacement;

        public Replacer(String regexp, String replacement) {
            this.regexp = regexp;
            this.replacement = replacement;
        }
    }

    private static String getTitle(Element channel) {
        return getNodeValue(getElement(channel, TITLE));
    }

    private static String getLink(Element channel) {
        return getNodeValue(getElement(channel, LINK));
    }

    private static String getDescription(Element channel) {
        return getNodeValue(getElement(channel, DESCRIPTION));
    }

    private static String getPubDate(Element channel) {
        return getNodeValue(getElement(channel, PUB_DATE));
    }

    private static String getChannelLanguage(Element channel) {
        return getNodeValue(getElement(channel, LANGUAGE));
    }

    private static String getChannelCategory(Element channel) {
        return getNodeValue(getElement(channel, CATEGORY));
    }

    private static String getChannelImageLink(Element channel) {
        return getNodeValue(getElement(channel, IMAGE));
    }

    private static String getChannelLastBuildDate(Element channel) {
        return getNodeValue(getElement(channel, LAST_BUILD));
    }

    private static int getChannelTTL(Element channel) {
        String ttlString = getNodeValue(getElement(channel, TTL));
        int ttl = -1;
        if (ttlString != null) {
            try {
                ttl = Integer.valueOf(ttlString);
            } catch (NumberFormatException e) { e.printStackTrace(); }
        }
        return ttl;
    }

    private static RssItem.Enclosure getItemEnclosure(Element item) {
        Element enclosure = getElement(item, ENCLOSURE);
        if (enclosure == null) return null;
        NamedNodeMap nodeMap = enclosure.getAttributes();
        RssItem.Enclosure enclosureClass = null;
        if (nodeMap.getLength() > 2) {
            String url = nodeMap.getNamedItem(URL).getNodeValue();

            long length = -1;
            try {
                length = Long.parseLong(nodeMap.getNamedItem(LENGTH).getNodeValue());
            } catch (NumberFormatException e) { e.printStackTrace(); }

            String mimeType = nodeMap.getNamedItem(TYPE).getNodeValue();

            enclosureClass = new RssItem.Enclosure(url, length, mimeType);
        }
        return enclosureClass;
    }

    private static String getNodeValue(Element element) {
        if (element == null) return null;
        Node node = element.getFirstChild();
        if (node == null) return null;
        String nodeValue = node.getNodeValue();
        if (nodeValue != null && nodeValue.trim().length() == 0) nodeValue = null;
        if (replacer != null && nodeValue != null && nodeValue.trim().length() > 0) {
            nodeValue = nodeValue.trim().replaceAll(replacer.regexp, replacer.replacement);
        }
        return nodeValue;
    }

    private static Element getElement(Element element, String name) {
        NodeList nodeList = element.getElementsByTagName(name);
        if (nodeList.getLength() == 0) return null;
        return (Element) nodeList.item(0);
    }
}