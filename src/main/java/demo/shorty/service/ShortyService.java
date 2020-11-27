package demo.shorty.service;

import demo.shorty.model.ShortyUrl;

import java.util.Optional;
import java.util.Set;

public interface ShortyService {

    Set<ShortyUrl> findAll();

    ShortyUrl shorten(String fullUrl);

    String createShortId(String fullUrl);

    Optional<ShortyUrl> findById(String shortId);

    boolean deleteLink(String shortId);
}
