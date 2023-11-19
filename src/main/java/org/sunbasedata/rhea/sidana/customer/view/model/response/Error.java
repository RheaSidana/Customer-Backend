package org.sunbasedata.rhea.sidana.customer.view.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Error {
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String message;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String error;

    public Error() {
    }

    public Error(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error1 = (Error) o;
        return Objects.equals(message, error1.message) && Objects.equals(error, error1.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, error);
    }

    @Override
    public String toString() {
        return "Error{" +
                "message='" + message + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
