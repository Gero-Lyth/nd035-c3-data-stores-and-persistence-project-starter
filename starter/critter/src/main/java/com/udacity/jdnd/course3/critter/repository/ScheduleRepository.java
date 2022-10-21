package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class ScheduleRepository {    
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    ModelMapper modelMapper;
    public void persist(Schedule schedule){
        entityManager.persist(schedule);
    };
    public Schedule find(Long id){
        return entityManager.find(Schedule.class,id);
    };
    public Schedule merge(Schedule schedule){
        return entityManager.merge(schedule);
    };
    void delete(Long id){
        Schedule schedule = entityManager.find(Schedule.class,id);
        entityManager.remove(schedule);
    };
    public List<Schedule> getAll(){
        TypedQuery<Schedule> query = entityManager.createNamedQuery("schedule.getAll", Schedule.class);
        return query.getResultList();

    }
    public List<Schedule> getByEmployeeId(long id){
        TypedQuery<Schedule> query = entityManager.createNamedQuery("schedule.getByEmployee", Schedule.class);
        query.setParameter("id",id);
        return query.getResultList();
    }

    public List<Schedule> getByPetId(long petId) {
        TypedQuery<Schedule> query = entityManager.createNamedQuery("schedule.getByPet", Schedule.class);
        query.setParameter("id",petId);
        return query.getResultList();
    }

    public List<Schedule> getByCustomerId(long customerId) {
        TypedQuery<Schedule> query = entityManager.createNamedQuery("schedule.getByCustomer", Schedule.class);
        query.setParameter("id",customerId);
        return query.getResultList();
    }
}
