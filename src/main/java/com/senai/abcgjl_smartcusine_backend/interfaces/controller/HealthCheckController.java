package com.senai.abcgjl_smartcusine_backend.interfaces.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

        @GetMapping("/healthz")
        public ResponseEntity<String> healthz() {
            return ResponseEntity.ok("ok");
        }

}
