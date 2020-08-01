package demo.shorty;

import java.util.concurrent.atomic.AtomicLong;

public class ShortyUrl {

    private final String shortId;
    private final String fullUrl;
    private final AtomicLong clicks = new AtomicLong();

    public ShortyUrl(String shortId, String fullUrl) {
        this.shortId = shortId;
        this.fullUrl = fullUrl;
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

    public ShortyUrl clicked() {
        clicks.incrementAndGet();
        return this;
    }
}
