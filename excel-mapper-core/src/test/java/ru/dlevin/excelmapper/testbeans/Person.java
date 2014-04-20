package ru.dlevin.excelmapper.testbeans;

import java.util.List;

/**
 * User: Dmitry Levin
 * Date: 27.03.14
 */
public class Person {

    private String name;

    private int age;

    private List<Address> addresses;

    private Company company;

    public Person(String name, int age, List<Address> addresses, Company company) {
        this.name = name;
        this.age = age;
        this.addresses = addresses;
        this.company = company;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
