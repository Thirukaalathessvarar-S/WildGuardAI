package com.example.wildlifeRescueCentre.Volunteer;

import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Volunteer {
    @Id
    private long volunteerId;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private String availability;
    @Column(nullable = false)
    private String role;
    @Column
    private List<Integer> assignedCases;

    // Getters and Setters
    public long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Integer> getAssignedCases() {
        return assignedCases;
    }

    public void setAssignedCases(List<Integer> assignedCases) {
        this.assignedCases = assignedCases;
    }
}
