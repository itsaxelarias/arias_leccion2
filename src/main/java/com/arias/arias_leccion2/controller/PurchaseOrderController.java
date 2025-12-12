package com.arias.arias_leccion2.controller;

import com.arias.arias_leccion2.entity.PurchaseOrder;
import com.arias.arias_leccion2.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService service;

    public PurchaseOrderController(PurchaseOrderService service) {
        this.service = service;
    }

    @PostMapping
    public PurchaseOrder create(@Valid @RequestBody PurchaseOrder order) {
        return service.create(order);
    }

    @GetMapping
    public List<PurchaseOrder> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) PurchaseOrder.Status status,
            @RequestParam(required = false) PurchaseOrder.Currency currency,
            @RequestParam(required = false) BigDecimal minTotal,
            @RequestParam(required = false) BigDecimal maxTotal,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to
    ) {
        return service.findAll(q, status, currency, minTotal, maxTotal, from, to);
    }
}
