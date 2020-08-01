package demo.shorty.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import demo.shorty.ShortyUrl;
import demo.shorty.ShortyService;

import java.util.Optional;

@Controller
class ShortyWebController {

    private final ShortyService service;

    public ShortyWebController(ShortyService service) {
        this.service = service;
    }

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute("urls", service.findAll());
        return "index";
    }

    @PostMapping("/shrink")
    String shrink(@RequestParam String fullUrl) {
        service.shrink(fullUrl);
        return "redirect:/";
    }

    @GetMapping("/{shortId}")
    RedirectView find(@PathVariable String shortId) {

        Optional<ShortyUrl> candidate = service.findById(shortId);

        return candidate.map(this::redirectToUrl).orElseGet(this::redirectToNotFound);
    }

    private RedirectView redirectToNotFound() {
        return new RedirectView("/error/404");
    }

    private RedirectView redirectToUrl(ShortyUrl shortyUrl) {
        return new RedirectView(shortyUrl.clicked().getFullUrl());
    }

}
