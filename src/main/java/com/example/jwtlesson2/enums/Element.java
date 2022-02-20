package com.example.jwtlesson2.enums;

public enum Element {
    USER("User not found", "This user already exists", "User successfully added",
            "User successfully edited", "User deleted"),

    CARD("Card not found", "This card already exists", "Card successfully added",
            "Card successfully edited", "Card deleted"),

    IN_COME("Income not found", "Income successfully added",
                 "Income successfully edited", "Income deleted");

    Element(String messageNotFound, String messageAdded, String messageEdited, String messageDeleted) {
        this.messageNotFound = messageNotFound;
        this.messageAdded = messageAdded;
        this.messageEdited = messageEdited;
        this.messageDeleted = messageDeleted;
    }

    Element(String messageNotFound, String messageExists, String messageAdded, String messageEdited, String messageDeleted) {
        this.messageNotFound = messageNotFound;
        this.messageExists = messageExists;
        this.messageAdded = messageAdded;
        this.messageEdited = messageEdited;
        this.messageDeleted = messageDeleted;
    }

    private String messageNotFound;

    private String messageExists;

    private String messageAdded;

    private String messageEdited;

    private String messageDeleted;

    public String getMessageDeleted() {
        return messageDeleted;
    }

    public String getMessageEdited() {
        return messageEdited;
    }

    public String getMessageAdded() {
        return messageAdded;
    }

    public String getMessageNotFound() {
        return messageNotFound;
    }

    public String getMessageExists() {
        return messageExists;
    }
}
