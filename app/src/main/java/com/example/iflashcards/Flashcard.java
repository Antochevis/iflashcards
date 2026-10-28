package com.example.iflashcards;

public class Flashcard {
    private String frente;
    private String verso;

    public Flashcard(String frente, String verso) {
        this.frente = frente;
        this.verso = verso;
    }

    public String getFrente() {
        return frente;
    }

    public void setFrente(String frente) {
        this.frente = frente;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
    }
}
