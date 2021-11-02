package com.example.payroll.assembler;

import com.example.payroll.controller.EmployeeController;
import com.example.payroll.model.Employee;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>>{

    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        try {
            return EntityModel.of(
                employee,
                linkTo(methodOn(EmployeeController.class)
                    .getEmployee(employee.getId()))
                    .withSelfRel(),
                linkTo(methodOn(EmployeeController.class)
                    .all())
                    .withRel("employees"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
