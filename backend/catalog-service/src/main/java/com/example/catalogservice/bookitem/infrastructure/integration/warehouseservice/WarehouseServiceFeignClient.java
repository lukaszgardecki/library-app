package com.example.catalogservice.bookitem.infrastructure.integration.warehouseservice;

import com.example.catalogservice.bookitem.domain.dto.RackDto;
import com.example.catalogservice.bookitem.domain.dto.ShelfDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseServiceFeignClient {

    @GetMapping("/racks/{id}")
    ResponseEntity<RackDto> getRackById(@PathVariable Long id);

    @GetMapping("/shelves/{id}")
    ResponseEntity<ShelfDto> getShelfById(@PathVariable Long id);
}
