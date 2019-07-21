package com.theeasiestway.enities;

public class RssItem extends RssEntity {

    private Enclosure enclosure;

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public RssItem setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
        return this;
    }

    @Override
    public RssItem setTitle(String title) {
        return (RssItem) super.setTitle(title);
    }

    @Override
    public RssItem setDescription(String description) {
        return (RssItem) super.setDescription(description);
    }

    @Override
    public RssItem setLink(String link) {
        return (RssItem) super.setLink(link);
    }

    @Override
    public RssItem setPubDate(String pubDate) {
        return (RssItem) super.setPubDate(pubDate);
    }

    public static class Enclosure {

        private String url;
        private long length = -1;
        private String mimeType;

        public Enclosure() {}

        public Enclosure(String url, long length, String mimeType) {
            this.url = url;
            this.length = length;
            this.mimeType = mimeType;
        }

        public String getUrl() {
            return url;
        }

        public Enclosure setUrl(String url) {
            this.url = url;
            return this;
        }

        public long getLength() {
            return length;
        }

        public Enclosure setLength(long length) {
            this.length = length;
            return this;
        }

        public String getMimeType() {
            return mimeType;
        }

        public Enclosure setMimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        @Override
        public String toString() {
            return String.format(
                    "url: %s\n" +
                       "length: %s\n" +
                       "mimeType: %s",
                    url,
                    length,
                    mimeType
            );
        }
    }

    @Override
    public String toString() {
        return "RssItem:\n" +
                super.toString() +
                String.format("enclosure: %s\n", (enclosure == null ? null : enclosure.toString()));
    }
}