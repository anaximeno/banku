package com.groupnine.banku.controllers;


import com.groupnine.banku.BankuApp;
import com.groupnine.banku.businesslogic.BankAgency;
import com.groupnine.banku.businesslogic.Employee;
import com.groupnine.banku.businesslogic.IOperator;
import com.groupnine.banku.miscellaneous.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.nio.charset.MalformedInputException;

public class LoginController {
    @FXML
    private TextField usernameLoginTextField;
    @FXML
    private PasswordField passwordLoginPasswordField;
    @FXML
    private Button logInBtn;
    @FXML
    private Button clearBtn;

    @FXML
    public void initialize(){
        clearBtn.setOnMouseClicked(event -> clearUserInput());

    }

    public void clearUserInput(){
        usernameLoginTextField.setText("");
        passwordLoginPasswordField.clear();
    }



    public void checkLogIn(){
        final String username = usernameLoginTextField.getText();
        final String password = passwordLoginPasswordField.getText();
        IOperator operator = BankAgency.getInstance().getBankOperator(username, password);
        if (operator != null){
            BankuApp.currentOperator = operator;
            //WindowContextController window = new WindowContextController("loading-screen-view");
            Logger.log("Successful LogIn");
        }
    }


}
