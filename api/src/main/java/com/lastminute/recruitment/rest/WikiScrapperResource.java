package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.domain.error.EmptyLinkProvidedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wiki")
@RestController
public class WikiScrapperResource {

    @Autowired
    private WikiScrapper wikiScrapper;

    static final Logger LOG = LoggerFactory.getLogger(WikiScrapperResource.class);

    @PostMapping("/scrap")
    public void scrapWikipedia(@RequestBody String link) {
        LOG.debug("Incoming request to scrap link: {}", link);
        validate(link);
        wikiScrapper.read(link);
    }

    private void validate(String link) {
        if (link == null || link.isBlank()) throw new EmptyLinkProvidedException();
    }

}
