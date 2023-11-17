package org.sunbasedaat.rhea.sidana.address.repository.model;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty
    private Long id;

    @Column(name = "address")
    @JsonProperty
    private String address;

    @Column(name = "street")
    @JsonProperty
    private String street;

    @Column(name = "city")
    @JsonProperty
    private String city;

    @Column(name = "state")
    @JsonProperty
    private String state;

    public Address() {
    }

    public Address(Long id, String address, String street, String city, String state) {
        this.id = id;
        this.address = address;
        this.street = street;
        this.city = city;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(id, address1.id) && Objects.equals(address, address1.address) && Objects.equals(street, address1.street) && Objects.equals(city, address1.city) && Objects.equals(state, address1.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, street, city, state);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

}
