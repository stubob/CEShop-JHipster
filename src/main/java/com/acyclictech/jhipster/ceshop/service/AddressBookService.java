package com.acyclictech.jhipster.ceshop.service;

import com.acyclictech.jhipster.ceshop.domain.AddressBook;
import com.acyclictech.jhipster.ceshop.repository.AddressBookRepository;
import com.acyclictech.jhipster.ceshop.service.dto.AddressBookDTO;
import com.acyclictech.jhipster.ceshop.service.mapper.AddressBookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AddressBook.
 */
@Service
@Transactional
public class AddressBookService {

    private final Logger log = LoggerFactory.getLogger(AddressBookService.class);

    private final AddressBookRepository addressBookRepository;

    private final AddressBookMapper addressBookMapper;

    public AddressBookService(AddressBookRepository addressBookRepository, AddressBookMapper addressBookMapper) {
        this.addressBookRepository = addressBookRepository;
        this.addressBookMapper = addressBookMapper;
    }

    /**
     * Save a addressBook.
     *
     * @param addressBookDTO the entity to save
     * @return the persisted entity
     */
    public AddressBookDTO save(AddressBookDTO addressBookDTO) {
        log.debug("Request to save AddressBook : {}", addressBookDTO);
        AddressBook addressBook = addressBookMapper.toEntity(addressBookDTO);
        addressBook = addressBookRepository.save(addressBook);
        return addressBookMapper.toDto(addressBook);
    }

    /**
     * Get all the addressBooks.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AddressBookDTO> findAll() {
        log.debug("Request to get all AddressBooks");
        return addressBookRepository.findAll().stream()
            .map(addressBookMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one addressBook by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AddressBookDTO findOne(Long id) {
        log.debug("Request to get AddressBook : {}", id);
        AddressBook addressBook = addressBookRepository.findOne(id);
        return addressBookMapper.toDto(addressBook);
    }

    /**
     * Delete the addressBook by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AddressBook : {}", id);
        addressBookRepository.delete(id);
    }
}
