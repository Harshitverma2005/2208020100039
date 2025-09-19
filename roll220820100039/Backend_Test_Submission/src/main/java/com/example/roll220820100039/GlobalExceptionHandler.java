package com.example.roll220820100039;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShortUrlException.class)
    @ResponseBody
    public ResponseEntity<Map<String,String>> handleCustom(ShortUrlException ex) {
        Map<String,String> map = new HashMap<>();
        map.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    // Add other handlers for 404, malformed input, etc.
}
