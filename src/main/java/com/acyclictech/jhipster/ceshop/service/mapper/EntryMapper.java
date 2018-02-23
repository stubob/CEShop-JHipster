package com.acyclictech.jhipster.ceshop.service.mapper;

import com.acyclictech.jhipster.ceshop.domain.*;
import com.acyclictech.jhipster.ceshop.service.dto.EntryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Entry and its DTO EntryDTO.
 */
@Mapper(componentModel = "spring", uses = {AddressBookMapper.class})
public interface EntryMapper extends EntityMapper<EntryDTO, Entry> {

    @Mapping(source = "addressBook.id", target = "addressBookId")
    EntryDTO toDto(Entry entry);

    @Mapping(source = "addressBookId", target = "addressBook")
    Entry toEntity(EntryDTO entryDTO);

    default Entry fromId(Long id) {
        if (id == null) {
            return null;
        }
        Entry entry = new Entry();
        entry.setId(id);
        return entry;
    }
}
