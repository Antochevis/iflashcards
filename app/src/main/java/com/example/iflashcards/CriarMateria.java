package com.example.iflashcards;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CriarMateria extends AppCompatActivity {

    private EditText editTituloMateria;
    private Repositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_materia);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repositorio = Repositorio.getInstancia();
        editTituloMateria = findViewById(R.id.editTituloMateria);
        CardView cardSalvarMateria = findViewById(R.id.cardSalvarMateria);

        // VALIDAÇÃO DE FORMULÁRIO (R3) E CADASTRO (CRUD R6)
        cardSalvarMateria.setOnClickListener(v -> {
            String nome = editTituloMateria.getText().toString().trim();

            if (nome.isEmpty()) {
                editTituloMateria.setError("O nome da matéria é obrigatório!");
                Toast.makeText(this, "Por favor, preencha o título da matéria!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verifica se a matéria já existe
            if (repositorio.getMateriaPorNome(nome) != null) {
                editTituloMateria.setError("Matéria já cadastrada!");
                Toast.makeText(this, "Esta matéria já existe!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Criar matéria no repositório
            Materia novaMateria = new Materia(nome);
            repositorio.adicionarMateria(novaMateria);

            Toast.makeText(this, "Matéria \"" + nome + "\" criada com sucesso!", Toast.LENGTH_SHORT).show();
            finish(); // Volta para a MainActivity
        });
    }
}
