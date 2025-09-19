package com.example.roll220820100039;

import com.affordmed.urlshortener.model.*;
import com.affordmed.urlshortener.service.UrlService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/shorturls")
public class UrlController {

    private final UrlService urlService;
    public UrlController(UrlService urlService) { this.urlService = urlService; }

    // 1. Create short URL (POST /shorturls)
    @PostMapping
    public ResponseEntity<Map<String,String>> createShortUrl(@RequestBody Map<String, Object> req) {
        String url = (String) req.get("url");
        Integer validity = req.get("validity")!=null ? (Integer) req.get("validity") : null;
        String shortcode = (String) req.get("shortcode");
        ShortUrl shortUrl = urlService.createShortUrl(url, validity, shortcode);
        Map<String,String> resp = new HashMap<>();
        resp.put("shortLink", "https://hostname:port/" + shortUrl.getShortcode());
        resp.put("expiry", shortUrl.getExpiry().toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // 2. Analytics (GET /shorturls/{shortcode})
    @GetMapping("/{shortcode}")
    public Map<String,Object> getAnalytics(@PathVariable String shortcode) {
        ShortUrl s = urlService.fetchShortUrl(shortcode);
        Map<String,Object> resp = new HashMap<>();
        resp.put("totalClicks", s.getClickRecords().size());
        resp.put("originalUrl", s.getOriginalUrl());
        resp.put("created", s.getCreated());
        resp.put("expiry", s.getExpiry());
        resp.put("clicks", s.getClickRecords());
        return resp;
    }
}

// 3. Redirect mapping in another controller or global config if needed.
