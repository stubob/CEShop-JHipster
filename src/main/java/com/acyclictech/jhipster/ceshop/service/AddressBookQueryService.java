package com.acyclictech.jhipster.ceshop.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.acyclictech.jhipster.ceshop.domain.AddressBook;
import com.acyclictech.jhipster.ceshop.domain.*; // for static metamodels
import com.acyclictech.jhipster.ceshop.repository.AddressBookRepository;
import com.acyclictech.jhipster.ceshop.service.dto.AddressBookCriteria;

import com.acyclictech.jhipster.ceshop.service.dto.AddressBookDTO;
import com.acyclictech.jhipster.ceshop.service.mapper.AddressBookMapper;

/**
 * Service for executing complex queries for AddressBook entities in the database.
 * The main input is a {@link AddressBookCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AddressBookDTO} or a {@link Page} of {@link AddressBookDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AddressBookQueryService extends QueryService<AddressBook> {

    private final Logger log = LoggerFactory.getLogger(AddressBookQueryService.class);


    private final AddressBookRepository addressBookRepository;

    private final AddressBookMapper addressBookMapper;

    public AddressBookQueryService(AddressBookRepository addressBookRepository, AddressBookMapper addressBookMapper) {
        this.addressBookRepository = addressBookRepository;
        this.addressBookMapper = addressBookMapper;
    }

    /**
     * Return a {@link List} of {@link AddressBookDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AddressBookDTO> findByCriteria(AddressBookCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<AddressBook> specification = createSpecification(criteria);
        return addressBookMapper.toDto(addressBookRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AddressBookDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AddressBookDTO> findByCriteria(AddressBookCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<AddressBook> specification = createSpecification(criteria);
        final Page<AddressBook> result = addressBookRepository.findAll(specification, page);
        return result.map(addressBookMapper::toDto);
    }

    /**
     * Function to convert AddressBookCriteria to a {@link Specifications}
     */
    private Specifications<AddressBook> createSpecification(AddressBookCriteria criteria) {
        Specifications<AddressBook> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AddressBook_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), AddressBook_.title));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserId(), AddressBook_.userId));
            }
            if (criteria.getEntriesId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getEntriesId(), AddressBook_.entries, Entry_.id));
            }
        }
        return specification;
    }

}
