package com.martynas.obj_4_gradebook;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private final StringProperty name;
    private final StringProperty surname;
    private final IntegerProperty ID;

    public Student(String name, String surname, int ID) {
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.ID = new SimpleIntegerProperty(ID);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public String getName() {
        return name.get();
    }

    public String getSurname() {
        return surname.get();
    }

    public int getID() {
        return ID.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getFullName() {
        return this.name.get() + " " + this.surname.get();
    }
}

