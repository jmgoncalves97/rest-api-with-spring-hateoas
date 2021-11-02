package com.example.payroll.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.payroll.assembler.EmployeeModelAssembler;
import com.example.payroll.exception.EmployeeNotFoundException;
import com.example.payroll.model.Employee;
import com.example.payroll.repository.EmployeeRepository;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class EmployeeController {
    
    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    @GetMapping("/employees")
    public
    List<EntityModel<Employee>> all() {

        List<EntityModel<Employee>> employees = repository.findAll()
            .stream()
            .map(employee -> assembler.toModel(employee))
        .collect(Collectors.toList());

        return employees;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/employees")
    EntityModel<Employee> newEmployee(@RequestBody Employee employee) {

        Employee save = repository.save(employee);
        return assembler.toModel(save);
    }

    @GetMapping("/employees/{id}")
    public
    EntityModel<Employee> getEmployee(@PathVariable Long id) throws Exception {

        Employee employee = repository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));

        return assembler.toModel(employee);
    }

    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @PutMapping("/employees/{id}")
    EntityModel<Employee> replacEmployee(@RequestBody Employee employeeParam,
                            @PathVariable Long id) throws Exception {

        Employee employee = repository.findById(id)
            .map(employeeMap -> {
                employeeMap.setName(employeeParam.getName());
                employeeMap.setRole(employeeParam.getRole());
                return repository.save(employeeMap);
        }).orElseThrow(() -> new EmployeeNotFoundException(id));

        return assembler.toModel(employee);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id){
        repository.deleteById(id);
    }
}
