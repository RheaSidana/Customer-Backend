package org.sunbasedata.rhea.sidana.customer.view.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.sunbasedata.rhea.sidana.customer.repository.model.Customer;

import java.util.Objects;

public class Success {
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String message;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private Customer customer;

    public Success(String message, Customer customer) {
        this.message = message;
        this.customer = customer;
    }

    public Success() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Success create = (Success) o;
        return Objects.equals(message, create.message) && Objects.equals(customer, create.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, customer);
    }

    @Override
    public String toString() {
        return "Success{" +
                "message='" + message + '\'' +
                ", customer=" + customer +
                '}';
    }
}
