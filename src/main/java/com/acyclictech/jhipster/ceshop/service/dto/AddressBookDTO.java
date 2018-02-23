package com.acyclictech.jhipster.ceshop.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AddressBook entity.
 */
public class AddressBookDTO implements Serializable {

    private Long id;

    private String title;

    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressBookDTO addressBookDTO = (AddressBookDTO) o;
        if(addressBookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressBookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressBookDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
