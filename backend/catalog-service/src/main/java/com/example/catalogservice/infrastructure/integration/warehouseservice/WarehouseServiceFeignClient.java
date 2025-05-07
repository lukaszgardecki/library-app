package com.example.catalogservice.infrastructure.integration.warehouseservice;

import com.example.catalogservice.domain.integration.warehouse.dto.RackDto;
import com.example.catalogservice.domain.integration.warehouse.dto.ShelfDto;
import com.example.catalogservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "warehouse-service", path = "/warehouse", configuration = FeignClientCustomConfiguration.class)
interface WarehouseServiceFeignClient {

    @GetMapping("/racks/{id}")
    ResponseEntity<RackDto> getRackById(@PathVariable Long id);

    @GetMapping("/shelves/{id}")
    ResponseEntity<ShelfDto> getShelfById(@PathVariable Long id);
}
