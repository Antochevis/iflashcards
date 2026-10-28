package com.example.iflashcards;

import java.util.ArrayList;
import java.util.List;

public class Materia {
    private String nome;
    private List<Flashcard> flashcards;

    public Materia(String nome) {
        this.nome = nome;
        this.flashcards = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public int getQuantidadeCartoes() {
        return flashcards.size();
    }

    public void adicionarFlashcard(Flashcard flashcard) {
        this.flashcards.add(flashcard);
    }

    public void removerFlashcard(int index) {
        if (index >= 0 && index < flashcards.size()) {
            this.flashcards.remove(index);
        }
    }
}
