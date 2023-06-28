package com.lastminute.recruitment;

import com.lastminute.recruitment.client.HtmlWikiClient;
import com.lastminute.recruitment.client.JsonWikiClient;
import com.lastminute.recruitment.domain.WikiPageRepository;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.persistence.InMemoryWikiPageRepository;
import com.lastminute.recruitment.reader.HtmlWikiReader;
import com.lastminute.recruitment.reader.JsonWikiReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class WikiScrapperConfiguration {

    @Bean
    @Profile("!json")
    public WikiReader htmlWikiReader() {
        return new HtmlWikiReader(new HtmlWikiClient());
    }

    @Bean
    @Profile("json")
    public WikiReader jsonWikiReader() {
        return new JsonWikiReader(new JsonWikiClient());
    }

    @Bean
    public WikiPageRepository wikiPageRepository() {
        return new InMemoryWikiPageRepository();
    }

    @Bean
    public WikiScrapper wikiScrapper(WikiPageRepository wikiPageRepository, WikiReader wikiReader) {
        return new WikiScrapper(wikiReader, wikiPageRepository);
    }
}
