package br.com.petshopsi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.petshopsi.classes.ConfiguracaoFirebase;
import br.com.petshopsi.helper.Base64Custom;
import br.com.petshopsi.helper.Base64CustomFuncionario;

public class CarregarPerfilClienteActivity extends AppCompatActivity {

    ImageView patinhaUm,patinhaDois,patinhaTres,patinhaQuatro;
    ProgressBar progressBarPerfil;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    private String identificadorUsuario;
    private DatabaseReference databaseRef;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth usuarioFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregar_perfil_cliente);

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
                        verificarPerfilUsuario();
                    }
                });

            }
        }).start();


    }

    public  void verificarPerfilUsuario(){

        //firebaseAuth.getCurrentUser();
        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();
        progressBarPerfil = (ProgressBar)findViewById(R.id.progressBarPerfil);
        progressBarPerfil.setVisibility(View.VISIBLE);

        //String uid = firebaseAuth.getCurrentUser().getEmail();

        final String uid = usuarioFirebase.getCurrentUser().getEmail();
        String emailUsuario = uid.toString();
        identificadorUsuario = Base64CustomFuncionario.codificarBase64(emailUsuario);

        databaseRef = ConfiguracaoFirebase.getFirebase().child("Clientes").child(identificadorUsuario);


        if (firebaseAuth != null){
            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    try {
                        String perfil = String.valueOf(dataSnapshot.child("perfil").getValue().toString());

                        if (perfil.equals("Cliente") == true){

                            // Toast.makeText(CarregarPerfilClienteActivity.this, "Login efetuado! " + perfil, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(CarregarPerfilClienteActivity.this, HomeClienteNavActivity.class);
                            startActivity(intent);
                            progressBarPerfil.setVisibility(View.GONE);
                            finish();

                        } else{

                            Toast.makeText(CarregarPerfilClienteActivity.this, "Erro de Perfil. Contate o administrador", Toast.LENGTH_SHORT).show();
                            progressBarPerfil.setVisibility(View.GONE);
                            finish();

                        }
                    }catch (Exception e){
                        Toast.makeText(CarregarPerfilClienteActivity.this, "Sua conta não é de Cliente.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CarregarPerfilClienteActivity.this, LoginActivity.class);
                        startActivity(intent);
                        firebaseAuth.signOut();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
}