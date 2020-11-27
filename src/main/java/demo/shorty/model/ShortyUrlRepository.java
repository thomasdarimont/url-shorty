package demo.shorty.model;

import java.util.Optional;
import java.util.Set;

public interface ShortyUrlRepository {

    Set<ShortyUrl> findAll();

    ShortyUrl save(ShortyUrl shortyUrl);

    Optional<ShortyUrl> findById(String shortId);

    boolean deleteById(String shortId);
}
