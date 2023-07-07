package com.example.zalik_rsd.controller;

import com.example.zalik_rsd.modal.EmployeeAccess;
import com.example.zalik_rsd.repository.EmployeeAccessRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@CrossOrigin(origins = "*",
        allowedHeaders = "*")
@RestController
public class EmployeeEntityRestController {

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EmployeeAccessRepository employeeAccessRepository;


    @GetMapping("/getAllEmployeeEntity")
    public ResponseEntity<String> getAllEmployeeEntity() {
        try {

            List<EmployeeAccess> farmEntities = employeeAccessRepository.findAll();
            String json = objectMapper.writeValueAsString(farmEntities);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(json);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getEmployeeEntityById(@PathVariable("id") int id) {
        try {
            id--;
            if (id<0){
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("Min id-1"));
            }
            List<EmployeeAccess> farmEntities = employeeAccessRepository.findAll();
            if (id>farmEntities.size()){
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("Max id-"+farmEntities.size()+1));
            }
            EmployeeAccess employeeAccess1 = farmEntities.get(id);
            String json = objectMapper.writeValueAsString(employeeAccess1);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(json);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/addEmployeeEntity")
    public ResponseEntity<String> createEmployeeEntity(@Valid @RequestBody EmployeeAccess employeeAccess, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        employeeAccessRepository.save(employeeAccess);

        String responseJson = null;
        try {
            responseJson = objectMapper.writeValueAsString(employeeAccess);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseJson);
    }

    @DeleteMapping("/removeEmployeeEntity/{id}")
    public ResponseEntity<String> deleteEntityById(@PathVariable("id") Long id) {
        Optional<EmployeeAccess> entityOptional = employeeAccessRepository.findById(id);

        if (entityOptional.isPresent()) {
            employeeAccessRepository.deleteById(id);
            return ResponseEntity.ok("Farm entity with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}