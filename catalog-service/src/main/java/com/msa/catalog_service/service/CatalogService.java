package com.msa.catalog_service.service;

import com.msa.catalog_service.dto.response.CatalogResponse;
import java.util.List;

public interface CatalogService {

    List<CatalogResponse> getAllCatalogs();

}
