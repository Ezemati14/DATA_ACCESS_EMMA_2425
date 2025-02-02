package org.example.springprueba.controller;

import jakarta.validation.Valid;
import org.example.springprueba.model.dao.IDeptDap;
import org.example.springprueba.model.dao.IEmployeDao;
import org.example.springprueba.model.entities.Dept;
import org.example.springprueba.model.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ViewController {

    @Autowired
    private IDeptDap deptDAO;
    @Autowired
    private IEmployeDao employeDap;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    //**************** DEPARTAMENTOS *************************

    @GetMapping("/verdepartamentos")
    public String viewDept(Model model) {
        List<Dept> depts = (List<Dept>) deptDAO.findAll();
        System.out.println(depts);
        model.addAttribute("depts", depts);

        return "verdepartamentos";
    }

    @GetMapping("/altadepartamento")
    public String viewAltaDept(Model model) {
        model.addAttribute("dept", new Dept());
        return "altadepartamento";
    }

    @PostMapping("/altadepartamento")
    public String addDept(@Valid @ModelAttribute("dept") Dept dept, Model model, BindingResult result) {

        if(!deptDAO.existsById(dept.getId())) {
            deptDAO.save(dept);
            //model.addAttribute("mensajeCorrecto", "Departamento creado!!!");
            return "redirect:/verdepartamentos";
        }else {
            model.addAttribute("errorDepartamento", "Ya existe un dept con ese id");
        }
        return "altadepartamento";
    }

    //**************** EMPLEADOS *************************

    @GetMapping("/verempleados")
    public String viewEmpleoyees(@RequestParam(value = "depno", required = false) Integer depno, Model model) {
        List<Employee> employees;

        if (depno != null) {
            employees = employeDap.findByDept_Id(depno);
        }else {
            employees = employeDap.findAll();
        }

        if (employees == null || employees.isEmpty()) {
            throw new RuntimeException("No se encontraron empleados.");
        }
        model.addAttribute("employees", employees);
        model.addAttribute("departamentos", deptDAO.findAll());
        return "verempleados";
    }

    @GetMapping("/altaempleado")
    public String viewAltaEmpleado(Model model) {
        model.addAttribute("emp", new Employee());
        return "altaempleado";
    }

    @PostMapping("/altaempleado")
    public String addEmpleado(@Valid @ModelAttribute("emp") Employee emp, Model model, BindingResult result) {

        if(employeDap.findById(emp.getId()).isPresent()) {
            model.addAttribute("errorEmpleado", "Ya existe un empleado con ese id");
            return "altaempleado";
        }
        employeDap.save(emp);
        return "redirect:/verempleados";
    }

    @GetMapping("/verempleado")
    public String mostrarEmpleado(Model model, @RequestParam(name = "id", required = true) int id) {
        Optional<Employee> empleado = employeDap.findById(id);
        if(empleado.isPresent()) {
            model.addAttribute("empleado", empleado.get());
            return "verempleado";
        }else {
            model.addAttribute("mensaje", "No existe un empleado con ese id");
            return "verempleados";
        }

    }

}