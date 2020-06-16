package br.com.petshopsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import br.com.petshopsi.classes.Animal;
import br.com.petshopsi.classes.Cliente;
import br.com.petshopsi.classes.ConfiguracaoFirebase;
import br.com.petshopsi.helper.Base64Custom;

public class CadastrarAnimalActivity extends AppCompatActivity {

    private EditText edtNome, edtEspecie, edtRaca, edtIdade, edtCor, edtPorte;
    private Button btnCadastrarAnimal;
    private Animal animal;

    String identificadorContato;
    String identificadorAnimal;
    private FirebaseAuth usuarioFirebase;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_animal);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //Cast dos edits

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEspecie = (EditText) findViewById(R.id.edtEspecie);
        edtRaca = (EditText) findViewById(R.id.edtRaca);
        edtIdade = (EditText) findViewById(R.id.edtIdade);
        edtCor = (EditText) findViewById(R.id.edtCor);
        edtPorte = (EditText) findViewById(R.id.edtPorte);


        //Cast do botão
        btnCadastrarAnimal = (Button) findViewById(R.id.btnCadastrarAnimal);

        btnCadastrarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ESSA VARIAVEL TEM QUE SER O EMAIL DO CLIENTE
                final String emailUsuarioLogado = usuarioFirebase.getCurrentUser().getEmail();

                final String nomeAnimal = edtNome.getText().toString();
                final String especieAnimal = edtEspecie.getText().toString();
                final String raca = edtRaca.getText().toString();
                final String idade = edtIdade.getText().toString();
                final String cor = edtCor.getText().toString();
                final String porte = edtPorte.getText().toString();


                // verificar se o usuario ta cadastrado no bd
                identificadorContato = Base64Custom.codificarBase64(emailUsuarioLogado);
                identificadorAnimal = Base64Custom.codificarBase64(nomeAnimal);

                //Validação dos campos
                if (nomeAnimal.equals("") == true){
                    edtNome.setError("Preencha o Nome do animal!");
                    return;
                }else {
                    edtNome.setError(null);
                }

                if (especieAnimal.equals("") == true){
                    edtEspecie.setError("Preencha a espécie do animal!");
                    return;
                }else {
                    edtEspecie.setError(null);
                }

                if (raca.equals("") == true){
                    edtRaca.setError("Preencha a data de validade da campanha");
                    return;
                }else {
                    edtRaca.setError(null);
                }
                if (idade.equals("") == true){
                    edtIdade.setError("Preencha a data de validade da campanha");
                    return;
                }else {
                    edtIdade.setError(null);
                }
                if (cor.equals("") == true){
                    edtCor.setError("Preencha a data de validade da campanha");
                    return;
                }else {
                    edtCor.setError(null);
                }
                if (porte.equals("") == true){
                    edtPorte.setError("Preencha a data de validade da campanha");
                    return;
                }else {
                    edtPorte.setError(null);
                }

                //recuperar instancia Firebase
                firebase = ConfiguracaoFirebase.getFirebase().child("Clientes").child(identificadorContato);
                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // VERIFICA SE JA TEM DADOS DO USUARIO SALVO NO BD
                        if (dataSnapshot.getValue() != null){

                            // RECUPERAR DADOS DO CONTATO A SER ADICIONADO
                            Cliente usuarioCliente = dataSnapshot.getValue(Cliente.class);

                            // Pega cpf, nome e email da empresa do usuario logado
                            String cpf = usuarioCliente.getCpf().toString();
                            String nome = usuarioCliente.getNome().toString();
                            String email = usuarioCliente.getEmail();
                            //usuarioFirebase.getCurrentUser().getEmail();



                            animal = new Animal();
                            animal.setId(UUID.randomUUID().toString());
                            animal.setNomeAnimal(nomeAnimal);
                            animal.setEmailCliente(emailUsuarioLogado);
                            animal.setEspecieAnimal(especieAnimal);
                            animal.setRaca(raca);
                            animal.setIdade(idade);
                            animal.setCor(cor);
                            animal.setPorte(porte);
                            animal.setNomeCliente(nome);
                            animal.setEmailCliente(email);
                            animal.setCpfCliente(cpf);

                            firebase = ConfiguracaoFirebase.getFirebase();
                            firebase = firebase.child("Animal")
                                    .child(identificadorContato).child(animal.getId());
                            firebase.setValue(animal);

                            Toast.makeText(CadastrarAnimalActivity.this, "Animal salvo com sucesso!", Toast.LENGTH_SHORT).show();

                            feedbackCadastroSucesso();

                        }else {
                            Toast.makeText(CadastrarAnimalActivity.this, "Erro ao cadastrar animal: " + identificadorContato, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });




    }

    public void feedbackCadastroSucesso(){
        AlertDialog.Builder builderFeedbackSucesso = new AlertDialog.Builder(CadastrarAnimalActivity.this);
        builderFeedbackSucesso.setMessage("Cadastro de animal realizado com sucesso!");
        builderFeedbackSucesso.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // FINALIZA ESSA ACTIVITY
                finish();

            }
        });
        builderFeedbackSucesso.setCancelable(false);
        AlertDialog alertFeedbackSucesso = builderFeedbackSucesso.create();
        alertFeedbackSucesso.show();
    }

}