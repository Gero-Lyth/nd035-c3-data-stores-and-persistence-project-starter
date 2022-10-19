package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    ModelMapper modelMapper;
    public Employee get(long employeeId) {
        return employeeRepository.find(employeeId);
    }

    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO,Employee.class);
        return this.save(employee);
    }
    public EmployeeDTO save(Employee employee) {
        employee = employeeRepository.merge(employee);
        employeeRepository.persist(employee);
        return modelMapper.map(employee,EmployeeDTO.class);
    }

    public List<EmployeeDTO> findByThings(EmployeeRequestDTO employeeDTO) {
        return employeeRepository.bySkillAndDayAvailable(employeeDTO.getSkills(),employeeDTO.getDate().getDayOfWeek());
    }

    public List<EmployeeDTO> getAll(){
        return employeeRepository.getAll().stream().map(item -> modelMapper.map(item,EmployeeDTO.class)).collect(Collectors.toList());
    }
}
