package com.martynas.obj_4_gradebook;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public interface DataModel {
    ObservableList<Group> getGroups();
    void addGroup(Group group);
    void removeGroup(Group group);

    void addStudent(Group group, Student student);
    void removeStudent(Group group, Student student);

    void markAttendance(Group group, Student student, LocalDate date);
    boolean isPresent(Group group, Student student, LocalDate date);
    List<LocalDate> getAttendanceDates(Group group, Student student);

}
