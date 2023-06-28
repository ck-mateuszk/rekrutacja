package com.lastminute.recruitment.domain;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class WikiScrapper {

    private static final Logger LOG = LoggerFactory.getLogger(WikiScrapper.class);

    private final WikiReader wikiReader;
    private final WikiPageRepository repository;

    public WikiScrapper(WikiReader wikiReader, WikiPageRepository repository) {
        this.wikiReader = wikiReader;
        this.repository = repository;
    }


    public void read(String link) {
        LOG.debug("Starting to process link: {} using WikiReader implementation: {}", link, wikiReader.getClass());
        Set<String> pages = new HashSet<>();
        processLinkWithChildren(link, pages);
    }

    private void processLinkWithChildren(String link, Set<String> pages) {
        WikiPage page = wikiReader.read(link);
        pages.add(page.getSelfLink());
        if(LOG.isDebugEnabled()) {
            LOG.debug("Finished scrapping link {} result is {}", link, new Gson().toJson(page));
        }
        LOG.debug("Starting to save wikiPage from link: {}", link);
        repository.save(page);
        LOG.debug("Finished saving wikiPage from link: {}", link);

        processChildren(pages, page);
    }

    private void processChildren(Set<String> pages, WikiPage page) {
        for (String child: page.getLinks()) {
            if(!pages.add(child)) {
                continue;
            }
            processLinkWithChildren(child, pages);
        }
    }
}
