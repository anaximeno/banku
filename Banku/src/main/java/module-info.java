module com.groupnine.banku {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafaker;
    requires java.sql;

    opens com.groupnine.banku.controllers to javafx.fxml, javafx.base;
    exports com.groupnine.banku;
    exports com.groupnine.banku.businesslogic;
    exports com.groupnine.banku.miscellaneous;
    exports com.groupnine.banku.controllers;
}