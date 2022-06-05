module com.groupnine.banku {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.groupnine.banku to javafx.fxml;
    exports com.groupnine.banku;
}