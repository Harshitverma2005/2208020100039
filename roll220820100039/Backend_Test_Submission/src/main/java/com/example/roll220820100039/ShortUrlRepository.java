package com.example.roll220820100039;

import com.affordmed.urlshortener.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {
}
