package com.lastminute.recruitment.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.client.JsonWikiClient;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;

import java.io.File;
import java.io.IOException;

public class JsonWikiReader implements WikiReader {
    private final JsonWikiClient jsonWikiClient;

    public JsonWikiReader(JsonWikiClient jsonWikiClient) {
        this.jsonWikiClient = jsonWikiClient;
    }

    @Override
    public WikiPage read(String link) {
        String fileName = jsonWikiClient.readJson(link);

        if (fileName == null) throw new WikiPageNotFound();
        ObjectMapper objectMapper = new ObjectMapper();
        WikiPage wikiPage;
        try {
            wikiPage = objectMapper.readValue(new File(fileName), WikiPage.class);
        } catch (IOException e) {
            throw new WikiPageNotFound();
        }

        return wikiPage;
    }
}
