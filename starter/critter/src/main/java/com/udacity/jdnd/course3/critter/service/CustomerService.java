package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    public List<Customer> getAllOwners(){
        return customerRepository.getAll();
    }
    public Customer save(Customer customer) {
        customer = customerRepository.merge(customer);
        customerRepository.persist(customer);
        return customer;
    }

    public Customer getOwnerByPet(long petId) {
        return  customerRepository.getOwnerByPet(petId);
    }
    public Customer get(long id){
        return customerRepository.find(id);
    }
}
