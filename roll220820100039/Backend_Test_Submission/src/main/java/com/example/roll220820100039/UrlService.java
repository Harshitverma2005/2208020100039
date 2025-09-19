package com.example.roll220820100039;

import com.affordmed.urlshortener.model.*;
import com.affordmed.urlshortener.repository.ShortUrlRepository;
import com.affordmed.urlshortener.exception.ShortUrlException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UrlService {
    private final ShortUrlRepository repo;

    public UrlService(ShortUrlRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public ShortUrl createShortUrl(String url, Integer validityMinutes, String desiredShortcode) {
        String code = desiredShortcode;
        if (code == null || code.isEmpty()) {
            code = generateUniqueShortCode();
        } else if (repo.existsById(code)) {
            throw new ShortUrlException("Shortcode collision");
        }
        if (!isValidUrl(url)) throw new ShortUrlException("Malformed input url");
        if (validityMinutes == null) validityMinutes = 30;

        ShortUrl s = new ShortUrl();
        s.setShortcode(code);
        s.setOriginalUrl(url);
        s.setCreated(LocalDateTime.now());
        s.setExpiry(s.getCreated().plusMinutes(validityMinutes));
        s.setClickRecords(new ArrayList<>());
        repo.save(s);
        return s;
    }

    public ShortUrl fetchShortUrl(String shortcode) {
        ShortUrl s = repo.findById(shortcode)
            .orElseThrow(() -> new ShortUrlException("Shortcode not found"));
        if (LocalDateTime.now().isAfter(s.getExpiry()))
            throw new ShortUrlException("Shortcode expired");
        return s;
    }

    public void recordClick(String shortcode, String referrer, String geoLocation) {
        ShortUrl s = fetchShortUrl(shortcode);
        List<ClickRecord> records = s.getClickRecords();
        ClickRecord cr = new ClickRecord();
        cr.setTimestamp(LocalDateTime.now());
        cr.setReferrer(referrer);
        cr.setGeoLocation(geoLocation);
        records.add(cr);
        repo.save(s);
    }

    // Utility methods below

    private String generateUniqueShortCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 5);
        } while (repo.existsById(code));
        return code;
    }

    private boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }
}
