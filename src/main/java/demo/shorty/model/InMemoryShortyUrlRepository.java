package demo.shorty.model;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Repository
class InMemoryShortyUrlRepository implements ShortyUrlRepository {

    private final Set<ShortyUrl> URLS = Collections.synchronizedSet(new LinkedHashSet<>());

    @Override
    public Set<ShortyUrl> findAll() {
        return Collections.unmodifiableSet(URLS);
    }

    @Override
    public ShortyUrl save(ShortyUrl shortyUrl) {
        URLS.add(shortyUrl);
        return shortyUrl;
    }

    @Override
    public Optional<ShortyUrl> findById(String shortId) {
        return URLS.stream()
                .filter(shortyUrl -> shortId.equals(shortyUrl.getShortId()))
                .findFirst();
    }

    @Override
    public boolean deleteById(String shortId) {
        return URLS.removeIf(s -> s.getShortId().equals(shortId));
    }
}
