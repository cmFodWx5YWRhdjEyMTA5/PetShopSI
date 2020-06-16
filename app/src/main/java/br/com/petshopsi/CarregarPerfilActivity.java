package br.com.petshopsi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import br.com.petshopsi.helper.Base64Custom;
import br.com.petshopsi.classes.Cliente;
import br.com.petshopsi.classes.ConfiguracaoFirebase;
import br.com.petshopsi.classes.Funcionario;

public class CarregarPerfilActivity extends AppCompatActivity {

    ProgressBar progressBarPerfil;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    ImageView patinhaUm,patinhaDois,patinhaTres,patinhaQuatro;

    String identificadorUsuario;
    private DatabaseReference firebase;
    private FirebaseAuth usuarioFirebase;
    private ArrayAdapter adapter;
    private ArrayList<Cliente> clientes;
    private ArrayList<Funcionario> funcionarios;
    private ValueEventListener valueEventListenerUsuarios;
    Cliente cliente;
    Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregar_perfil);

        patinhaUm = (ImageView)findViewById(R.id.patinhaUm);
        patinhaDois = (ImageView)findViewById(R.id.patinhaDois);
        patinhaTres = (ImageView)findViewById(R.id.patinhaTres);
        patinhaQuatro = (ImageView)findViewById(R.id.patinhaQuatro);


        // FIREBASE AUTH
        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        progressBarPerfil = (ProgressBar)findViewById(R.id.progressBarPerfil);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < 100){
                    mProgressStatus++;
                    android.os.SystemClock.sleep(50);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBarPerfil.setProgress(mProgressStatus);
                            progressBarPerfil.setScaleY(10f);
                            if (mProgressStatus > 20){
                                patinhaUm.setVisibility(View.VISIBLE);
                            }
                            if (mProgressStatus > 40){
                                patinhaDois.setVisibility(View.VISIBLE);
                            }
                            if (mProgressStatus > 60){
                                patinhaTres.setVisibility(View.VISIBLE);
                            }
                            if (mProgressStatus > 80){
                                patinhaQuatro.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        VerificaPerfilUsuario();
                    }
                });

            }
        }).start();





    }

    private void VerificaPerfilUsuario() {

        progressBarPerfil.setVisibility(View.VISIBLE);

        /*// Verifica se usuário está logado e contata ativada
        if (firebaseAuth != null){
            databaseRefClientes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {

                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            cliente = data.getValue(Cliente.class);
                            String perfil = cliente.getPerfil();

                            if(perfil.equals("Cliente") == true){

                                Intent intentMCli = new Intent(CarregarPerfilActivity.this, HomeClienteNavActivity.class);
                                startActivity(intentMCli);
                                Toast.makeText(CarregarPerfilActivity.this, "Sou: " + perfil, Toast.LENGTH_SHORT).show();
                                progressBarPerfil.setVisibility(View.GONE);
                                finish();

                            } else{

                                Toast.makeText(CarregarPerfilActivity.this, "Contate o administrador", Toast.LENGTH_SHORT).show();
                                progressBarPerfil.setVisibility(View.GONE);
                                finish();

                            }
                        }

                    }catch (Exception e){
                        Toast.makeText(CarregarPerfilActivity.this, "Exceção Cliente", Toast.LENGTH_SHORT).show();

                        databaseRefFuncionarios.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try {
                                    for (DataSnapshot data : dataSnapshot.getChildren()){
                                        funcionario = data.getValue(Funcionario.class);
                                        String perfil = funcionario.getPerfil();

                                        if(perfil.equals("Funcionario") == true){

                                            Intent intentMCli = new Intent(CarregarPerfilActivity.this, HomeClienteNavActivity.class);
                                            startActivity(intentMCli);
                                            Toast.makeText(CarregarPerfilActivity.this, "Sou: " + perfil, Toast.LENGTH_SHORT).show();
                                            progressBarPerfil.setVisibility(View.GONE);
                                            finish();

                                        } else{

                                            Toast.makeText(CarregarPerfilActivity.this, "Contate o administrador", Toast.LENGTH_SHORT).show();
                                            progressBarPerfil.setVisibility(View.GONE);
                                            finish();

                                        }
                                    }
                                }catch (Exception e){
                                    Toast.makeText(CarregarPerfilActivity.this, "Exceção Funcionários", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }*/
    }

}
