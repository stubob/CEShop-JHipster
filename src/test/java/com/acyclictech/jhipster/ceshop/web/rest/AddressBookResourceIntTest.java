package com.acyclictech.jhipster.ceshop.web.rest;

import com.acyclictech.jhipster.ceshop.JhipsterSampleApplicationApp;

import com.acyclictech.jhipster.ceshop.domain.AddressBook;
import com.acyclictech.jhipster.ceshop.domain.Entry;
import com.acyclictech.jhipster.ceshop.repository.AddressBookRepository;
import com.acyclictech.jhipster.ceshop.service.AddressBookService;
import com.acyclictech.jhipster.ceshop.service.dto.AddressBookDTO;
import com.acyclictech.jhipster.ceshop.service.mapper.AddressBookMapper;
import com.acyclictech.jhipster.ceshop.web.rest.errors.ExceptionTranslator;
import com.acyclictech.jhipster.ceshop.service.dto.AddressBookCriteria;
import com.acyclictech.jhipster.ceshop.service.AddressBookQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.acyclictech.jhipster.ceshop.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AddressBookResource REST controller.
 *
 * @see AddressBookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class AddressBookResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private AddressBookQueryService addressBookQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAddressBookMockMvc;

    private AddressBook addressBook;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddressBookResource addressBookResource = new AddressBookResource(addressBookService, addressBookQueryService);
        this.restAddressBookMockMvc = MockMvcBuilders.standaloneSetup(addressBookResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressBook createEntity(EntityManager em) {
        AddressBook addressBook = new AddressBook()
            .title(DEFAULT_TITLE)
            .userId(DEFAULT_USER_ID);
        return addressBook;
    }

    @Before
    public void initTest() {
        addressBook = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddressBook() throws Exception {
        int databaseSizeBeforeCreate = addressBookRepository.findAll().size();

        // Create the AddressBook
        AddressBookDTO addressBookDTO = addressBookMapper.toDto(addressBook);
        restAddressBookMockMvc.perform(post("/api/address-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressBookDTO)))
            .andExpect(status().isCreated());

        // Validate the AddressBook in the database
        List<AddressBook> addressBookList = addressBookRepository.findAll();
        assertThat(addressBookList).hasSize(databaseSizeBeforeCreate + 1);
        AddressBook testAddressBook = addressBookList.get(addressBookList.size() - 1);
        assertThat(testAddressBook.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAddressBook.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createAddressBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressBookRepository.findAll().size();

        // Create the AddressBook with an existing ID
        addressBook.setId(1L);
        AddressBookDTO addressBookDTO = addressBookMapper.toDto(addressBook);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressBookMockMvc.perform(post("/api/address-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressBook in the database
        List<AddressBook> addressBookList = addressBookRepository.findAll();
        assertThat(addressBookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAddressBooks() throws Exception {
        // Initialize the database
        addressBookRepository.saveAndFlush(addressBook);

        // Get all the addressBookList
        restAddressBookMockMvc.perform(get("/api/address-books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())));
    }

    @Test
    @Transactional
    public void getAddressBook() throws Exception {
        // Initialize the database
        addressBookRepository.saveAndFlush(addressBook);

        // Get the addressBook
        restAddressBookMockMvc.perform(get("/api/address-books/{id}", addressBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addressBook.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()));
    }

    @Test
    @Transactional
    public void getAllAddressBooksByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        addressBookRepository.saveAndFlush(addressBook);

        // Get all the addressBookList where title equals to DEFAULT_TITLE
        defaultAddressBookShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the addressBookList where title equals to UPDATED_TITLE
        defaultAddressBookShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllAddressBooksByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        addressBookRepository.saveAndFlush(addressBook);

        // Get all the addressBookList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultAddressBookShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the addressBookList where title equals to UPDATED_TITLE
        defaultAddressBookShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllAddressBooksByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressBookRepository.saveAndFlush(addressBook);

        // Get all the addressBookList where title is not null
        defaultAddressBookShouldBeFound("title.specified=true");

        // Get all the addressBookList where title is null
        defaultAddressBookShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressBooksByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        addressBookRepository.saveAndFlush(addressBook);

        // Get all the addressBookList where userId equals to DEFAULT_USER_ID
        defaultAddressBookShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the addressBookList where userId equals to UPDATED_USER_ID
        defaultAddressBookShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAddressBooksByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        addressBookRepository.saveAndFlush(addressBook);

        // Get all the addressBookList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultAddressBookShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the addressBookList where userId equals to UPDATED_USER_ID
        defaultAddressBookShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllAddressBooksByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressBookRepository.saveAndFlush(addressBook);

        // Get all the addressBookList where userId is not null
        defaultAddressBookShouldBeFound("userId.specified=true");

        // Get all the addressBookList where userId is null
        defaultAddressBookShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressBooksByEntriesIsEqualToSomething() throws Exception {
        // Initialize the database
        Entry entries = EntryResourceIntTest.createEntity(em);
        em.persist(entries);
        em.flush();
        addressBook.addEntries(entries);
        addressBookRepository.saveAndFlush(addressBook);
        Long entriesId = entries.getId();

        // Get all the addressBookList where entries equals to entriesId
        defaultAddressBookShouldBeFound("entriesId.equals=" + entriesId);

        // Get all the addressBookList where entries equals to entriesId + 1
        defaultAddressBookShouldNotBeFound("entriesId.equals=" + (entriesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAddressBookShouldBeFound(String filter) throws Exception {
        restAddressBookMockMvc.perform(get("/api/address-books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAddressBookShouldNotBeFound(String filter) throws Exception {
        restAddressBookMockMvc.perform(get("/api/address-books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingAddressBook() throws Exception {
        // Get the addressBook
        restAddressBookMockMvc.perform(get("/api/address-books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddressBook() throws Exception {
        // Initialize the database
        addressBookRepository.saveAndFlush(addressBook);
        int databaseSizeBeforeUpdate = addressBookRepository.findAll().size();

        // Update the addressBook
        AddressBook updatedAddressBook = addressBookRepository.findOne(addressBook.getId());
        // Disconnect from session so that the updates on updatedAddressBook are not directly saved in db
        em.detach(updatedAddressBook);
        updatedAddressBook
            .title(UPDATED_TITLE)
            .userId(UPDATED_USER_ID);
        AddressBookDTO addressBookDTO = addressBookMapper.toDto(updatedAddressBook);

        restAddressBookMockMvc.perform(put("/api/address-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressBookDTO)))
            .andExpect(status().isOk());

        // Validate the AddressBook in the database
        List<AddressBook> addressBookList = addressBookRepository.findAll();
        assertThat(addressBookList).hasSize(databaseSizeBeforeUpdate);
        AddressBook testAddressBook = addressBookList.get(addressBookList.size() - 1);
        assertThat(testAddressBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAddressBook.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAddressBook() throws Exception {
        int databaseSizeBeforeUpdate = addressBookRepository.findAll().size();

        // Create the AddressBook
        AddressBookDTO addressBookDTO = addressBookMapper.toDto(addressBook);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAddressBookMockMvc.perform(put("/api/address-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressBookDTO)))
            .andExpect(status().isCreated());

        // Validate the AddressBook in the database
        List<AddressBook> addressBookList = addressBookRepository.findAll();
        assertThat(addressBookList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAddressBook() throws Exception {
        // Initialize the database
        addressBookRepository.saveAndFlush(addressBook);
        int databaseSizeBeforeDelete = addressBookRepository.findAll().size();

        // Get the addressBook
        restAddressBookMockMvc.perform(delete("/api/address-books/{id}", addressBook.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AddressBook> addressBookList = addressBookRepository.findAll();
        assertThat(addressBookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressBook.class);
        AddressBook addressBook1 = new AddressBook();
        addressBook1.setId(1L);
        AddressBook addressBook2 = new AddressBook();
        addressBook2.setId(addressBook1.getId());
        assertThat(addressBook1).isEqualTo(addressBook2);
        addressBook2.setId(2L);
        assertThat(addressBook1).isNotEqualTo(addressBook2);
        addressBook1.setId(null);
        assertThat(addressBook1).isNotEqualTo(addressBook2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressBookDTO.class);
        AddressBookDTO addressBookDTO1 = new AddressBookDTO();
        addressBookDTO1.setId(1L);
        AddressBookDTO addressBookDTO2 = new AddressBookDTO();
        assertThat(addressBookDTO1).isNotEqualTo(addressBookDTO2);
        addressBookDTO2.setId(addressBookDTO1.getId());
        assertThat(addressBookDTO1).isEqualTo(addressBookDTO2);
        addressBookDTO2.setId(2L);
        assertThat(addressBookDTO1).isNotEqualTo(addressBookDTO2);
        addressBookDTO1.setId(null);
        assertThat(addressBookDTO1).isNotEqualTo(addressBookDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(addressBookMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(addressBookMapper.fromId(null)).isNull();
    }
}
