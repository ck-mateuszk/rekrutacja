package com.lastminute.recruitment.domain;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WikiScrapperTest {

    @InjectMocks
    private WikiScrapper wikiScrapper;

    @Mock private WikiReader wikiReader;
    @Mock private WikiPageRepository wikiPageRepository;

    @Test
    void read_shouldSaveAllLinkedPages() {
        WikiPage initialWikiPage = new WikiPage("Self", "Content", "http://self", List.of("http://link1", "http://link2"));
        WikiPage linkedPage1 = new WikiPage("Link 1", "Content 1", "http://link1", List.of("http://link3"));
        WikiPage linkedPage2 = new WikiPage("Link 2", "Content 2", "http://link2", List.of());
        WikiPage linkedPage3 = new WikiPage("Link 3", "Content 3", "http://link3", List.of());

        when(wikiReader.read("http://self")).thenReturn(initialWikiPage);
        when(wikiReader.read("http://link1")).thenReturn(linkedPage1);
        when(wikiReader.read("http://link2")).thenReturn(linkedPage2);
        when(wikiReader.read("http://link3")).thenReturn(linkedPage3);

        wikiScrapper.read("http://self");

        verify(wikiPageRepository).save(initialWikiPage);
        verify(wikiPageRepository).save(linkedPage1);
        verify(wikiPageRepository).save(linkedPage2);
        verify(wikiPageRepository).save(linkedPage3);

        verify(wikiPageRepository, times(4)).save(any());
    }
}
