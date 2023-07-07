package com.example.zalik_rsd.modal;

import jakarta.persistence.*;
import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_access")
public class EmployeeAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Employee Name is required")
    @Size(min = 10, max = 40, message = "Employee Name must be between 10 and 40 characters")
    @Column(nullable = false)
    private String name;

    @Column(name = "entryTime", nullable = false)
    private String entryTime;

    @Column(name = "exitTime", nullable = false)
    private String exitTime;

    @Min(value = 1, message = "Room Number must be greater than or equal to 1")
    @Column(name = "roomNumber", nullable = false)
    private int roomNumber;

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }


    // constructors, getters, setters
}