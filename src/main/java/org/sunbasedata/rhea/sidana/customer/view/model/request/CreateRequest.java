package org.sunbasedata.rhea.sidana.customer.view.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class CreateRequest {
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "cannot be null")
    private String first_name;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "cannot be null")
    private String last_name;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String street;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String address;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String city;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String state;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String email;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String phone;

    public CreateRequest() {
    }

    public CreateRequest(String first_name, String last_name, String street, String address, String city, String state, String email, String phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.street = street;
        this.address = address;
        this.city = city;
        this.state = state;
        this.email = email;
        this.phone = phone;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateRequest create = (CreateRequest) o;
        return Objects.equals(first_name, create.first_name) && Objects.equals(last_name, create.last_name) && Objects.equals(street, create.street) && Objects.equals(address, create.address) && Objects.equals(city, create.city) && Objects.equals(state, create.state) && Objects.equals(email, create.email) && Objects.equals(phone, create.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, last_name, street, address, city, state, email, phone);
    }

    @Override
    public String toString() {
        return "Create{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", street='" + street + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
