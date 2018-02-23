package com.acyclictech.jhipster.ceshop.service.mapper;

import com.acyclictech.jhipster.ceshop.domain.*;
import com.acyclictech.jhipster.ceshop.service.dto.AddressBookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AddressBook and its DTO AddressBookDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AddressBookMapper extends EntityMapper<AddressBookDTO, AddressBook> {


    @Mapping(target = "entries", ignore = true)
    AddressBook toEntity(AddressBookDTO addressBookDTO);

    default AddressBook fromId(Long id) {
        if (id == null) {
            return null;
        }
        AddressBook addressBook = new AddressBook();
        addressBook.setId(id);
        return addressBook;
    }
}
