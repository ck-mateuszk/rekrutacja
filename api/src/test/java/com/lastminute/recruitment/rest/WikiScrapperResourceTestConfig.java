package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.WikiScrapper;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WikiScrapperResourceTestConfig {
    @Bean
    public WikiScrapper wikiScrapper() {
        return Mockito.mock(WikiScrapper.class);
    }
}