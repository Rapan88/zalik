package com.example.zalik_rsd.repository;

import com.example.zalik_rsd.modal.EmployeeAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeAccessRepository extends JpaRepository<EmployeeAccess, Long> {
    List<EmployeeAccess> findByRoomNumber(int roomNumber);
}
