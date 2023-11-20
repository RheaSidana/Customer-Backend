package org.sunbasedata.rhea.sidana.customer.repository.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.sunbasedata.rhea.sidana.address.repository.model.Address;
import org.sunbasedata.rhea.sidana.contact.repository.model.Contact;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty
    private Long id;

    @Column(name = "first_name")
    @NotNull(message = "First Name cann't be null")
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last Name cann't be null")
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "address")
//    @NotNull(message = "Address cannot be null")
    @JsonProperty
    private Long address;

    @ManyToOne
    @JoinColumn(name = "address", referencedColumnName = "ID", insertable = false, updatable = false)
    private Address customerAddress;

    @Column(name = "contact")
//    @NotNull(message = "Contact cannot be null")
    @JsonProperty
    private Long contact;

    @ManyToOne
    @JoinColumn(name = "contact", referencedColumnName = "ID", insertable = false, updatable = false)
    private Contact customerContact;

    public Customer() {
    }

    public Customer(Long id, String firstName, String lastName, Long address, Long contact) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contact = contact;
    }

    public Customer(Customer customer) {
        this.id = customer.id;
        this.firstName = customer.firstName;
        this.lastName = customer.lastName;
        this.address = customer.address;
        this.contact = customer.contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getAddress() {
        return address;
    }

    public void setAddress(Long address) {
        this.address = address;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Contact getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(Contact customerContact) {
        this.customerContact = customerContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(address, customer.address) && Objects.equals(contact, customer.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, contact);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", customerAddress=" + customerAddress +
                ", contact=" + contact +
                ", customerContact=" + customerContact +
                '}';
    }
}
