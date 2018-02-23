package com.acyclictech.jhipster.ceshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Task entity.
 * @author The JHipster team.
 */
@ApiModel(description = "Task entity. @author The JHipster team.")
@Entity
@Table(name = "address_book")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "user_id")
    private String userId;

    @OneToMany(mappedBy = "addressBook")
    @JsonIgnore
    private Set<Entry> entries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public AddressBook title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public AddressBook userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<Entry> getEntries() {
        return entries;
    }

    public AddressBook entries(Set<Entry> entries) {
        this.entries = entries;
        return this;
    }

    public AddressBook addEntries(Entry entry) {
        this.entries.add(entry);
        entry.setAddressBook(this);
        return this;
    }

    public AddressBook removeEntries(Entry entry) {
        this.entries.remove(entry);
        entry.setAddressBook(null);
        return this;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressBook addressBook = (AddressBook) o;
        if (addressBook.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressBook.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressBook{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
