package ru.dkotsur.calculator.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "persons",
        foreignKeys = @ForeignKey(entity = Session.class,
            parentColumns = "id",
            childColumns = "session_id",
            onDelete = CASCADE))
public class Person implements Comparable<Person> {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;

    @ColumnInfo(name = "session_id")
    private long sessionId;

    @Ignore
    private Double budget;

    public Person(String name, long sessionId) {
        this.name = name;
        this.sessionId = sessionId;
        budget = 0.0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return name;
    }


    @Override
    public int compareTo(Person person) {
        return (int) ((budget.doubleValue() - person.budget.doubleValue()));
    }

    public void plusBudget(double delta) {
        budget+= delta;
    }

    public void minusBudget(double delta) {
        budget-=delta;
    }

    public double getBudget() {
        return budget;
    }
}
