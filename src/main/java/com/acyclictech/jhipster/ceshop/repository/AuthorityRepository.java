package com.acyclictech.jhipster.ceshop.repository;

import com.acyclictech.jhipster.ceshop.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
