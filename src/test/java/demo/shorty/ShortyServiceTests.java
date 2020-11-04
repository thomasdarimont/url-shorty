package demo.shorty;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ShortyServiceTests {

    @Autowired
    ShortyService shortyService;

    @Test
    void thereShouldBeNoUrlsOnStart() {
        assertThat(shortyService.findAll()).isEmpty();
    }

    @Test
    void createAndFindAll() {

        ShortyUrl shortUrl = shortyService.shorten("https://google.de");

        List<ShortyUrl> urls = shortyService.findAll();
        assertThat(urls).isNotEmpty();
        assertThat(urls).hasSize(1);
    }

    @Test
    void createAndFindOne() {

        ShortyUrl shortUrl = shortyService.shorten("https://google.de");

        Optional<ShortyUrl> found = shortyService.findById(shortUrl.getShortId());

        assertThat(found).isNotEmpty();
        assertThat(found).contains(shortUrl);
    }

    @Test
    void createAndDeleteOne() {

        ShortyUrl shortUrl = shortyService.shorten("https://google.de");

        shortyService.deleteLink(shortUrl.getShortId());

        Optional<ShortyUrl> found = shortyService.findById(shortUrl.getShortId());
        assertThat(found).isEmpty();
    }
}
