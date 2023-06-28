package com.lastminute.recruitment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonWikiPage {

    private final String title;
    private final String content;
    private final String selfLink;
    private final List<String> links;

    public JsonWikiPage(@JsonProperty("title") String title, @JsonProperty("content") String content, @JsonProperty("selfLink") String selfLink, @JsonProperty("links") List<String> links) {
        this.title = title;
        this.content = content;
        this.selfLink = selfLink;
        this.links = links;
    }

    public List<String> getLinks() {
        return links;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
