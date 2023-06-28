package com.lastminute.recruitment;

import com.lastminute.recruitment.rest.WikiScrapperResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = WikiScrapperResource.class)
@ActiveProfiles("html")
@Import(WikiScrapperConfiguration.class)
public class WikiScrapperResourceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldReturn404ForNotExistingWikiPage() {
        WebTestClient.ResponseSpec response = webTestClient.post().uri("/wiki/scrap").bodyValue("http://localhost:8080").exchange();

        response.expectStatus().isNotFound()
                .expectBody().isEmpty();
    }

    @Test
    public void shouldReturn200ForExistingWikiPage() {
        WebTestClient.ResponseSpec response = webTestClient.post().uri("/wiki/scrap").bodyValue("\"http://wikiscrapper.test/site1\"").exchange();

        response.expectStatus().isOk();
        // acceptance criteria specify nothing about reading the scrapped page
    }

}
