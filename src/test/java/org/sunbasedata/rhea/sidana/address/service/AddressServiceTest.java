package org.sunbasedata.rhea.sidana.address.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sunbasedata.rhea.sidana.address.repository.AddressRepository;
import org.sunbasedata.rhea.sidana.address.repository.model.Address;
import org.sunbasedata.rhea.sidana.exception.UnableToSaveToDbException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    public void shouldNotAddToDBWhenUnableToSaveToDb() {
        // Arrange
        Address customerAddress = new Address(1L, "hno 1", "Street 1", "City", "State");
        when(addressRepository.findByAddress(
                "hno 1",
                "Street 1",
                "City",
                "State"
                )
        ).thenReturn(Optional.empty());
        when(addressRepository.save(any(Address.class))).thenReturn(null); // Simulate save failure

        // Act & Assert
        UnableToSaveToDbException unableToSaveToDbException = assertThrows(UnableToSaveToDbException.class, () ->
                addressService.addToDB(customerAddress)
        );

        String exception = "unable to save address to db";
        assertEquals(exception, unableToSaveToDbException.getMessage());
    }

    @Test
    public void shouldNotAddToDBWhenAddressAlreadyInDB() throws UnableToSaveToDbException {
        // Arrange
        Address customerAddress = new Address(
                1L,"Hno 1", "Street 1",
                "City", "State"
        );
        when(addressRepository.findByAddress(
                "Hno 1","Street 1", "City", "State"
        )).thenReturn(Optional.of(customerAddress));

        // Act
        Address result = addressService.addToDB(customerAddress);

        // Assert
        assertNotNull(result);
        assertEquals(customerAddress, result);
    }

    @Test
    public void testAddToDB_AddressNotInDB_Success() throws UnableToSaveToDbException {
        // Arrange
        Address customerAddress = new Address(1L, "HNO 1", "Street 1", "City", "State");
        when(addressRepository.save(any(Address.class))).thenReturn(customerAddress);
        when(addressRepository.findByAddress(
                "HNO 1", "Street 1",
                "City", "State"
        )).thenReturn(Optional.empty());

        // Act
        Address result = addressService.addToDB(customerAddress);

        // Assert
        assertNotNull(result);
        assertEquals(customerAddress, result);
    }
}