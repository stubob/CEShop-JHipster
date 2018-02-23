package com.acyclictech.jhipster.ceshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.acyclictech.jhipster.ceshop.domain.Entry;

import com.acyclictech.jhipster.ceshop.repository.EntryRepository;
import com.acyclictech.jhipster.ceshop.web.rest.errors.BadRequestAlertException;
import com.acyclictech.jhipster.ceshop.web.rest.util.HeaderUtil;
import com.acyclictech.jhipster.ceshop.service.dto.EntryDTO;
import com.acyclictech.jhipster.ceshop.service.mapper.EntryMapper;
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
 * REST controller for managing Entry.
 */
@RestController
@RequestMapping("/api")
public class EntryResource {

    private final Logger log = LoggerFactory.getLogger(EntryResource.class);

    private static final String ENTITY_NAME = "entry";

    private final EntryRepository entryRepository;

    private final EntryMapper entryMapper;

    public EntryResource(EntryRepository entryRepository, EntryMapper entryMapper) {
        this.entryRepository = entryRepository;
        this.entryMapper = entryMapper;
    }

    /**
     * POST  /entries : Create a new entry.
     *
     * @param entryDTO the entryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entryDTO, or with status 400 (Bad Request) if the entry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entries")
    @Timed
    public ResponseEntity<EntryDTO> createEntry(@RequestBody EntryDTO entryDTO) throws URISyntaxException {
        log.debug("REST request to save Entry : {}", entryDTO);
        if (entryDTO.getId() != null) {
            throw new BadRequestAlertException("A new entry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entry entry = entryMapper.toEntity(entryDTO);
        entry = entryRepository.save(entry);
        EntryDTO result = entryMapper.toDto(entry);
        return ResponseEntity.created(new URI("/api/entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entries : Updates an existing entry.
     *
     * @param entryDTO the entryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entryDTO,
     * or with status 400 (Bad Request) if the entryDTO is not valid,
     * or with status 500 (Internal Server Error) if the entryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entries")
    @Timed
    public ResponseEntity<EntryDTO> updateEntry(@RequestBody EntryDTO entryDTO) throws URISyntaxException {
        log.debug("REST request to update Entry : {}", entryDTO);
        if (entryDTO.getId() == null) {
            return createEntry(entryDTO);
        }
        Entry entry = entryMapper.toEntity(entryDTO);
        entry = entryRepository.save(entry);
        EntryDTO result = entryMapper.toDto(entry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entries : get all the entries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entries in body
     */
    @GetMapping("/entries")
    @Timed
    public List<EntryDTO> getAllEntries() {
        log.debug("REST request to get all Entries");
        List<Entry> entries = entryRepository.findAll();
        return entryMapper.toDto(entries);
        }

    /**
     * GET  /entries/:id : get the "id" entry.
     *
     * @param id the id of the entryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entries/{id}")
    @Timed
    public ResponseEntity<EntryDTO> getEntry(@PathVariable Long id) {
        log.debug("REST request to get Entry : {}", id);
        Entry entry = entryRepository.findOne(id);
        EntryDTO entryDTO = entryMapper.toDto(entry);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entryDTO));
    }

    /**
     * DELETE  /entries/:id : delete the "id" entry.
     *
     * @param id the id of the entryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        log.debug("REST request to delete Entry : {}", id);
        entryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
