package demo.shorty.api;

import demo.shorty.ShortyUrl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import demo.shorty.ShortyService;

import java.util.Optional;

@RestController
class ShortyApiController {

    private final ShortyService service;

    public ShortyApiController(ShortyService service) {
        this.service = service;
    }

    @GetMapping("/api/urls")
    Object findAll() {
        return service.findAll();
    }

    @PostMapping("/api/urls")
    ShortyUrl shorten(@RequestParam String fullUrl) {
        return service.shorten(fullUrl);
    }

    @DeleteMapping("/api/urls/{shortId}")
    ResponseEntity<Void> delete(@PathVariable String shortId) {

        boolean deleted = service.deleteLink(shortId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/api/urls/{shortId}")
    ResponseEntity<?> find(@PathVariable String shortId) {
        return ResponseEntity.of(service.findById(shortId));
    }

    @GetMapping("/api/urls/{shortId}/redirect")
    ResponseEntity<?> redirect(@PathVariable String shortId) {
        Optional<ShortyUrl> maybeShortUrl = service.findById(shortId);
        return ResponseEntity.of(maybeShortUrl.map(shortUrl -> {
            return ResponseEntity
                    .status(HttpStatus.TEMPORARY_REDIRECT)
                    .header(HttpHeaders.LOCATION, shortUrl.getFullUrl())
                    .build();
        }));
    }
}
