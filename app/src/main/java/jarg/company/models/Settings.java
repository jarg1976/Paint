package jarg.company.models;

import java.io.Serializable;

public class Settings implements Serializable {

    public static final String BACKGROUND_DEFAULT = "#000000";
    public static final String TEXT_COLOR_DEFAULT = "#ffffff";

    private String background;
    private String textColor;

    public Settings(String background, String textColor) {
        this.background = background;
        this.textColor = textColor;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "background='" + background + '\'' +
                ", textColor='" + textColor + '\'' +
                '}';
    }
}
