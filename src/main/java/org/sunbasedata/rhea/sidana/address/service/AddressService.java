package org.sunbasedata.rhea.sidana.address.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunbasedata.rhea.sidana.address.repository.AddressRepository;
import org.sunbasedata.rhea.sidana.address.repository.model.Address;
import org.sunbasedata.rhea.sidana.customer.view.model.request.CreateRequest;
import org.sunbasedata.rhea.sidana.exception.UnableToSaveToDbException;

import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address addToDB(Address customerAddress) throws UnableToSaveToDbException {
        Optional<Address> alreadyInDB = isAlreadyInDB(customerAddress);
        if(alreadyInDB.isPresent()){
            return alreadyInDB.get();
        }


        Address address = addressRepository.save(customerAddress);
        if(address == null){
            throw new UnableToSaveToDbException("unable to save address to db");
        }
        return address;
    }

    private Optional<Address> isAlreadyInDB(Address address){
        Optional<Address> byAddress = addressRepository.findByAddress(
                address.getAddress(),
                address.getStreet(),
                address.getCity(),
                address.getState()
        );


        return byAddress;
    }

    public Address getAddress(Long addressID) {
        Optional<Address> addressById = addressRepository.findById(addressID);
        return addressById.get();
    }

    public Address createNewIfRequired(
            Address address,
            CreateRequest updateCustomer
    ) throws UnableToSaveToDbException {
        if(!(
                address.getAddress().equals(updateCustomer.getAddress()) &&
                address.getCity().equals(updateCustomer.getCity()) &&
                address.getState().equals(updateCustomer.getState()) &&
                address.getStreet().equals(updateCustomer.getStreet())
        )){
            return addToDB(new Address(
                    0L,
                    updateCustomer.getAddress(),
                    updateCustomer.getStreet(),
                    updateCustomer.getCity(),
                    updateCustomer.getState()
            ));
        }

        return address;
    }
}
