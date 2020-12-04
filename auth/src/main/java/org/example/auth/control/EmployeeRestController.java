package org.example.auth.control;

import org.example.auth.model.Employee;
import org.example.auth.model.Person;
import org.example.auth.store.EmployeeRepository;
import org.example.auth.store.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeRepository store;
    private final PersonRepository persons;

    // EMPLOYEES

    @Autowired
    public EmployeeRestController(EmployeeRepository store, PersonRepository persons) {
        this.store = store;
        this.persons = persons;
    }

    @GetMapping("/employees")
    public List<Employee> getAll() {
        return store.findAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getById(@PathVariable int id) {
        Optional<Employee> rsl = store.findById(id);
        return new ResponseEntity<>(rsl.orElse(null),
                rsl.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/employees")
    public Employee add(@RequestBody Employee emp) {
        emp.setId(0);
        store.save(emp);
        return emp;
    }

    @PutMapping("/employees")
    public Employee update(@RequestBody Employee emp) {
        store.save(emp);
        return emp;
    }

    @DeleteMapping("/employees/{id}")
    public HttpStatus delete(@PathVariable int id) {
        boolean present = store.existsById(id);
        if(present) {
            store.deleteById(id);
        }
        return present ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }

    // EMPLOYEE PERSONS

    @GetMapping("/employees/{id}/persons")
    public ResponseEntity<List<Person>> getPersonsById(@PathVariable int id) {
        Optional<Employee> rsl = store.findById(id);
        if(rsl.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(rsl.get().getPersons(), HttpStatus.OK);
    }

    @PostMapping("/employees/{id}/persons")
    public ResponseEntity<Employee> addPerson(@PathVariable int id, @RequestBody Person person) {
        Optional<Employee> rsl = store.findById(id);
        if(rsl.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        persons.save(person);
        Employee emp = rsl.get();
        emp.getPersons().add(person);
        store.save(emp);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}/persons/{pid}")
    public ResponseEntity<Employee> addPerson(@PathVariable int id, @PathVariable int pid) {
        Optional<Employee> rsl = store.findById(id);
        Optional<Person> person = persons.findById(id);
        if(rsl.isEmpty() || person.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Employee emp = rsl.get();
        emp.getPersons().remove(person.get());
        store.save(emp);
        return new ResponseEntity<>(rsl.get(), HttpStatus.OK);
    }
}
