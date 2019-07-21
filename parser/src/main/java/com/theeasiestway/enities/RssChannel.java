package com.theeasiestway.enities;

import java.util.ArrayList;

public class RssChannel extends RssEntity {

    private String language;
    private String category;
    private String imageLink;
    private String lastBuildDate;
    private int ttl = -1;
    private ArrayList<RssItem> items = new ArrayList<>();

    public String getLanguage() {
        return language;
    }

    public RssChannel setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public RssChannel setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getImageLink() {
        return imageLink;
    }

    public RssChannel setImageLink(String imageLink) {
        this.imageLink = imageLink;
        return this;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public RssChannel setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
        return this;
    }

    public int getTtl() {
        return ttl;
    }

    public RssChannel setTtl(int ttl) {
        this.ttl = ttl;
        return this;
    }

    public RssChannel addItem(RssItem item) {
        if (item != null) items.add(item);
        return this;
    }

    public ArrayList<RssItem> getItems() {
        return items;
    }

    public RssChannel setItems(ArrayList<RssItem> items) {
        this.items = items;
        return this;
    }

    @Override
    public RssChannel setTitle(String title) {
        return (RssChannel) super.setTitle(title);
    }

    @Override
    public RssChannel setDescription(String description) {
        return (RssChannel) super.setDescription(description);
    }

    @Override
    public RssChannel setLink(String link) {
        return (RssChannel) super.setLink(link);
    }

    @Override
    public RssChannel setPubDate(String pubDate) {
        return (RssChannel) super.setPubDate(pubDate);
    }

    @Override
    public String toString() {
        return "RssChannel:\n" +
                super.toString() +
                String.format(
                        "language: %s\n" +
                        "category: %s\n" +
                        "imageLink: %s\n" +
                        "lastBuildDate: %s\n" +
                        "ttl: %s\n\n",
                        language,
                        category,
                        imageLink,
                        lastBuildDate,
                        ttl
                ) +
                toStringItems() + "\n";
    }

    public String toStringItems() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RssItems count: ").append(items.size()).append("\n\n");
        if (items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                stringBuilder.append("item[").append(i).append("]:\n");
                stringBuilder.append(items.get(i).toString()).append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
