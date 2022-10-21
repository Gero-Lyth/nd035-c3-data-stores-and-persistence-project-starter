package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return mapCustomerToDTO(customerService.save(modelMapper.map(customerDTO,Customer.class)));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = customerService.getAllOwners();
        return customerList.stream().map(this::mapCustomerToDTO).collect(Collectors.toList());
    }

    /**
     * Turns a customer to a DTO.
     * Code help by Muma, a Mentor on Udacity
     * @param customer
     * @return Customer DTO
     */

    public CustomerDTO mapCustomerToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        modelMapper.map(customer, customerDTO);
        List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return mapCustomerToDTO(customerService.getOwnerByPet(petId));
    }

    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployees(){
        return employeeService.getAll();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeService.save(employeeDTO),EmployeeDTO.class);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.get(employeeId);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.get(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.save(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService.findByThings(employeeDTO).stream().map(item -> modelMapper.map(item,EmployeeDTO.class)).collect(Collectors.toList());
    }
}
