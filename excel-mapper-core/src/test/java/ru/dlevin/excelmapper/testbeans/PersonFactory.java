package ru.dlevin.excelmapper.testbeans;

/**
 * User: Dmitry Levin
 * Date: 28.03.14
 */
public class PersonFactory {

    public Person getJohn() {
        return new PersonBuilder().create("John", 20, "IT Inc")
                                  .addAddress("San Francisco", "Lombard str.")
                                  .addAddress("New York", "Wall str.")
                                  .build();
    }

    public Person getMike() {
        return new PersonBuilder().create("Mike", 42, "Micro Inc")
                                  .addAddress("Chicago", "Louis str.")
                                  .build();
    }
}
