package com.example.kartcontrolremote;

class POJOKart {
    private boolean engineCut;
    private boolean isActive;
    private boolean isBattActive;
    private boolean isGreen;
    private int kart_no;

    boolean isGreen() {
        return this.isGreen;
    }

    void setGreen(boolean green) {
        this.isGreen = green;
    }

    boolean isBattActive() {
        return this.isBattActive;
    }

    void setBattActive(boolean battActive) {
        this.isBattActive = battActive;
    }

    boolean isActive() {
        return this.isActive;
    }

    void setActive(boolean active) {
        this.isActive = active;
    }

    boolean isEngineCut() {
        return this.engineCut;
    }

    void setEngineCut(boolean engineCut) {
        this.engineCut = engineCut;
    }

    int getKart_no() {
        return this.kart_no;
    }

    void setKart_no(int kart_no) {
        this.kart_no = kart_no;
    }
}
