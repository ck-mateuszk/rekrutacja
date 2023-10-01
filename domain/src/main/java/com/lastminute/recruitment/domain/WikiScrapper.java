package com.lastminute.recruitment.domain;

import java.util.HashSet;
import java.util.Set;

public class WikiScrapper {

    private final WikiReader wikiReader;
    private final WikiPageRepository repository;

    public WikiScrapper(WikiReader wikiReader, WikiPageRepository repository) {
        this.wikiReader = wikiReader;
        this.repository = repository;
    }


    public void read(String link) {
        WikiPage wikiPage = wikiReader.read(link);
        Set<WikiPage> wikiPagesRead = readAll(wikiPage);
        wikiPagesRead.forEach(repository::save);
    }

    private Set<WikiPage> readAll(WikiPage initialWikiPage) {
        return getAllLinkedPages(initialWikiPage, new HashSet<>());
    }

    private Set<WikiPage> getAllLinkedPages(WikiPage mainWikiPage, Set<String> alreadyReadLinks) {
        Set<WikiPage> result = new HashSet<>();

        if (alreadyReadLinks.contains(mainWikiPage.getSelfLink())) {
            return result;
        }

        alreadyReadLinks.add(mainWikiPage.getSelfLink());
        result.add(mainWikiPage);

        for (String nextLink : mainWikiPage.getLinks()) {
            WikiPage nextWikiPage = wikiReader.read(nextLink);
            result.add(nextWikiPage);
            result.addAll(getAllLinkedPages(nextWikiPage, alreadyReadLinks));
        }

        return result;
    }
}
