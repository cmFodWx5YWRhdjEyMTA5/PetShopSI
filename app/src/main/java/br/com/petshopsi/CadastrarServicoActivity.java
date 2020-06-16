package br.com.petshopsi;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import br.com.petshopsi.classes.Servico;


public class CadastrarServicoActivity extends AppCompatActivity {

    private EditText editServico,editdDscricao,editValor,editObs;
    private Button save;
    Servico servico;
    // Variaveis do Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_servico);

        editServico = findViewById(R.id.editServico);
        editdDscricao = findViewById(R.id.editdDscricao);
        editValor = findViewById(R.id.editValor);
        editObs = findViewById(R.id.editObs);
        save = findViewById(R.id.save);

        // Add
        inicializaFirebase();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String nomeServico = editServico.getText().toString();
                    String descricao = editdDscricao.getText().toString();
                    String obs = editObs.getText().toString();
                    String valorServico = editValor.getText().toString();
                    Double valor = Double.parseDouble(valorServico);

                    if (nomeServico.trim().equals("")){
                        editServico.requestFocus();
                        editServico.setError("Informe o nome do Serviço!");
                        return;
                    }

                    if (valorServico.trim().equals("") == true){
                        editValor.requestFocus();
                        editValor.setError("Informe um valor!");
                        return;
                    }

                    servico = new Servico();
                    servico.setId(UUID.randomUUID().toString());
                    servico.setServico(editServico.getText().toString());
                    servico.setDescricao(editdDscricao.getText().toString());
                    servico.setObservacao(editObs.getText().toString());
                    servico.setValor(valor);
                    servico.salvar();
                }catch (Exception ex){
                    editValor.requestFocus();
                    editValor.setError("Campo vazio ou caracteres inválidos");
                    Toast.makeText(getApplicationContext(),"O valor deve possuir números",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void inicializaFirebase() {
        FirebaseApp.initializeApp(CadastrarServicoActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void abrirTelaHome(){
        Intent intent = new Intent(CadastrarServicoActivity.this, HomeClienteNavActivity.class);
        startActivity(intent);
        finish();
    }


}
