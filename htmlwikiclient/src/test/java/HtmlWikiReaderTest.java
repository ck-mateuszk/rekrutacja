import com.lastminute.recruitment.client.HtmlWikiClient;
import com.lastminute.recruitment.domain.WikiPage;
import com.lastminute.recruitment.domain.WikiReader;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import com.lastminute.recruitment.reader.HtmlWikiReader;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HtmlWikiReaderTest {

    private final HtmlWikiClient htmlWikiClient = mock(HtmlWikiClient.class);

    private final WikiReader wikiReader = new HtmlWikiReader(htmlWikiClient);

    @Test
    public void shouldThrowWikiPageNotFoundOnMissingFile() {
        when(htmlWikiClient.readHtml(any())).thenThrow(WikiPageNotFound.class);
        String testUrl = "testUrl";
        assertThrows(WikiPageNotFound.class, () -> wikiReader.read(testUrl));
    }

    @Test
    public void shouldProcessWikiPage() {
        when(htmlWikiClient.readHtml(any())).thenReturn(Objects.requireNonNull(getClass().getResource("example.html")).getFile());
        String testUrl = "testUrl";
        WikiPage wikiPage = wikiReader.read(testUrl);
        assertEquals(wikiPage.getTitle(), "title");
        assertEquals(wikiPage.getContent(), "content");
        assertEquals(wikiPage.getSelfLink(), "selfLink");
        assertEquals(wikiPage.getLinks().size(), 1);
        assertEquals(wikiPage.getLinks().get(0), "http://wikiscrapper.test/site1");
    }
}
