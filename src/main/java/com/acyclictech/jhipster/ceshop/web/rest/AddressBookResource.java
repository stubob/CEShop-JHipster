package com.acyclictech.jhipster.ceshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acyclictech.jhipster.ceshop.service.AddressBookService;
import com.acyclictech.jhipster.ceshop.web.rest.errors.BadRequestAlertException;
import com.acyclictech.jhipster.ceshop.web.rest.util.HeaderUtil;
import com.acyclictech.jhipster.ceshop.service.dto.AddressBookDTO;
import com.acyclictech.jhipster.ceshop.service.dto.AddressBookCriteria;
import com.acyclictech.jhipster.ceshop.service.AddressBookQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AddressBook.
 */
@RestController
@RequestMapping("/api")
public class AddressBookResource {

    private final Logger log = LoggerFactory.getLogger(AddressBookResource.class);

    private static final String ENTITY_NAME = "addressBook";

    private final AddressBookService addressBookService;

    private final AddressBookQueryService addressBookQueryService;

    public AddressBookResource(AddressBookService addressBookService, AddressBookQueryService addressBookQueryService) {
        this.addressBookService = addressBookService;
        this.addressBookQueryService = addressBookQueryService;
    }

    /**
     * POST  /address-books : Create a new addressBook.
     *
     * @param addressBookDTO the addressBookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addressBookDTO, or with status 400 (Bad Request) if the addressBook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/address-books")
    @Timed
    public ResponseEntity<AddressBookDTO> createAddressBook(@RequestBody AddressBookDTO addressBookDTO) throws URISyntaxException {
        log.debug("REST request to save AddressBook : {}", addressBookDTO);
        if (addressBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new addressBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressBookDTO result = addressBookService.save(addressBookDTO);
        return ResponseEntity.created(new URI("/api/address-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /address-books : Updates an existing addressBook.
     *
     * @param addressBookDTO the addressBookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addressBookDTO,
     * or with status 400 (Bad Request) if the addressBookDTO is not valid,
     * or with status 500 (Internal Server Error) if the addressBookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/address-books")
    @Timed
    public ResponseEntity<AddressBookDTO> updateAddressBook(@RequestBody AddressBookDTO addressBookDTO) throws URISyntaxException {
        log.debug("REST request to update AddressBook : {}", addressBookDTO);
        if (addressBookDTO.getId() == null) {
            return createAddressBook(addressBookDTO);
        }
        AddressBookDTO result = addressBookService.save(addressBookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addressBookDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /address-books : get all the addressBooks.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of addressBooks in body
     */
    @GetMapping("/address-books")
    @Timed
    public ResponseEntity<List<AddressBookDTO>> getAllAddressBooks(AddressBookCriteria criteria) {
        log.debug("REST request to get AddressBooks by criteria: {}", criteria);
        List<AddressBookDTO> entityList = addressBookQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /address-books/:id : get the "id" addressBook.
     *
     * @param id the id of the addressBookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressBookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/address-books/{id}")
    @Timed
    public ResponseEntity<AddressBookDTO> getAddressBook(@PathVariable Long id) {
        log.debug("REST request to get AddressBook : {}", id);
        AddressBookDTO addressBookDTO = addressBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(addressBookDTO));
    }

    /**
     * DELETE  /address-books/:id : delete the "id" addressBook.
     *
     * @param id the id of the addressBookDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/address-books/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddressBook(@PathVariable Long id) {
        log.debug("REST request to delete AddressBook : {}", id);
        addressBookService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
