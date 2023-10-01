package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class WikiScrapperResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WikiScrapper wikiScrapper;

    @Test
    void scrapWikipedia_shouldReturnOk() throws Exception {
        String link = "http://wikiscrapper.test/page";
        String requestBody = "\"" + link + "\"";

        mockMvc.perform(post("/wiki/scrap")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());

        Mockito.verify(wikiScrapper).read(link);
    }

    @Test
    void scrapWikipedia_shouldReturnNotFound() throws Exception {
        String link = "http://wikiscrapper.test/nonexistent-page";
        String requestBody = "\"" + link + "\"";

        Mockito.doThrow(new WikiPageNotFound()).when(wikiScrapper).read(link);

        mockMvc.perform(post("/wiki/scrap")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }
}
