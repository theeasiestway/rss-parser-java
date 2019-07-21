package com.theeasiestway.parser;

import com.theeasiestway.enities.RssChannel;
import com.theeasiestway.net.NetworkService;

public class RssParser {

    private NetworkService networkService = new NetworkService();

    public RssChannel parse(String url) throws Exception {
        return RssDataMapper.map(networkService.request(url), () -> networkService.disconnect());
    }

    public RssChannel parse(String url, String regexp, String replacement) throws Exception {
        return RssDataMapper.map(networkService.request(url), new RssDataMapper.Replacer(regexp, replacement), () -> networkService.disconnect());
    }

    public RssParser setConnTimeout(int timeout) {
        networkService.setConnTimeout(timeout);
        return this;
    }

    public RssParser setReadTimeout(int timeout) {
        networkService.setReadTimeout(timeout);
        return this;
    }
}