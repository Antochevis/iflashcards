package com.example.iflashcards;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class CriarFlashcard extends AppCompatActivity {

    private Spinner spinnerMateria;
    private EditText editPerguntaFlashcard;
    private EditText editRespostaFlashcard;
    private Repositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_flashcard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repositorio = Repositorio.getInstancia();

        spinnerMateria = findViewById(R.id.spinnerMateria);
        editPerguntaFlashcard = findViewById(R.id.editPerguntaFlashcard);
        editRespostaFlashcard = findViewById(R.id.editRespostaFlashcard);
        CardView cardSalvar = findViewById(R.id.cardSalvar);

        carregarSpinnerMaterias();

        // VALIDAÇÃO DE FORMULÁRIO (R3) E CRIAÇÃO DE FLASHCARD (CRUD R6)
        cardSalvar.setOnClickListener(v -> {
            if (spinnerMateria.getSelectedItem() == null) {
                Toast.makeText(this, "Cadastre ao menos uma matéria antes!", Toast.LENGTH_SHORT).show();
                return;
            }

            String nomeMateria = spinnerMateria.getSelectedItem().toString();
            String pergunta = editPerguntaFlashcard.getText().toString().trim();
            String resposta = editRespostaFlashcard.getText().toString().trim();

            if (pergunta.isEmpty()) {
                editPerguntaFlashcard.setError("A pergunta é obrigatória!");
                Toast.makeText(this, "Preencha a pergunta do flashcard!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (resposta.isEmpty()) {
                editRespostaFlashcard.setError("A resposta é obrigatória!");
                Toast.makeText(this, "Preencha a resposta do flashcard!", Toast.LENGTH_SHORT).show();
                return;
            }

            Materia materia = repositorio.getMateriaPorNome(nomeMateria);
            if (materia != null) {
                materia.adicionarFlashcard(new Flashcard(pergunta, resposta));
                Toast.makeText(this, "Flashcard salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish(); // Volta para a tela anterior
            }
        });
    }

    private void carregarSpinnerMaterias() {
        List<String> nomes = repositorio.getNomesMaterias();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nomes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMateria.setAdapter(adapter);
    }
}
