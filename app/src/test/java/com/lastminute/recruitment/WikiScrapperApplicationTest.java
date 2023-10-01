package com.lastminute.recruitment;

import com.lastminute.recruitment.domain.WikiScrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("json")
class WikiScrapperApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void scrapWikipedia_withJsonProfile_shouldReturnOk() throws Exception {
        String requestBody = "\"http://wikiscrapper.test/site1\"";
        mockMvc.perform(post("/wiki/scrap")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void scrapWikipedia_withJsonProfile_shouldReturnNotFound() throws Exception {
        String requestBody = "\"http://wikiscrapper.test/siteXX\"";
        mockMvc.perform(post("/wiki/scrap")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }
}
