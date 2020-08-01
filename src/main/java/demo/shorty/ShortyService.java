package demo.shorty;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ShortyService {

    private final List<ShortyUrl> URLS = Collections.synchronizedList(new ArrayList<>());

    public List<ShortyUrl> findAll() {
        return Collections.unmodifiableList(URLS);
    }

    public ShortyUrl shrink(String fullUrl) {
        String shortId = Integer.toHexString(fullUrl.hashCode() ^ Double.hashCode(ThreadLocalRandom.current().nextDouble()));
        ShortyUrl shortyUrl = new ShortyUrl(shortId, fullUrl);
        URLS.add(shortyUrl);
        return shortyUrl;
    }

    public Optional<ShortyUrl> findById(String shortId) {
        return URLS
                .stream()
                .filter(shortyUrl -> shortId.equals(shortyUrl.getShortId()))
                .findFirst();
    }
}
