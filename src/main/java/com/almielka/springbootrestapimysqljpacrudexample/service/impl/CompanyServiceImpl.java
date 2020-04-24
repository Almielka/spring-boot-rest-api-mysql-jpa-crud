package com.almielka.springbootrestapimysqljpacrudexample.service.impl;

import com.almielka.springbootrestapimysqljpacrudexample.domain.Company;
import com.almielka.springbootrestapimysqljpacrudexample.repository.CompanyRepository;
import com.almielka.springbootrestapimysqljpacrudexample.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * Extension of {@link CommonServiceImpl}
 * with explicitly defined the repository,
 * which is then called in the abstract constructor {@link CommonServiceImpl#CommonServiceImpl}
 * class {@code Company}, repository {@code CompanyRepository}
 *
 * @author Anna S. Almielka
 */

@Service
public class CompanyServiceImpl extends CommonServiceImpl<Company, CompanyRepository> implements CompanyService {
    public CompanyServiceImpl(CompanyRepository repository) {
        super(repository);
    }

    @Override
    public Set<Company> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    @Override
    public Optional<Company> findByName(String name) {
        return repository.findByName(name);
    }

}

