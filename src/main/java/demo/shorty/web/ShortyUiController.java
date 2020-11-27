package demo.shorty.web;

import demo.shorty.service.ShortyService;
import demo.shorty.model.ShortyUrl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
class ShortyUiController {

    private final ShortyService service;

    public ShortyUiController(ShortyService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("urls", service.findAll());
        return "index";
    }

    @PostMapping("/shorten")
    public String shorten(@RequestParam String fullUrl) {

        if (!StringUtils.isEmpty(fullUrl)) {
            service.shorten(fullUrl);
        }

        return "redirect:/";
    }

    @GetMapping("/{shortId}")
    public RedirectView find(@PathVariable String shortId) {

        Optional<ShortyUrl> candidate = service.findById(shortId);

        return candidate.map(this::redirectToUrl)
                .orElseGet(this::redirectToNotFound);
    }

    @DeleteMapping("/{shortId}")
    public RedirectView delete(@PathVariable String shortId) {

        service.deleteLink(shortId);

        RedirectView redirect = new RedirectView("/", true);
        redirect.setStatusCode(HttpStatus.SEE_OTHER);
        return redirect;
    }

    private RedirectView redirectToNotFound() {
        return new RedirectView("/error/404");
    }

    private RedirectView redirectToUrl(ShortyUrl shortyUrl) {
        return new RedirectView(shortyUrl.clicked().getFullUrl());
    }

}
