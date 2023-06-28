package com.lastminute.recruitment.domain;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiScrapper {

    static final Logger LOG = LoggerFactory.getLogger(WikiScrapper.class);

    private final WikiReader wikiReader;
    private final WikiPageRepository repository;

    public WikiScrapper(WikiReader wikiReader, WikiPageRepository repository) {
        this.wikiReader = wikiReader;
        this.repository = repository;
    }


    public void read(String link) {
        LOG.debug("Starting to process link: {} using WikiReader implementation: {}", link, wikiReader.getClass());
        WikiPage wikiPage = wikiReader.read(link);

        if(LOG.isDebugEnabled()) {
            LOG.debug("Finished scrapping link {} result is {}", link, new Gson().toJson(wikiPage));
        }
        LOG.debug("Starting to save wikiPage from link: {}", link);
        repository.save(wikiPage);
        LOG.debug("Finished saving wikiPage from link: {}", link);
    }

}
