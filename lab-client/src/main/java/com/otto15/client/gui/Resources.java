package com.otto15.client.gui;

public enum Resources {
    LOGIN_WINDOW_PATH("/views/login.fxml"),
    REGISTER_WINDOW_PATH("/views/register.fxml"),
    MAIN_WINDOW_PATH("/views/main.fxml"),
    TABLE_PATH("/views/table.fxml"),
    VISUALIZE_PATH("/views/visualize.fxml"),
    ADD_PATH("/views/add.fxml"),
    UPDATE_PATH("/views/update.fxml"),
    TABLE_MENU_PATH("/views/tableMenu.fxml");

    private final String path;

    Resources(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
