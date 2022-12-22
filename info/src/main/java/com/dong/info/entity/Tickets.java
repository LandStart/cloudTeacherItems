package com.dong.info.entity;

import java.util.List;

public class Tickets {
    private List<String> urls;
    private Integer size;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
