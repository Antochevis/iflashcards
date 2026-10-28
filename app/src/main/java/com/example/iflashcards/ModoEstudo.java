package com.example.iflashcards;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ModoEstudo extends AppCompatActivity {

    private TextView txtBadgeVerso;
    private TextView txtPerguntaContexto;
    private TextView txtResposta;
    private View divisor;

    private Materia materia;
    private int indiceAtual = 0;
    private boolean mostrandoVerso = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modo_estudo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CardView cardFechar = findViewById(R.id.cardFechar);
        CardView cardFlashcard = findViewById(R.id.cardFlashcard);

        txtBadgeVerso = findViewById(R.id.txtBadgeVerso);
        txtPerguntaContexto = findViewById(R.id.txtPerguntaContexto);
        txtResposta = findViewById(R.id.txtResposta);
        divisor = findViewById(R.id.divisor);

        String nomeMateria = getIntent().getStringExtra("MATERIA_NOME");
        materia = Repositorio.getInstancia().getMateriaPorNome(nomeMateria);

        if (materia == null || materia.getFlashcards().isEmpty()) {
            Toast.makeText(this, "Nenhum flashcard nesta matéria ainda!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Botão Fechar / Voltar
        cardFechar.setOnClickListener(v -> finish());

        // Alternar entre Frente e Verso ao clicar no cartão
        cardFlashcard.setOnClickListener(v -> {
            mostrandoVerso = !mostrandoVerso;
            atualizarExibicaoFlashcard();
        });

        // Alternar para o próximo flashcard ao pressionar longamente o cartão
        cardFlashcard.setOnLongClickListener(v -> {
            List<Flashcard> lista = materia.getFlashcards();
            if (lista.size() > 1) {
                indiceAtual = (indiceAtual + 1) % lista.size();
                mostrandoVerso = false;
                atualizarExibicaoFlashcard();
                Toast.makeText(this, "Próximo cartão (" + (indiceAtual + 1) + "/" + lista.size() + ")", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        atualizarExibicaoFlashcard();
    }

    private void atualizarExibicaoFlashcard() {
        Flashcard flashcard = materia.getFlashcards().get(indiceAtual);

        if (mostrandoVerso) {
            txtBadgeVerso.setText("VERSO");
            txtPerguntaContexto.setText(flashcard.getFrente());
            txtResposta.setText(flashcard.getVerso());
            divisor.setVisibility(View.VISIBLE);
        } else {
            txtBadgeVerso.setText("FRENTE");
            txtPerguntaContexto.setText("Materia: " + materia.getNome());
            txtResposta.setText(flashcard.getFrente());
            divisor.setVisibility(View.GONE);
        }
    }
}
