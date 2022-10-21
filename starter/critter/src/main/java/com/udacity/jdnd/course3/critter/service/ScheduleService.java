package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;
    public List<Schedule> getAll() {
        return scheduleRepository.getAll();
    }

    public List<Schedule> getByEmployeeId(long id) {
        return scheduleRepository.getByEmployeeId(id);
    }
    public Schedule save(Schedule schedule) {
        System.out.println(schedule.getEmployees());
        schedule = scheduleRepository.merge(schedule);
        System.out.println(schedule.getEmployees());
        scheduleRepository.persist(schedule);
        System.out.println(schedule.getEmployees());
        return schedule;
    }

    public List<Schedule> getByPetId(long petId) {
        return scheduleRepository.getByPetId(petId);
    }

    public List<Schedule> getByCustomerId(long customerId) {
        return scheduleRepository.getByCustomerId(customerId);
    }
}
