package com.lastminute.recruitment.client;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JsonWikiClient {

    public String readJson(String link) throws IOException {
        String name = link.replace("\"", "")
                .replace("http://wikiscrapper.test/", "/wikiscrapper/") + ".json";
        InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream(name));
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }
}
