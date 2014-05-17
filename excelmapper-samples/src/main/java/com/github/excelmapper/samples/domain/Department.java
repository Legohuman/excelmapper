package com.github.excelmapper.samples.domain;

import java.util.List;

/**
 * User: Dmitry Levin
 * Date: 08.03.14
 */
public class Department {

    private String name;

    private List<Person> persons;

    public Department() {
    }

    public Department(String name, List<Person> persons) {
        this.name = name;
        this.persons = persons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
