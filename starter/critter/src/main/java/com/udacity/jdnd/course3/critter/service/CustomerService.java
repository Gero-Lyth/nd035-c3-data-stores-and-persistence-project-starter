package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CustomerRepository customerRepository;
    public List<CustomerDTO> getAllOwners(){
        List<CustomerDTO> dtos = customerRepository.getAll()
                .stream()
                .map(user -> modelMapper.map(user, CustomerDTO.class))
                .collect(Collectors.toList());
        return dtos;
    }
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO,Customer.class);
        customer = customerRepository.merge(customer);
        customerRepository.persist(customer);
        return modelMapper.map(customer,CustomerDTO.class);
    }

    public CustomerDTO getOwnerByPet(long petId) {
        return  modelMapper.map(customerRepository.getOwnerByPet(petId),CustomerDTO.class);
    }
    public CustomerDTO get(long id){
        return modelMapper.map(customerRepository.find(id),CustomerDTO.class);
    }
}
