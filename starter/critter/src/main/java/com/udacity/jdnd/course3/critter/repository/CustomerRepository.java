package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CustomerRepository {
    @PersistenceContext
    EntityManager entityManager;

    public void persist(Customer customer){
        entityManager.persist(customer);
        entityManager.flush();
    };
    public Customer find(Long id){
        return entityManager.find(Customer.class,id);
    };
    public List<Customer> getAll(){
        TypedQuery<Customer> query = entityManager.createNamedQuery("customer.getAll", Customer.class);
        return query.getResultList();
    }
    public Customer getOwnerByPet(Long id){
        TypedQuery<Customer> query = entityManager.createNamedQuery("customer.getByPet", Customer.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }
    public Customer merge(Customer customer){
        return entityManager.merge(customer);
    };
    public void delete(Long id){
        Customer customer = entityManager.find(Customer.class,id);
        entityManager.remove(customer);
    };

    public Optional<Customer> findById(long ownerId) {
        return Optional.of(find(ownerId));
    }
}
