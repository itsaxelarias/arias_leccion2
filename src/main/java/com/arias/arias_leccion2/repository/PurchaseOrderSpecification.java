package com.arias.arias_leccion2.repository;

import com.arias.arias_leccion2.entity.PurchaseOrder;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseOrderSpecification {

    public static Specification<PurchaseOrder> textSearch(String q) {
        return (root, query, cb) -> {
            if (q == null || q.isBlank()) return null;
            String like = "%" + q.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("orderNumber")), like),
                    cb.like(cb.lower(root.get("supplierName")), like)
            );
        };
    }

    public static Specification<PurchaseOrder> hasStatus(PurchaseOrder.Status status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<PurchaseOrder> hasCurrency(PurchaseOrder.Currency currency) {
        return (root, query, cb) ->
                currency == null ? null : cb.equal(root.get("currency"), currency);
    }

    public static Specification<PurchaseOrder> minTotal(BigDecimal min) {
        return (root, query, cb) ->
                min == null ? null : cb.greaterThanOrEqualTo(root.get("totalAmount"), min);
    }

    public static Specification<PurchaseOrder> maxTotal(BigDecimal max) {
        return (root, query, cb) ->
                max == null ? null : cb.lessThanOrEqualTo(root.get("totalAmount"), max);
    }

    public static Specification<PurchaseOrder> dateBetween(
            LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;
            if (from != null && to != null)
                return cb.between(root.get("createdAt"), from, to);
            if (from != null)
                return cb.greaterThanOrEqualTo(root.get("createdAt"), from);
            return cb.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }
}
