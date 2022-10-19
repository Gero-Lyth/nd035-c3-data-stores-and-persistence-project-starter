package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PetRepository {
    @PersistenceContext
    EntityManager entityManager;

    public void persist(Pet pet){
        entityManager.persist(pet);
    };
    public Pet find(Long id){
        return entityManager.find(Pet.class,id);
    };
    public Pet merge(Pet pet){
        return entityManager.merge(pet);
    };
    public void delete(Long id){
        Pet pet = entityManager.find(Pet.class,id);
        entityManager.remove(pet);
    };

    public List<Pet> getAll(){
        TypedQuery<Pet> query = entityManager.createNamedQuery("pet.getAll", Pet.class);
        return query.getResultList();
    }
    public List<Pet> getByOwner(Long id){
        TypedQuery<Pet> query = entityManager.createNamedQuery("pet.getByOwner", Pet.class);
        query.setParameter("id",id);
        return query.getResultList();
    }

    public Optional<Pet> findById(Long id) {
        return Optional.of(find(id));
    }
}
