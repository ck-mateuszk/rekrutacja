package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wiki")
@RestController
@RequiredArgsConstructor
public class WikiScrapperResource {

    private final WikiScrapper wikiScrapper;

    @PostMapping("/scrap")
    public void scrapWikipedia(@RequestBody String link) {
        throw new WikiPageNotFound();
        //wikiScrapper.read(link);
    }

}
