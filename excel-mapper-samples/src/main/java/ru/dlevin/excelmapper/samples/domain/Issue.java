package ru.dlevin.excelmapper.samples.domain;

/**
 * User: Dmitry Levin
 * Date: 10.03.14
 */
public class Issue {
    private int number;

    private IssueType type;

    private Importance importance;

    private String title;

    private String description;

    private String asignee;

    public Issue() {
    }

    public Issue(int number, IssueType type, Importance importance, String title, String description, String asignee) {
        this.number = number;
        this.type = type;
        this.importance = importance;
        this.title = title;
        this.description = description;
        this.asignee = asignee;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public IssueType getType() {
        return type;
    }

    public void setType(IssueType type) {
        this.type = type;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAsignee() {
        return asignee;
    }

    public void setAsignee(String asignee) {
        this.asignee = asignee;
    }
}
