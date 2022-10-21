package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    PetService petService;
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        System.out.println(scheduleDTO.getPetIds());
        return mapScheduleToDTO(scheduleService.save(mapDTOToSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAll().stream().map(this::mapScheduleToDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.getByPetId(petId).stream().map(this::mapScheduleToDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getByEmployeeId(employeeId).stream().map(this::mapScheduleToDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getByCustomerId(customerId).stream().map(this::mapScheduleToDTO).collect(Collectors.toList());
    }

    public ScheduleDTO mapScheduleToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        modelMapper.map(schedule, scheduleDTO);
        List<Long> petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        scheduleDTO.setPetIds(petIds);
        List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(employeeIds);
        return scheduleDTO;
    }
    public Schedule mapDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        modelMapper.map(scheduleDTO, schedule);
        List<Pet> petIds = scheduleDTO.getPetIds().stream().map(index -> petService.findById(index).get()).collect(Collectors.toList());
        schedule.setPets(petIds);
        System.out.println(schedule.getPets());
        List<Employee> employeeIds = scheduleDTO.getEmployeeIds().stream().map(index -> employeeService.get(index)).collect(Collectors.toList());
        schedule.setEmployees(employeeIds);
        return schedule;
    }
}
