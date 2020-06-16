package br.com.petshopsi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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
import br.com.petshopsi.classes.ConfiguracaoFirebase;

public class CarregarPerfilActivity extends AppCompatActivity {

    ProgressBar progressBarPerfil;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    ImageView patinhaUm,patinhaDois,patinhaTres,patinhaQuatro;

    // AUTENTICACAO
    private FirebaseAuth mAuth;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    //Pegar o usuario corrente
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    //Referência do banco
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregar_perfil);

        patinhaUm = (ImageView)findViewById(R.id.patinhaUm);
        patinhaDois = (ImageView)findViewById(R.id.patinhaDois);
        patinhaTres = (ImageView)findViewById(R.id.patinhaTres);
        patinhaQuatro = (ImageView)findViewById(R.id.patinhaQuatro);

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

        String uid = firebaseAuth.getUid();
        databaseRef = ConfiguracaoFirebase.getFirebase().child("Usuario").child(uid);


        // Verifica se usuário está logado e conta ativada
        if (firebaseAuth != null){

            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String perfil = String.valueOf(dataSnapshot.child("perfil").getValue().toString());

                    // By pass devido a variavel perfil esta nula
                    if (perfil == null){
                        Intent intentMCli = new Intent(CarregarPerfilActivity.this, HomeClienteNavActivity.class);
                        startActivity(intentMCli);
                        Toast.makeText(CarregarPerfilActivity.this, "Login efetuado!", Toast.LENGTH_SHORT).show();
                        progressBarPerfil.setVisibility(View.GONE);
                        finish();
                    }

                    if(perfil.equals("Cliente") == true){

                        Intent intentMCli = new Intent(CarregarPerfilActivity.this, HomeClienteNavActivity.class);
                        startActivity(intentMCli);
                        Toast.makeText(CarregarPerfilActivity.this, "Login efetuado!", Toast.LENGTH_SHORT).show();
                        progressBarPerfil.setVisibility(View.GONE);
                        finish();

                    } else if (perfil.equals("Funcionario") == true){

                        Toast.makeText(CarregarPerfilActivity.this, "Tela em construção!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();

                        /*Intent intentMFunc = new Intent(CarregarPerfilActivity.this, HomeFuncionarioNavActivity.class);
                        startActivity(intentMFunc);
                        Toast.makeText(CarregarPerfilActivity.this, "Login efetuado!", Toast.LENGTH_SHORT).show();
                        progressBarPerfil.setVisibility(View.GONE);
                        finish();*/

                    } else{

                        Toast.makeText(CarregarPerfilActivity.this, "Contate o administrador", Toast.LENGTH_SHORT).show();
                        progressBarPerfil.setVisibility(View.GONE);
                        finish();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
