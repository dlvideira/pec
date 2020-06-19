package com.pec.personalexpensescontrol.model;

public enum Category {
    STUDY("Estudos"),
    FOOD("Alimentação"),
    TRIP("Viagens");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public static Category fromValue(String text) {
        for (Category b : Category.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }

}

