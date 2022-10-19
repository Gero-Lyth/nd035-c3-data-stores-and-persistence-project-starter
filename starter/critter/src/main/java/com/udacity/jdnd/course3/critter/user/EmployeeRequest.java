package com.udacity.jdnd.course3.critter.user;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class EmployeeRequest {
    @Id
    @GeneratedValue
    private Long id;
    @ElementCollection(targetClass = EmployeeSkill.class)
    @JoinTable(name = "request_skills", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "skill", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;
    private LocalDate date;

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
