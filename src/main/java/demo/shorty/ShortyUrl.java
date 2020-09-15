package demo.shorty;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class ShortyUrl {

    private final String shortId;
    private final String fullUrl;
    private final AtomicLong clicks = new AtomicLong();
    private final LocalDateTime createdAt;

    public ShortyUrl(String shortId, String fullUrl, LocalDateTime createdAt) {
        this.shortId = shortId;
        this.fullUrl = fullUrl;
        this.createdAt = createdAt;
    }

    public String getShortId() {
        return shortId;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public long getClicks() {
        return clicks.longValue();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ShortyUrl clicked() {
        clicks.incrementAndGet();
        return this;
    }
}
