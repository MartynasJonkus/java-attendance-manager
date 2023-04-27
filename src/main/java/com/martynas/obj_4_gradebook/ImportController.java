package com.martynas.obj_4_gradebook;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ImportController extends Controller{
    @FXML
    void csvImport(){
        try (CSVReader reader = new CSVReader(new FileReader("student_data.csv"))) {
            String[] line = reader.readNext();
            String temp = " ";
            while ((line = reader.readNext()) != null) {
                if(!line[0].equals(temp)){
                    Group group = new Group(line[0], FXCollections.observableArrayList());
                    groupData.addGroup(group);
                }
                temp = line[0];
                String name = line[1];
                String surname = line[2];
                int ID = Integer.parseInt(line[3]);
                Student student = new Student(name, surname, ID);
                groupData.addStudent(groups.get(groups.size() - 1), student);
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Failed to read file: " + e.getMessage());
        }
        System.out.println("Imported data from file successfully!");
    }
    @FXML
    void xlsxImport() throws IOException {
        // Load the workbook from the Excel file
        Workbook workbook = WorkbookFactory.create(new FileInputStream("student_data.xlsx"));
        int numSheets = workbook.getNumberOfSheets();

        for (int i = 0; i < numSheets; i++) {
            // Get the sheet for the group
            Sheet sheet = workbook.getSheetAt(i);

            // Create a new group
            String groupName = sheet.getSheetName();
            Group group = new Group(groupName, FXCollections.observableArrayList());

            // Loop through the rows in the sheet
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Skip the header row
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Get the data for the student
                String name = row.getCell(0).getStringCellValue();
                String surname = row.getCell(1).getStringCellValue();
                int id = (int) row.getCell(2).getNumericCellValue();

                // Create a new student and add it to the group
                Student student = new Student(name, surname, id);
                group.getStudents().add(student);
            }

            // Add the group to the ObservableList
            groups.add(group);
        }
    }
}
