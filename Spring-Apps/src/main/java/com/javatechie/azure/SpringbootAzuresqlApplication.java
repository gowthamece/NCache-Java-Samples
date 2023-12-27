package com.javatechie.azure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@SpringBootApplication
@EnableCaching
@RestController
public class SpringbootAzuresqlApplication 
{
    @Autowired
    private EmployeeRepository repository;
     @Autowired
    private EmployeeService service;
   
     @PostMapping("/employee")
     public Employee addEmployee(@RequestBody Employee employee) {
         return service.save(employee);
     }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return repository.findAll();
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringbootAzuresqlApplication.class, args);
    }
      @GetMapping("/employees/id")
    public Employee getEmployeesById(@RequestParam int id) {     
        return  service.get(id);
    }

}

@Service
class EmployeeService {
    @Autowired
    private EmployeeRepository repo;

    public List<Employee> listAll() 
    {
        return repo.findAll();
    }
    
    @CachePut(value = "employee", key = "#employee.id")
    public Employee save(Employee employee) {
        repo.save(employee);
        return employee;
    }


    @Cacheable(value = "employee", key = "#id")
    public Employee get(int id) 
    {
        return repo.findById(id).get();
    }

}
