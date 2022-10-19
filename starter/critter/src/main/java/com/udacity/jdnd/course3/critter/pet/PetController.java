package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PetService petService;
    @Autowired
    CustomerService customerService;
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return modelMapper.map(petService.save(modelMapper.map(petDTO,Pet.class), petDTO.getOwnerId()), PetDTO.class);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return modelMapper.map(petService.findById(petId).get(),PetDTO.class);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.findAllPets().stream().map(item -> modelMapper.map(item, PetDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.findPetsByOwnerId(ownerId).stream().map(item -> modelMapper.map(item, PetDTO.class)).collect(Collectors.toList());
    }
}
