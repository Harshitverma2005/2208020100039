package com.example.roll220820100039;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ClickRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    private String referrer;
    private String geoLocation; // Optional: for simplicity as string

    // Getters/Setters ...
}
