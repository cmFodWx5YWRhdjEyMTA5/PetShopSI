package br.com.petshopsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.petshopsi.classes.Animal;
import br.com.petshopsi.classes.ConfiguracaoFirebase;
import br.com.petshopsi.helper.Base64Custom;

public class TelaCrudAnimaisActivity extends AppCompatActivity {

    private EditText edtNome, edtEspecie, edtRaca, edtIdade, edtCor, edtPorte;
    private Button btnDeletarAnimal, btnEditarAnimal;
    Animal animalSelecionado;
    String IDEmailCliente;
    String IDNomeAnimal;
    String IDNovoNome;

    // Variaveis do Firebase
    private DatabaseReference firebase;
    // PEGAR USUARIO
    private FirebaseAuth usuarioFirebase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_crud_animais);

        //Cast dos edits

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEspecie = (EditText) findViewById(R.id.edtEspecie);
        edtRaca = (EditText) findViewById(R.id.edtRaca);
        edtIdade = (EditText) findViewById(R.id.edtIdade);
        edtCor = (EditText) findViewById(R.id.edtCor);
        edtPorte = (EditText) findViewById(R.id.edtPorte);

        //Cast do botão
        btnDeletarAnimal = (Button) findViewById(R.id.btnDeletarAnimal);
        btnEditarAnimal = (Button) findViewById(R.id.btnEditarAnimal);

        // PEGAR EMAIL DO USUARIO LOGADO
        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();
        final String emailUsuarioLogado = usuarioFirebase.getCurrentUser().getEmail();

        // RECEBE DADOS DO ANIMAL
        final String idAnimal = getIntent().getStringExtra("idAnimal");
        String emailCliente = getIntent().getStringExtra("emailCliente");
        String nomeAnimal = getIntent().getStringExtra("nomeAnimal");
        String especieAnimal = getIntent().getStringExtra("especieAnimal");
        String racaAnimal = getIntent().getStringExtra("racaAnimal");
        String idadeAnimal = getIntent().getStringExtra("idadeAnimal");
        String corAnimal = getIntent().getStringExtra("corAnimal");
        String porteAnimal =  getIntent().getStringExtra("porteAnimal");

        edtNome.setText(nomeAnimal);
        edtEspecie.setText(especieAnimal);
        edtRaca.setText(racaAnimal);
        edtIdade.setText(idadeAnimal);
        edtCor.setText(corAnimal);
        edtPorte.setText(porteAnimal);


        IDEmailCliente = Base64Custom.codificarBase64(emailCliente);


        firebase = ConfiguracaoFirebase.getFirebase();


        btnEditarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builderVincularCartao = new AlertDialog.Builder(TelaCrudAnimaisActivity.this);
                builderVincularCartao.setMessage("Deseja se editar este animal?");
                builderVincularCartao.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        // condicoes psrs SIM

                        Animal animal = new Animal();
                        animal.setId(idAnimal);
                        animal.setEmailCliente(emailUsuarioLogado);
                        animal.setNomeAnimal(edtNome.getText().toString());
                        animal.setEspecieAnimal(edtEspecie.getText().toString().trim());
                        animal.setRaca(edtRaca.getText().toString().trim());
                        animal.setIdade(edtIdade.getText().toString().trim());
                        animal.setCor(edtCor.getText().toString().trim());
                        animal.setPorte(edtPorte.getText().toString().trim());

                        firebase.child("Animal").child(IDEmailCliente).child(idAnimal).setValue(animal);

                        Intent intent = new Intent(TelaCrudAnimaisActivity.this, AnimaisCadastradosActivity.class);
                        startActivity(intent);

                        finish();


                    }
                });
                builderVincularCartao.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // condicoes psrs Nao
                        Toast.makeText(TelaCrudAnimaisActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });


                builderVincularCartao.setCancelable(false);
                AlertDialog alertFeedbackSucesso = builderVincularCartao.create();
                alertFeedbackSucesso.show();

            }
        });

        btnDeletarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builderVincularCartao = new AlertDialog.Builder(TelaCrudAnimaisActivity.this);
                builderVincularCartao.setMessage("Deseja se apagar este animal?");
                builderVincularCartao.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        // condicoes psrs SIM

                        firebase.child("Animal").child(IDEmailCliente).child(idAnimal).removeValue();

                        Intent intent = new Intent(TelaCrudAnimaisActivity.this, AnimaisCadastradosActivity.class);
                        startActivity(intent);

                        finish();


                    }
                });
                builderVincularCartao.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // condicoes para Nao
                        Toast.makeText(TelaCrudAnimaisActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });


                builderVincularCartao.setCancelable(false);
                AlertDialog alertFeedbackSucesso = builderVincularCartao.create();
                alertFeedbackSucesso.show();

            }
        });

    }
}