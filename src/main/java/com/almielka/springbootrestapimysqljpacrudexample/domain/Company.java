package com.almielka.springbootrestapimysqljpacrudexample.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * JavaBean domain object representing a Company.
 * Each Company has an id property {@code id} and a name property {@code name},
 * than extends class with this properties {@link AbstractNameEntity}.
 * Also each Company has:
 * <ul>
 * <li>imagery type {@code companyType}, there are three possible types {@link CompanyType};</li>
 * <li>collection of products {@code productSet} {@link Product}.</li>
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
@Table(name = "company", indexes = {@Index(columnList = "name", unique = true)})
public class Company extends AbstractNameEntity {

    @Column(name = "imagery_type")
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Product> productSet = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Company company = (Company) o;

        return companyType == company.companyType;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (companyType != null ? companyType.hashCode() : 0);
        return result;
    }
}