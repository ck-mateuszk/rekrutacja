package com.lastminute.recruitment.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Profile("json")
@RequiredArgsConstructor
public class WikiReaderJson implements WikiReader {

    private final ObjectMapper mapper;
    @Override
    public WikiPage read(String link) {
        try {
            return mapper.readValue(new JsonWikiClient().readJson(link), WikiPage.class);
        } catch (IOException | NullPointerException e) {
            throw new WikiPageNotFound();
        }
    }
}
