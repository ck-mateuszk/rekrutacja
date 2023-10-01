package com.lastminute.recruitment.client;

import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Profile("html")
public class WikiReaderHtml implements WikiReader {

    @Override
    public WikiPage read(String link) {
        try {
            Document doc = Jsoup.parse(new HtmlWikiClient().readHtml(link));
            return WikiPage.builder()
                    .title(getTitle(doc))
                    .content(getContent(doc))
                    .selfLink(getSelfLink(doc))
                    .links(getLinks(doc))
                    .build();
        } catch (IOException | NullPointerException e) {
            throw new WikiPageNotFound();
        }
    }

    private String getTitle(Document doc) {
        return doc.getElementsByClass("title").first().ownText();
    }

    private String getContent(Document doc) {
        return doc.getElementsByClass("content").first().ownText();
    }

    private String getSelfLink(Document doc) {
        return doc.getElementsByTag("meta").attr("selfLink");
    }

    private List<String> getLinks(Document doc) {
        return doc.select("a[href]")
                .stream()
                .map(element -> element.absUrl("href"))
                .toList();
    }
}
