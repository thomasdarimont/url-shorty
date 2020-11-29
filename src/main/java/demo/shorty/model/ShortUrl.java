package demo.shorty.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class ShortUrl {

    private final String shortId;

    private final String fullUrl;

    private final AtomicLong clicks = new AtomicLong();

    private final LocalDateTime createdAt;

    private final AtomicReference<LocalDateTime> lastAccessedAt;

    public ShortUrl(String shortId, String fullUrl, LocalDateTime createdAt) {
        this.shortId = shortId;
        this.fullUrl = fullUrl;
        this.createdAt = createdAt;
        this.lastAccessedAt = new AtomicReference<>(createdAt);
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

    public LocalDateTime getLastAccessedAt() {
        return lastAccessedAt.get();
    }

    public ShortUrl clicked() {
        clicks.incrementAndGet();
        lastAccessedAt.lazySet(LocalDateTime.now());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShortUrl shortUrl = (ShortUrl) o;
        return shortId.equals(shortUrl.shortId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortId);
    }

    @Override
    public String toString() {
        return "ShortUrl{" +
                "shortId='" + shortId + '\'' +
                ", fullUrl='" + fullUrl + '\'' +
                ", clicks=" + clicks +
                ", createdAt=" + createdAt +
                ", lastAccessedAt=" + lastAccessedAt +
                '}';
    }
}