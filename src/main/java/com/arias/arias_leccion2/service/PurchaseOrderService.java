package com.arias.arias_leccion2.service;

import com.arias.arias_leccion2.entity.PurchaseOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseOrderService {

    List<PurchaseOrder> findAll(
            String q,
            PurchaseOrder.Status status,
            PurchaseOrder.Currency currency,
            BigDecimal minTotal,
            BigDecimal maxTotal,
            LocalDateTime from,
            LocalDateTime to
    );

    PurchaseOrder create(PurchaseOrder order);
}

