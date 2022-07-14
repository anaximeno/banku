package com.groupnine.banku.controllers;

import javafx.scene.Scene;

public class WindowsContextController {
    private static WindowsContextController instance;
    private Scene mainScene;

    private WindowsContextController() {
        mainScene = null;
    }

    public static WindowsContextController getInstance() {
        if (instance == null) {
            instance = new WindowsContextController();
        }
        return instance;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public void setMainScene(Scene scene) {
        mainScene = scene;
    }
}
