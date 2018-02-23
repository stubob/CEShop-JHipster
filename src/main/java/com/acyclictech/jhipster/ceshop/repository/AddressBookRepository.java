package com.acyclictech.jhipster.ceshop.repository;

import com.acyclictech.jhipster.ceshop.domain.AddressBook;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AddressBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook, Long>, JpaSpecificationExecutor<AddressBook> {

}
