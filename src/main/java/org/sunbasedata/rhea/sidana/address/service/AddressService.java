package org.sunbasedata.rhea.sidana.address.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunbasedata.rhea.sidana.address.repository.AddressRepository;
import org.sunbasedata.rhea.sidana.address.repository.model.Address;
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
}
