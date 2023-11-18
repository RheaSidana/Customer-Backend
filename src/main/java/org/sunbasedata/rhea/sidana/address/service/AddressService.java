package org.sunbasedata.rhea.sidana.address.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunbasedata.rhea.sidana.address.repository.AddressRepository;
import org.sunbasedata.rhea.sidana.address.repository.model.Address;
import org.sunbasedata.rhea.sidana.exception.UnableToSaveToDbException;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address addToDB(Address customerAddress) throws UnableToSaveToDbException {
        Address address = addressRepository.save(customerAddress);
        if(address == null){
            throw new UnableToSaveToDbException("unable to save address to db");
        }
        return address;
    }
}
