package com.almielka.restapicrud.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean domain object representing a Product of the {@link Company}.
 * Each Product has an id property {@code id} and a name property {@code name},
 * than extends class with this properties {@link AbstractNameEntity}.
 * Also each Product has:
 * <ul>
 * <li>{@code company} {@link Company};
 * <li>the date when the product was made {@code createdDate}, UTC time zone;</li>
 * <li>the {@code price} of the product;</li>
 * <li>collection of orders {@code orderList} {@link Order}, cause Product can be ordered.</li>
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
@Table(name = "product", indexes = {@Index(columnList = "created_date")})
public class Product extends AbstractNameEntity {

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    // ManyToOne: One company has Many products, Оne product belongs to Оne company

    @Column(name = "created_date")
    @NotNull
    @PastOrPresent
    private Instant createdDate;

    @Column
    @Min(value = 0, message = "The price must be positive")
    private double price;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "product_order_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_product_id"))
    private List<Order> orderList = new ArrayList<>();
    // ManyToMany: One product can be ordered Many times, One Order can have Many product


    public Product(Company company, Instant createdDate, double price) {
        this.company = company;
        this.createdDate = createdDate;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "company=" + company +
                ", createdDate=" + createdDate +
                ", price=" + price +
                ", orderList=" + orderList.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Product product = (Product) o;

        if (Double.compare(product.price, price) != 0) return false;
        if (company != null ? !company.equals(product.company) : product.company != null) return false;
        return createdDate != null ? createdDate.equals(product.createdDate) : product.createdDate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
