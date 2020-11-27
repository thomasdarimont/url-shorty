package demo.shorty.service;

import demo.shorty.model.ShortyUrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DefaultShortyServiceTests {

    @Autowired
    DefaultShortyService shortyService;

    @Test
    void thereShouldBeNoUrlsOnStart() {
        assertThat(shortyService.findAll()).isEmpty();
    }

    @Test
    void createAndFindAll() {

        ShortyUrl shortUrl = shortyService.shorten("https://google.de");
        assertThat(shortUrl).isNotNull();

        Set<ShortyUrl> urls = shortyService.findAll();
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
