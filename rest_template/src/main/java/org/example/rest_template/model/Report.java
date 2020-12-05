package org.example.rest_template.model;

import java.sql.Timestamp;
import java.util.Random;

public class Report {

    private int id;
    private Timestamp created;
    private Person person;

    public Report() {
        Random rnd = new Random();
        id = rnd.nextInt();
        created = new Timestamp(System.currentTimeMillis());
    }

    public static Report of(Person person) {
        Report rsl = new Report();
        rsl.person = person;
        return rsl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
