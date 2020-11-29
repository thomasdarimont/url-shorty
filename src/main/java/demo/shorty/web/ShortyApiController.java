package demo.shorty.web;

import demo.shorty.service.ShortUrlService;
import demo.shorty.model.ShortUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/urls")
class ShortyApiController {

    private static final Logger LOG = LoggerFactory.getLogger(ShortyMvcController.class);

    private final ShortUrlService service;

    public ShortyApiController(ShortUrlService service) {
        this.service = service;
    }

    @GetMapping
    public Object findAll() {

        LOG.info("find all shorturls");

        return service.findAll();
    }

    @PostMapping
    public ShortUrl newShortUrl(@RequestParam String fullUrl) {

        LOG.info("create new shorturl");

        return service.create(fullUrl);
    }

    @DeleteMapping("/{shortId}")
    public ResponseEntity<Void> deleteById(@PathVariable String shortId) {

        LOG.info("try to delete shorturl by id");

        boolean deleted = service.deleteById(shortId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{shortId}")
    public ResponseEntity<?> findById(@PathVariable String shortId) {

        LOG.info("try to find shorturl by id");

        return ResponseEntity.of(service.findById(shortId));
    }

    @GetMapping("/{shortId}/redirect")
    public ResponseEntity<?> resolveRedirect(@PathVariable String shortId) {

        LOG.info("try to redirect to shorturl by id");

        Optional<ShortUrl> maybeShortUrl = service.findById(shortId);

        return ResponseEntity.of(maybeShortUrl.map(shortUrl -> {
            return ResponseEntity
                    .status(HttpStatus.TEMPORARY_REDIRECT)
                    .header(HttpHeaders.LOCATION, shortUrl.getFullUrl())
                    .build();
        }));
    }
}
