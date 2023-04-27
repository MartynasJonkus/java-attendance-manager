package com.martynas.obj_4_gradebook;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class ReportController extends Controller{
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;

    @FXML
    void outputReport() throws IOException {
        LocalDate startDate = fromDate.getValue();
        LocalDate endDate = toDate.getValue();

        // Create a new PDF document
        PDDocument document = new PDDocument();

        // Create a new page
        PDPage page = new PDPage();
        document.addPage(page);

        // Create a new content stream for the page
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Set the font for the report
        PDFont font = PDType1Font.HELVETICA_BOLD;
        contentStream.setFont(font, 12);

        // Set the title of the report
        String title = "Attendance Report (" + startDate + " to " + endDate + ")";
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText(title);
        contentStream.endText();

        int offset = 700;
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        // Iterate over all groups and students
        for (Group group : groups) {
            contentStream.beginText();
            offset -= 30;
            contentStream.newLineAtOffset(100, offset);
            contentStream.showText(group.getName());
            contentStream.endText();

            for (Student student : group.getStudents()) {
                List<LocalDate> attendanceDates = group.getAttendanceDates(student);
                int attendedDays = 0;

                // Count the number of days the student has attended within the specified date range
                for (LocalDate date : attendanceDates) {
                    if (date.isAfter(startDate.minusDays(1)) && date.isBefore(endDate.plusDays(1))) {
                        attendedDays++;
                    }
                }

                // Calculate the attendance rate
                double attendanceRate = 0;
                if (totalDays > 0) {
                    attendanceRate = ((double) attendedDays / (double) totalDays) * 100;
                }

                // Add the student's name and attendance rate to the report
                contentStream.beginText();
                offset -= 20;
                contentStream.newLineAtOffset(100, offset);
                contentStream.showText(student.getFullName() + ": " + String.format("%.2f", attendanceRate) + "%");
                contentStream.endText();
            }
        }

        // Close the content stream and save the document
        contentStream.close();
        document.save("Attendance Report.pdf");
        document.close();
    }
}
