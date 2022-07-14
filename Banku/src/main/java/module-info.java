module com.groupnine.banku {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.groupnine.banku.controllers to javafx.fxml;
    exports com.groupnine.banku;
    exports com.groupnine.banku.businesslogic;
}