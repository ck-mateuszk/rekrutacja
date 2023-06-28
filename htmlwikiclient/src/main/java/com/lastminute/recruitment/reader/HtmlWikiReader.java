package com.lastminute.recruitment.reader;

import com.lastminute.recruitment.client.HtmlWikiClient;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class HtmlWikiReader implements WikiReader {
    private final HtmlWikiClient htmlWikiClient;

    public HtmlWikiReader(HtmlWikiClient htmlWikiClient) {
        this.htmlWikiClient = htmlWikiClient;
    }

    @Override
    public WikiPage read(String link){
        String fileName = htmlWikiClient.readHtml(link);

        Document document = Jsoup.parse(fileName);
        Elements elements = document.getAllElements();
        if (elements.size() == 0) throw new WikiPageNotFound();

        String title = getTitle(document);
        String content = getContent(document);
        String selfLink = getSelfLink(document);
        List<String> links = getLinks(document);

        return new WikiPage(title, content, selfLink, links);
    }

    private static List<String> getLinks(Document document) {
        Elements linkTags = document.select("a[href]");
        return linkTags.stream().map(linkTag -> linkTag.attr("abs:href")).filter(tag -> !tag.isBlank()).collect(Collectors.toList());
    }

    private static String getContent(Document document) {
        Elements contentTags = document.getElementsByClass("content");
        return contentTags.stream().map(Element::ownText).filter(text -> !text.isBlank()).findFirst().orElse(null);
    }

    private static String getSelfLink(Document document) {
        Elements metaTags = document.getElementsByTag("meta");
        return metaTags.stream().map(meta -> meta.attr("selfLink")).filter(tag -> !tag.isBlank()).findFirst().orElse(null);
    }

    private static String getTitle(Document document) {
        Elements titleTags = document.getElementsByClass("title");
        return titleTags.stream().map(Element::ownText).filter(text -> !text.isBlank()).findFirst().orElse(null);
    }
}
