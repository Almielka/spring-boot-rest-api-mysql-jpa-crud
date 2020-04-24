package com.almielka.springbootrestapimysqljpacrudexample.service;


import com.almielka.springbootrestapimysqljpacrudexample.domain.Company;

import java.util.Optional;
import java.util.Set;

/**
 * CompanyService interface includes additional methods only for Company
 *
 * @author Anna S. Almielka
 */

public interface CompanyService extends CommonService<Company> {
    Set<Company> findByNameContaining(String name);
    Optional<Company> findByName(String name);
}
