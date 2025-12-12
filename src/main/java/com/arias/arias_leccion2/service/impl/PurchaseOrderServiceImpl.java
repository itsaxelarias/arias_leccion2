package com.arias.arias_leccion2.service.impl;

import com.arias.arias_leccion2.entity.PurchaseOrder;
import com.arias.arias_leccion2.repository.PurchaseOrderRepository;
import com.arias.arias_leccion2.repository.PurchaseOrderSpecification;
import com.arias.arias_leccion2.service.PurchaseOrderService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository repository;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PurchaseOrder> findAll(
            String q,
            PurchaseOrder.Status status,
            PurchaseOrder.Currency currency,
            BigDecimal minTotal,
            BigDecimal maxTotal,
            LocalDateTime from,
            LocalDateTime to) {

        if (minTotal != null && minTotal.signum() < 0)
            throw new IllegalArgumentException("minTotal debe ser >= 0");

        if (maxTotal != null && maxTotal.signum() < 0)
            throw new IllegalArgumentException("maxTotal debe ser >= 0");

        if (from != null && to != null && from.isAfter(to))
            throw new IllegalArgumentException("from no puede ser mayor que to");

        Specification<PurchaseOrder> spec =
                Specification.where(PurchaseOrderSpecification.textSearch(q))
                        .and(PurchaseOrderSpecification.hasStatus(status))
                        .and(PurchaseOrderSpecification.hasCurrency(currency))
                        .and(PurchaseOrderSpecification.minTotal(minTotal))
                        .and(PurchaseOrderSpecification.maxTotal(maxTotal))
                        .and(PurchaseOrderSpecification.dateBetween(from, to));

        return repository.findAll(spec);
    }

    @Override
    public PurchaseOrder create(PurchaseOrder order) {
        return repository.save(order);
    }
}
