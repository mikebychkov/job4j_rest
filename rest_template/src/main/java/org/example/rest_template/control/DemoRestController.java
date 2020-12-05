package org.example.rest_template.control;

import org.example.rest_template.model.Person;
import org.example.rest_template.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DemoRestController {

    private final RestTemplate rest;

    private static final String API = "http://localhost:8080/api/person/";
    private static final String API_ID = "http://localhost:8080/api/person/{id}";

    @Autowired
    public DemoRestController(RestTemplate rest) {
        this.rest = rest;
    }

    @GetMapping("/report")
    public List<Report> getReport() {
        List<Report> rsl = new ArrayList<>();
        List<Person> persons = rest.exchange(API,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Person>>(){}
                ).getBody();
        assert persons != null;
        rsl = persons.stream().map(Report::of).collect(Collectors.toList());
        return rsl;
    }

    @PostMapping("/report")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person rsl = rest.postForObject(API, person, Person.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/report")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        rest.put(API, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/report/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }
}
