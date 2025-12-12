package com.arias.arias_leccion2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "purchase_orders",
        uniqueConstraints = @UniqueConstraint(columnNames = "orderNumber"))
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "orderNumber es obligatorio")
    @Column(nullable = false, unique = true)
    private String orderNumber;

    @NotBlank(message = "supplierName es obligatorio")
    @Column(nullable = false)
    private String supplierName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "status es obligatorio")
    private Status status;

    @NotNull(message = "totalAmount es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "totalAmount debe ser >= 0")
    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "currency es obligatorio")
    private Currency currency;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDate expectedDeliveryDate;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public enum Status {
        DRAFT, SUBMITTED, APPROVED, REJECTED, CANCELLED
    }

    public enum Currency {
        USD, EUR
    }
}
