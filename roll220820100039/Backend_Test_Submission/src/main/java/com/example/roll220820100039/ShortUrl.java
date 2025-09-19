package com.example.roll220820100039;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

@Entity
public class ShortUrl {
    @Id
    private String shortcode; // e.g., "abcd1"
    private String originalUrl;
    private LocalDateTime created;
    private LocalDateTime expiry;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClickRecord> clickRecords;

    // Getters/Setters ...
}
