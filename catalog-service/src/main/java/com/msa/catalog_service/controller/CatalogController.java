package com.msa.catalog_service.controller;

import com.msa.catalog_service.dto.response.CatalogResponse;
import com.msa.catalog_service.service.CatalogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {

    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in Catalog Service On PORT %s",
            env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.welcome");
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<CatalogResponse>> getCatalogs() {

        List<CatalogResponse> data = catalogService.getAllCatalogs();

        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
