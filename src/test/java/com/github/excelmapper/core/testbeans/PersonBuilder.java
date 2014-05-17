package com.github.excelmapper.core.testbeans;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Dmitry Levin
 * Date: 27.03.14
 */
public class PersonBuilder {

    private Person person = new Person();

    public PersonBuilder create(String name, int age, String companyName) {
        person.setName(name);
        person.setAge(age);
        person.setCompany(new Company(companyName));
        return this;
    }

    public PersonBuilder addAddress(String city, String street) {
        List<Address> addresses = person.getAddresses();
        if (addresses == null) {
            person.setAddresses(new ArrayList<Address>());
        }
        person.getAddresses().add(new Address(city, street));
        return this;
    }

    public Person build() {
        return person;
    }
}
