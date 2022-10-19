package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class EmployeeRepository {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    ModelMapper modelMapper;
    public void persist(Employee employee){
        entityManager.persist(employee);
    };
    public Employee find(Long id){
        return entityManager.find(Employee.class,id);
    };
    public Employee merge(Employee employee){
        return entityManager.merge(employee);
    };
    void delete(Long id){
        Employee employee = entityManager.find(Employee.class,id);
        entityManager.remove(employee);
    };
    public List<Employee> getAll(){
        TypedQuery<Employee> query = entityManager.createNamedQuery("employee.getAll", Employee.class);
        return query.getResultList();

    }
    public List<Employee> bySkill(EmployeeSkill skill){
        TypedQuery<Employee> query = entityManager.createNamedQuery("employee.bySkill", Employee.class);
        query.setParameter("skill",skill);
        return query.getResultList();
    }
    public List<Employee> byDayAvailable(DayOfWeek day){
        TypedQuery<Employee> query = entityManager.createNamedQuery("employee.byDayAvailable", Employee.class);
        query.setParameter("day",day);
        return query.getResultList();
    }
    public List<EmployeeDTO> bySkillAndDayAvailable(Set<EmployeeSkill> skill, DayOfWeek day){
        TypedQuery<Employee> query = entityManager.createNamedQuery("employee.byDayAvailable", Employee.class);
        query.setParameter("day",day);
        TypedQuery<Employee> query2 = entityManager.createNamedQuery("employee.bySkill", Employee.class);
        query2.setParameter("skills",skill);
        List<Employee> quiry = query2.getResultList();
        quiry.removeIf(item -> !(query.getResultList().contains(item) && item.getSkills().containsAll(skill)));
        return quiry.stream().map(item -> modelMapper.map(item,EmployeeDTO.class)).collect(Collectors.toList());
    }
    public List<EmployeeDTO> bySkillAndDayAvailable(EmployeeRequestDTO requestDTO){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        System.out.println(root.get("daysAvailable"));
        query.select(root)
                .where(cb.in(root.get("daysAvailable")).value(requestDTO.getDate().getDayOfWeek()));
        return entityManager.createQuery(query).getResultList().stream().map(item -> modelMapper.map(item,EmployeeDTO.class)).collect(Collectors.toList());
    }
}
