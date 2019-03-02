package com.example.blusmart.data.model;

public enum DutyState {
    START("START"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETE("COMPLETE");

    private String state;

    DutyState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
