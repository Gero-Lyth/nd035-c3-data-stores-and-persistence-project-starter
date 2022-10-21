package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
                name = "schedule.getAll",
                query = "select s from Schedule s"),

        @NamedQuery(
                name = "schedule.getByEmployee",
                query = "select s from Schedule s join s.employees e where e.id = :id"),

        @NamedQuery(
                name = "schedule.getByPet",
                query = "select s from Schedule s join s.pets p where p.id = :id"),
        @NamedQuery(
                name = "schedule.getByCustomer",
                query = "select s from Schedule s join s.pets p where p.owner.id = :id")
}
)
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToMany(fetch = FetchType.LAZY)
    @Column(name = "employee_id", nullable = false)
    private List<Employee> employees = new ArrayList<>();
    @ManyToMany
    @Column(name = "pet_id", nullable = false)
    private List<Pet> pets = new ArrayList<>();
    private LocalDate date;
    @ElementCollection(targetClass = EmployeeSkill.class)
    @JoinTable(name = "schedule_skills", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "skill", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
