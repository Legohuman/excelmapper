package ru.dlevin.excelmapper.samples.domain;

/**
 * User: Dmitry Levin
 * Date: 10.03.14
 */
public class UserStory {
    private String name;

    public UserStory() {
    }

    public UserStory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserStory{" +
            "name='" + name + '\'' +
            '}';
    }
}
