package com.almielka.restapicrud.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean domain object representing an Order.
 * Each Order has an id property {@code id},
 * than extends class with this property {@link AbstractIdEntity}.
 * Also each Order has:
 * <ul>
 * <li>date of order {@code createdAt}, UTC time zone;</li>
 * <li>collection of products {@code productSet} {@link Product} which were ordered.</li>
 * </ul>
 *
 * @author Anna S. Almielka
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Component
@Table(name = "order_product", indexes = {@Index(columnList = "created_at")})
public class Order extends AbstractIdEntity {

    @Column(name = "created_at")
    @NotNull
    private Instant createdAt;

    @ManyToMany(mappedBy = "orderList",  cascade = {CascadeType.MERGE})
    private List<Product> productList = new ArrayList<>();

    @Override
    public String toString() {
        return "Order{" +
                "createdAt=" + createdAt +
                ", productList=" + productList.toString() +
                '}';
    }
}