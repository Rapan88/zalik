package com.example.zalik_rsd.controller;

import com.example.zalik_rsd.modal.EmployeeAccess;
import com.example.zalik_rsd.repository.EmployeeAccessRepository;
import com.example.zalik_rsd.service.GeneratePDF;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class EmployeeAccessController {

    private final EmployeeAccessRepository employeeAccessRepository;

    private GeneratePDF generatePDF = new GeneratePDF();

    @Autowired
    public EmployeeAccessController(EmployeeAccessRepository employeeAccessRepository) {
        this.employeeAccessRepository = employeeAccessRepository;
    }

    @GetMapping("/")
    public String getAllEmployeeAccess(Model model) {
        List<EmployeeAccess> employeeAccessList = employeeAccessRepository.findAll();
        model.addAttribute("employeeList", employeeAccessList);
        return "index";
    }

    @GetMapping("/add")
    public String showAddForm(EmployeeAccess employeeAccess) {
        return "create";
    }

    @PostMapping("/add")
    public String addEmployeeAccess(@Valid EmployeeAccess employeeAccess, BindingResult result) {
        if (result.hasErrors()) {
            return "create";
        }

        employeeAccessRepository.save(employeeAccess);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        EmployeeAccess employeeAccess = employeeAccessRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee access ID: " + id));
        model.addAttribute("employee", employeeAccess);
        return "update";
    }

    @PostMapping("/edit/{id}")
    public String updateEmployeeAccess(@PathVariable("id") long id, @Valid EmployeeAccess employeeAccess,
                                       BindingResult result) {
        if (result.hasErrors()) {
            employeeAccess.setId(id);
            return "update";
        }

        employeeAccessRepository.save(employeeAccess);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployeeAccess(@PathVariable("id") long id) {
        EmployeeAccess employeeAccess = employeeAccessRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee access ID: " + id));
        employeeAccessRepository.delete(employeeAccess);
        return "redirect:/";
    }

    @PostMapping("/filter")
    public String filterByRoomNumber(@RequestParam("roomNumber") int roomNumber, Model model) {
        if (("" + roomNumber).equals("")) {
            List<EmployeeAccess> employeeAccessList = employeeAccessRepository.findAll();
            model.addAttribute("employeeList", employeeAccessList);
            return "index";
        }
        List<EmployeeAccess> employeeList = employeeAccessRepository.findByRoomNumber(roomNumber);
        model.addAttribute("employeeList", employeeList);
        return "index";
    }

    @GetMapping("/download/pdf")
    public String getPDFFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            generatePDF.doDownload(request, response, employeeAccessRepository.findAll());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }
}
