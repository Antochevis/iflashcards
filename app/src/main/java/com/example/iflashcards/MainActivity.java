package com.example.iflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout containerMaterias;
    private Repositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repositorio = Repositorio.getInstancia();
        containerMaterias = findViewById(R.id.containerMaterias);

        CardView cardCriarFlashcard = findViewById(R.id.cardCriarFlashcard);
        CardView cardCriarMateria = findViewById(R.id.cardCriarMateria);

        // NAVEGAÇÃO: Criar Flashcard (R1/R2)
        cardCriarFlashcard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CriarFlashcard.class);
            startActivity(intent);
        });

        // NAVEGAÇÃO: Criar Matéria (R1/R2)
        cardCriarMateria.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CriarMateria.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarMaterias();
    }

    // LISTA FUNCIONANDO E ATUALIZANDO DINAMICAMENTE (R5 & R6)
    private void carregarMaterias() {
        containerMaterias.removeAllViews();
        List<Materia> materias = repositorio.getMaterias();

        for (Materia materia : materias) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_materia, containerMaterias, false);

            TextView txtNome = itemView.findViewById(R.id.txtNomeMateriaItem);
            TextView txtQtd = itemView.findViewById(R.id.txtQtdCartoesItem);

            txtNome.setText(materia.getNome());
            int qtd = materia.getQuantidadeCartoes();
            txtQtd.setText(qtd + (qtd == 1 ? " cartão" : " cartões"));

            // R5: Clique para Detalhe / Modo Estudo
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ModoEstudo.class);
                intent.putExtra("MATERIA_NOME", materia.getNome());
                startActivity(intent);
            });

            // R6: Opção de Excluir Matéria no clique longo (CRUD)
            itemView.setOnLongClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Opções da Matéria")
                        .setMessage("Deseja excluir a matéria \"" + materia.getNome() + "\"?")
                        .setPositiveButton("Excluir", (dialog, which) -> {
                            repositorio.removerMateria(materia);
                            carregarMaterias();
                            Toast.makeText(this, "Matéria excluída!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
                return true;
            });

            containerMaterias.addView(itemView);
        }
    }
}
