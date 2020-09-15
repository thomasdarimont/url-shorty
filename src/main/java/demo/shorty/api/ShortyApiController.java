package demo.shorty.api;

import demo.shorty.ShortyUrl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import demo.shorty.ShortyService;

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
    ShortyUrl shrink(@RequestParam String fullUrl) {
        return service.shorten(fullUrl);
    }

    @GetMapping("/api/urls/{shortId}")
    ResponseEntity<?> find(@PathVariable String shortId) {
        return ResponseEntity.of(service.findById(shortId));
    }
}
