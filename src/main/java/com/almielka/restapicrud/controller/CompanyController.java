package com.almielka.restapicrud.controller;

import com.almielka.restapicrud.domain.Company;
import com.almielka.restapicrud.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * @author Anna S. Almielka
 */

@RestController
@RequestMapping(value = "/api/companies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    //add Company
    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company newCompany) {
        Company company = companyService.saveAndFlush(newCompany);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }

    //edit Company by Id
    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompanyById(@PathVariable("id") Long id, @RequestBody Company updateCompany) {
        Optional<Company> optionalCompany = companyService.findById(id);
        return getCompanyResponseEntity(updateCompany, optionalCompany);
    }

    //edit Company by Name
    @PutMapping("/name/{name}")
    public ResponseEntity<Company> updateCompanyByName(@PathVariable("name") String name, @RequestBody Company updateCompany) {
        Optional<Company> optionalCompany = companyService.findByName(name);
        return getCompanyResponseEntity(updateCompany, optionalCompany);
    }

    private ResponseEntity<Company> getCompanyResponseEntity(Company updateCompany, Optional<Company> optionalCompany) {
        if (optionalCompany.isPresent()) {
            updateCompany.setId(optionalCompany.get().getId());
            companyService.saveAndFlush(updateCompany);
            return ResponseEntity.status(HttpStatus.OK).body(updateCompany);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //read Company by Id
    @GetMapping("/{id}")
    public ResponseEntity<Company> readCompanyById(@PathVariable("id") Long id) {
        Optional<Company> optionalCompany = companyService.findById(id);
        return optionalCompany.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //read Company by Name
    @GetMapping("/name/{name}")
    public ResponseEntity<Set<Company>> readCompanyByName(@PathVariable("name") String name) {
        Set<Company> companySet = companyService.findByNameContaining(name);
        if (companySet.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(companySet);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //delete Company by Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyById(@PathVariable("id") Long id) {
        try {
            companyService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //delete Company by Name
    @DeleteMapping("/name/{name}")
    public ResponseEntity<Void> deleteCompanyByName(@PathVariable("name") String name) {
        Optional<Company> optionalCompany = companyService.findByName(name);
        if (optionalCompany.isPresent()) {
            companyService.deleteById(optionalCompany.get().getId());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
