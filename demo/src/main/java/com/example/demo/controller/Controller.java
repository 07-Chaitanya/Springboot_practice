package com.example.demo.controller;

import com.example.demo.model.EmployeeModel;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Hello")
public class Controller {
    private EmployeeRepository employeeRepository;

    public Controller(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<EmployeeModel> getEmployee(){
        return employeeRepository.findAll();
    }
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeModel> getEmployee(@PathVariable Long id){
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/add")
    public EmployeeModel addEmployee(@RequestBody EmployeeModel employeeModel){
        return employeeRepository.save(employeeModel);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeModel> updateEmployee(@PathVariable Long id, @RequestBody EmployeeModel employeeModel ){
        return employeeRepository.findById(id)
 .map(existing -> {
            existing.setName(employeeModel.getName());
            existing.setDepartment(employeeModel.getDepartment());
            EmployeeModel updated = employeeRepository.save(existing);
            return ResponseEntity.ok(updated);
        })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (!employeeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}