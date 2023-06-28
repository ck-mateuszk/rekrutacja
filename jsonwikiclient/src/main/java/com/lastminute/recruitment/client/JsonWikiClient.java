package com.lastminute.recruitment.client;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;

import java.net.URL;
import java.util.Optional;

public class JsonWikiClient {

    public String readJson(String link) {
        String name = link.replace("\"", "")
                .replace("http://wikiscrapper.test/", "/wikiscrapper/") + ".json";
        return Optional.ofNullable(getClass().getResource(name)).map(URL::getFile).orElseThrow(WikiPageNotFound::new);
    }
}
