package edu.ucdenver.jasmeenkaur.todoornottodoapp;

public class Category {
    private String id;
    private String name;
    private String defaultCategory;
    private String font;
    private String textColor;
    private String backgroundColor;
    private String listID;

    public Category(String id, String name, String defaultCategory, String font, String textColor,
                    String backgroundColor, String listID){
        this.id = id;
        this.name = name;
        this.defaultCategory = defaultCategory;
        this.font = font;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.listID = listID;
    }


    // ======================================== getters ========================================
    public String getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDefaultCategory() {
        return defaultCategory;
    }
    public String getFont() {
        return font;
    }
    public String getTextColor(){
        return textColor;
    }
    public String getBackgroundColor() {
        return backgroundColor;
    }
    public String getListID() {
        return listID;
    }

    // ======================================== setters ========================================
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDefaultCategory(String defaultCategory) {
        this.defaultCategory = defaultCategory;
    }
    public void setFont(String font) {
        this.font = font;
    }
    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public void setListID(String listID) {
        this.listID = listID;
    }
}

