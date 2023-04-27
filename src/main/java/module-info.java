module com.martynas.obj_4_gradebook {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.pdfbox;
    requires com.opencsv;


    opens com.martynas.obj_4_gradebook to javafx.fxml;
    exports com.martynas.obj_4_gradebook;
}