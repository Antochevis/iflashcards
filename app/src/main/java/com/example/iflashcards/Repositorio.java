package com.example.iflashcards;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {
    private static Repositorio instancia;
    private final List<Materia> materias;

    private Repositorio() {
        materias = new ArrayList<>();
        inicializarDadosExemplo();
    }

    public static synchronized Repositorio getInstancia() {
        if (instancia == null) {
            instancia = new Repositorio();
        }
        return instancia;
    }

    private void inicializarDadosExemplo() {
        Materia m1 = new Materia("Algoritmos");
        m1.adicionarFlashcard(new Flashcard(
                "O que é uma variável em programação?",
                "É um espaço na memória usado para armazenar um valor que pode ser consultado ou alterado durante a execução do programa."
        ));
        m1.adicionarFlashcard(new Flashcard(
                "O que é uma estrutura de repetição?",
                "É um bloco de código que executa repetidamente enquanto uma condição for verdadeira (ex: while, for)."
        ));

        Materia m2 = new Materia("POO");
        m2.adicionarFlashcard(new Flashcard(
                "O que é uma classe?",
                "É um molde ou modelo a partir do qual os objetos são criados."
        ));

        Materia m3 = new Materia("Redes de Computadores");
        m3.adicionarFlashcard(new Flashcard(
                "O que é o protocolo IP?",
                "É o protocolo responsável pelo desempenhamento do roteamento de pacotes através das fronteiras de redes."
        ));

        materias.add(m1);
        materias.add(m2);
        materias.add(m3);
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public Materia getMateriaPorNome(String nome) {
        for (Materia m : materias) {
            if (m.getNome().equalsIgnoreCase(nome)) {
                return m;
            }
        }
        return null;
    }

    public void adicionarMateria(Materia materia) {
        materias.add(materia);
    }

    public void removerMateria(Materia materia) {
        materias.remove(materia);
    }

    public List<String> getNomesMaterias() {
        List<String> nomes = new ArrayList<>();
        for (Materia m : materias) {
            nomes.add(m.getNome());
        }
        return nomes;
    }
}
