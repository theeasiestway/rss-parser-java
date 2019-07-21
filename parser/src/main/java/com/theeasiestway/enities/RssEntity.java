package com.theeasiestway.enities;

public class RssEntity {

    private String title;
    private String link;
    private String description;
    private String pubDate;

    public String getTitle() {
        return title;
    }

    public RssEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLink() {
        return link;
    }

    public RssEntity setLink(String link) {
        this.link = link;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RssEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPubDate() {
        return pubDate;
    }

    public RssEntity setPubDate(String pubDate) {
        this.pubDate = pubDate;
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "title: %s\n" +
                   "link: %s\n" +
                   "description: %s\n" +
                   "pubDate: %s\n",
                title,
                link,
                description,
                pubDate
        );
    }
}