import com.example.zalik_rsd.modal.EmployeeAccess;
import com.example.zalik_rsd.repository.EmployeeAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/employee-access")
public class EmployeeAccessController {

    private final EmployeeAccessRepository employeeAccessRepository;

    @Autowired
    public EmployeeAccessController(EmployeeAccessRepository employeeAccessRepository) {
        this.employeeAccessRepository = employeeAccessRepository;
    }

    @GetMapping("/")
    public String getAllEmployeeAccess(Model model) {
        List<EmployeeAccess> employeeAccessList = employeeAccessRepository.findAll();
        model.addAttribute("employeeAccessList", employeeAccessList);
        return "employee-access/list";
    }

    @GetMapping("/new")
    public String showAddForm(EmployeeAccess employeeAccess) {
        return "employee-access/add";
    }

    @PostMapping("/new")
    public String addEmployeeAccess(@Valid EmployeeAccess employeeAccess, BindingResult result) {
        if (result.hasErrors()) {
            return "employee-access/add";
        }

        employeeAccessRepository.save(employeeAccess);
        return "redirect:/employee-access/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        EmployeeAccess employeeAccess = employeeAccessRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee access ID: " + id));
        model.addAttribute("employeeAccess", employeeAccess);
        return "employee-access/edit";
    }

    @PostMapping("/update/{id}")
    public String updateEmployeeAccess(@PathVariable("id") long id, @Valid EmployeeAccess employeeAccess,
                                       BindingResult result) {
        if (result.hasErrors()) {
            employeeAccess.setId(id);
            return "employee-access/edit";
        }

        employeeAccessRepository.save(employeeAccess);
        return "redirect:/employee-access/";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployeeAccess(@PathVariable("id") long id) {
        EmployeeAccess employeeAccess = employeeAccessRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee access ID: " + id));
        employeeAccessRepository.delete(employeeAccess);
        return "redirect:/employee-access/";
    }
}
