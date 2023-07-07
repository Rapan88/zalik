package com.example.zalik_rsd.controller;

import com.example.zalik_rsd.modal.EmployeeAccess;
import com.example.zalik_rsd.repository.EmployeeAccessRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequestMapping("/api")
@CrossOrigin(origins = "*",
        allowedHeaders = "*")
@RestController
public class FarmEntityRestController {

    @Autowired
    private EmployeeAccess employeeAccess;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EmployeeAccessRepository employeeAccessRepository;


    @GetMapping("/getAllEmployeeEntity")
    public ResponseEntity<String> getAllFarmEntity() {
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


    @PutMapping("/addFarmEntity")
    public ResponseEntity<String> createFarmEntity(@Valid @RequestBody EmployeeAccess farm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        farmRepository.save(farm);

        String responseJson = null;
        try {
            responseJson = objectMapper.writeValueAsString(farm);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseJson);
    }

    @DeleteMapping("/removeFarmEntity/{id}")
    public ResponseEntity<String> deleteEntityById(@PathVariable("id") Long id) {
        Optional<FarmEntity> entityOptional = farmRepository.findById(id);

        if (entityOptional.isPresent()) {
            farmRepository.deleteById(id);
            return ResponseEntity.ok("Farm entity with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}