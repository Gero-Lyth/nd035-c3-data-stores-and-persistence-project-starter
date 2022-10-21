package com.udacity.jdnd.course3.critter.user;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@NamedQueries({
        @NamedQuery(
            name = "employee.getAll",
            query = "select c from Employee c"),
        @NamedQuery(
                name = "employee.bySkill",
                query = "select e from Employee e join e.skills s where s in :skills"),
        @NamedQuery(
                name = "employee.byDayAvailable",
                query = "select e from Employee e join e.daysAvailable d where :day = d"),
        @NamedQuery(
                name = "employee.bySkillAndDayAvailable",
                query = "select e from Employee e join e.skills s where :day in e.daysAvailable and s in :skills")
}
)
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private long id;
    @Nationalized
    private String name;
    @ElementCollection(targetClass = EmployeeSkill.class)
    @JoinTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "skill", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;
    @ElementCollection(targetClass = DayOfWeek.class)
    @JoinTable(name = "employee_days", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "days", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == Employee.class)
            return this.id == ((Employee) obj).id;
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
