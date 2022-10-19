package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.UserController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;
    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }
    public Pet save(Pet pet, long ownerId){
        Optional<Customer> customerOptional = customerRepository.findById(ownerId);
        if (customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            pet.setCustomer(customer);
            pet = petRepository.merge(pet);
            customer.addPets(pet);
            return pet;
        }
        throw new UnsupportedOperationException("Customer not available to save pet");
    }
    public Optional<Pet> findById(Long id){
        return petRepository.findById(id);
    }
    public List<Pet> findAllPets(){
        return petRepository.getAll();
    }
    public List<Pet> findPetsByOwnerId(Long ownerId){
        List<Pet> pets = customerRepository.findById(ownerId).get().getPets();
        return pets;
    }
}