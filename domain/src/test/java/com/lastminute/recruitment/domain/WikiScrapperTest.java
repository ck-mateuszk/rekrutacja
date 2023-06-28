package com.lastminute.recruitment.domain;

import com.google.common.collect.Lists;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WikiScrapperTest {

    private final WikiReader wikiReaderMock = mock(WikiReader.class);

    private final WikiPageRepository wikiPageRepositoryMock = mock(WikiPageRepository.class);

    private final WikiScrapper wikiScrapper = new WikiScrapper(wikiReaderMock, wikiPageRepositoryMock);

    @Test
    public void handlesWikiPageNotFoundException() {
        when(wikiReaderMock.read(any())).thenThrow(WikiPageNotFound.class);
        String testUrl = "testUrl";
        assertThrows(WikiPageNotFound.class, () -> wikiScrapper.read(testUrl));
        verify(wikiReaderMock, times(1)).read(testUrl);
        verify(wikiPageRepositoryMock, times(0)).save(any());
    }

    @Test
    public void handlesCorrectUrl() {
        when(wikiReaderMock.read("page1")).thenReturn(new WikiPage("title", "content", "selfLink", Lists.newArrayList()));

        wikiScrapper.read("page1");

        verify(wikiReaderMock, times(1)).read(any());
        verify(wikiPageRepositoryMock, times(1)).save(any());
    }

    @Test
    public void handlesLoopedWikiPages() {
        WikiPage expectedWikiPage1 = new WikiPage("title", "content", "page1", Lists.newArrayList("page2", "page3"));
        WikiPage expectedWikiPage2 = new WikiPage("title", "content", "page2", Lists.newArrayList("page3", "page1"));
        WikiPage expectedWikiPage3 = new WikiPage("title", "content", "page3", Lists.newArrayList("page1", "page3"));
        when(wikiReaderMock.read("page1")).thenReturn(expectedWikiPage1);
        when(wikiReaderMock.read("page2")).thenReturn(expectedWikiPage2);
        when(wikiReaderMock.read("page3")).thenReturn(expectedWikiPage3);

        wikiScrapper.read("page1");

        verify(wikiReaderMock, times(1)).read("page1");
        verify(wikiReaderMock, times(1)).read("page2");
        verify(wikiReaderMock, times(1)).read("page3");
        verify(wikiPageRepositoryMock, times(1)).save(expectedWikiPage1);
        verify(wikiPageRepositoryMock, times(1)).save(expectedWikiPage2);
        verify(wikiPageRepositoryMock, times(1)).save(expectedWikiPage3);
    }
}
