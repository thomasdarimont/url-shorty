package demo.shorty.service;

import demo.shorty.model.ShortyUrl;
import demo.shorty.model.ShortyUrlRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
class DefaultShortyService implements ShortyService {

    private final ShortyUrlRepository repository;

    public DefaultShortyService(ShortyUrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<ShortyUrl> findAll() {
        return repository.findAll();
    }

    @Override
    public ShortyUrl shorten(String fullUrl) {
        String shortId = createShortId(fullUrl);
        ShortyUrl shortyUrl = new ShortyUrl(shortId, fullUrl, LocalDateTime.now());
        return repository.save(shortyUrl);
    }

    @Override
    public String createShortId(String fullUrl) {
        return Integer.toHexString(fullUrl.hashCode());
    }

    @Override
    public Optional<ShortyUrl> findById(String shortId) {
        return repository.findById(shortId);
    }

    @Override
    public boolean deleteLink(String shortId) {
        return repository.deleteById(shortId);
    }
}
