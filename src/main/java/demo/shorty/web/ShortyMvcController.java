package demo.shorty.web;

import demo.shorty.model.ShortUrl;
import demo.shorty.service.ShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
class ShortyMvcController {

    private static final Logger LOG = LoggerFactory.getLogger(ShortyMvcController.class);

    private final ShortUrlService service;

    public ShortyMvcController(ShortUrlService service) {
        this.service = service;
    }

    @GetMapping({"/", "/urls"})
    public String index(Model model) {

        LOG.info("show index page");

        model.addAttribute("urls", service.findAll());
        return "index";
    }

    @PostMapping("/urls/shorten")
    public String newShortUrl(@RequestParam String fullUrl) {

        LOG.info("create new shorturl");

        if (StringUtils.hasText(fullUrl)) {
            service.create(fullUrl);
        }

        return "redirect:/urls";
    }

    @GetMapping("/urls/{shortId}")
    public RedirectView find(@PathVariable String shortId) {

        LOG.info("try to find shorturl by id");

        Optional<ShortUrl> candidate = service.findById(shortId);

        return candidate.map(this::redirectToUrl)
                .orElseGet(this::redirectToNotFound);
    }

    @DeleteMapping("/urls/{shortId}")
    @ResponseBody
    public void delete(@PathVariable String shortId) {

        LOG.info("try to delete shorturl by id");

        service.deleteById(shortId);
    }

    private RedirectView redirectToNotFound() {
        return new RedirectView("/error/404");
    }

    private RedirectView redirectToUrl(ShortUrl shortUrl) {
        return new RedirectView(shortUrl.clicked().getFullUrl());
    }

}
