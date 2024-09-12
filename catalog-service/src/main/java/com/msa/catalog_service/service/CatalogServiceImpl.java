package com.msa.catalog_service.service;

import com.msa.catalog_service.dto.response.CatalogResponse;
import com.msa.catalog_service.repository.CatalogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;


    @Override
    public List<CatalogResponse> getAllCatalogs() {
        return catalogRepository.findAll().stream()
            .map(CatalogResponse::of)
            .toList();

    }

}
