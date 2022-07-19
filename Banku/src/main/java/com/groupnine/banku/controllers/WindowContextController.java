package com.groupnine.banku.controllers;

import com.groupnine.banku.BankuApp;

import com.groupnine.banku.miscellaneous.LogType;
import com.groupnine.banku.miscellaneous.Logger;
import javafx.fxml.FXMLLoader;
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
        this.stage = stage;
        this.height = height;
        this.width = width;
        this.defaultViewPath = defaultViewPath;
        this.themePath = themePath;
        this.title = title;
    }

    public WindowContextController(double height, double width, String defaultViewPath, String title, String themePath)
    {
        this(new Stage(), height, width, defaultViewPath, title, themePath);
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

    public void setDefaultThemeAtPath(String path)
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

    private Scene loadViewFromPath(String path) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(BankuApp.class.getResource(path));
        return new Scene(fxmlLoader.load());
    }

    public void setStage(Stage stage)
    {
        assert stage != null; // stage should not be null
        this.stage = stage;
        this.stage.setHeight(height);
        this.stage.setWidth(width);
        this.stage.setResizable(false);
        openDefaultView();
        activateTheme();
        updateTitle();
    }

    public void activateTheme()
    {
        //"css/theme.css"
        if (!themePath.isEmpty()) {
            String themePath = BankuApp.class.getResource(this.themePath).toExternalForm();
            getStage().getScene().getStylesheets().add(themePath);
        }
    }

    public Stage getStage()
    {
        if (stage == null) {
            setStage(new Stage());
        }
        return stage;
    }

    public void show()
    {
        getStage().show();
    }

    public void openView(String path)
    {
        try {
            this.stage.setScene(loadViewFromPath(path));
        } catch (IOException exception) {
            Logger.log("Could not load view at '" + path + "'", LogType.ERROR);
            Logger.log(exception.getMessage(), LogType.EXIT_ERROR);
        }
    }

    public void openDefaultView()
    {
        openView(defaultViewPath);
    }
}
