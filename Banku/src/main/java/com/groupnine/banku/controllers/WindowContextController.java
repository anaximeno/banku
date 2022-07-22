package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;

import com.groupnine.banku.miscellaneous.LogType;
import com.groupnine.banku.miscellaneous.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// Abstract this class for general use
public class WindowContextController {
    public final static double DEFAULT_HEIGHT = 600;
    public final static double DEFAULT_WIDTH = 840;
    public final static String DEFAULT_TITLE = "Banku - A Banking Application";
    private final static String DEFAULT_THEME_PATH = "css/theme.css";
    private Stage stage;
    private final double height;
    private final double width;
    private final String defaultViewPath;
    private String themePath;
    private String title;

    public WindowContextController(Stage stage, double height, double width, String defaultViewPath, String title, String themePath)
    {
        this.height = height;
        this.width = width;
        this.defaultViewPath = defaultViewPath;
        this.themePath = themePath;
        this.title = title;
        setStage(stage);
    }

    public WindowContextController(double height, double width, String defaultViewPath, String title, String themePath)
    {
        this(new Stage(), height, width, defaultViewPath, title, themePath);
    }
    public WindowContextController(double height, double width, String defaultViewPath, String title)
    {
        this(new Stage(), height, width, defaultViewPath, title, DEFAULT_THEME_PATH);
    }

    public WindowContextController(String defaultViewPath, String title, String themePath)
    {
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH, defaultViewPath, title, themePath);
    }

    public WindowContextController(String defaultViewPath, String title)
    {
        this(defaultViewPath, title, DEFAULT_THEME_PATH);
    }

    public WindowContextController(String defaultViewPath)
    {
        this(defaultViewPath, DEFAULT_TITLE);
    }

    public void setThemeAtPath(String path)
    {
        themePath = path;
    }

    public void setTitle(String title)
    {
        this.title = title;
        updateTitle();
    }

    public void updateTitle()
    {
        getStage().setTitle(title);
    }

    private void setRootViewOnMainScene(String path)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BankuApp.class.getResource(path));
            getStage().getScene().setRoot(fxmlLoader.load());
        } catch (IOException exception) {
            Logger.log("Could not load view at '" + path + "'", LogType.ERROR);
            Logger.log(exception.getMessage(), LogType.EXIT_ERROR);
        }
    }

    public void setStage(Stage stage)
    {
        assert stage != null; // stage should not be null
        this.stage = stage;
        this.stage.setHeight(height);
        this.stage.setWidth(width);
        this.stage.setResizable(false);
        this.stage.setScene(new Scene(new Group()));
        activateTheme();
        updateTitle();
    }

    public void activateTheme()
    {
        //"css/theme.css"
        if (themePath != null && !themePath.isEmpty()) {
            try {
                String themePath = BankuApp.class.getResource(this.themePath).toExternalForm();
                getStage().getScene().getStylesheets().add(themePath);
            } catch (NullPointerException exception) {
                Logger.log("Could not load the theme '" + themePath + "'", LogType.ERROR);
                Logger.log(exception.getMessage(), LogType.EXIT_ERROR);
            }
        }
    }

    public Stage getStage()
    {
        if (stage == null) {
            setStage(new Stage());
        }
        return stage;
    }

    private void show()
    {
        activateTheme();
        getStage().show();
    }

    public void showView(String path, String windowTitle)
    {
        getStage().setTitle(windowTitle);
        setRootViewOnMainScene(path);
        show();
    }

    public void showDefaultView()
    {
        showView(defaultViewPath, title);
    }
}
