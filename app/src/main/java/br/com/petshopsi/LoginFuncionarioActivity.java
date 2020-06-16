package br.com.petshopsi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import br.com.petshopsi.classes.ConfiguracaoFirebase;
import br.com.petshopsi.classes.Funcionario;
import br.com.petshopsi.helper.Base64Custom;
import br.com.petshopsi.helper.Preferencias;

public class LoginFuncionarioActivity extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn,forgotTextLink,criarConta;
    ProgressBar progressBar;

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
        setContentView(R.layout.activity_login_funcionario);

        /*// VERIFICA USUARIO LOGADO
        verificarUsuarioLogado();*/

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        mLoginBtn = findViewById(R.id.loginBtn);
        mCreateBtn = findViewById(R.id.createText);
        forgotTextLink = findViewById(R.id.forgotPassword);
        criarConta = (TextView)findViewById(R.id.criarConta);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // authenticate the user
                EfetuarLogin(mEmail.getText().toString(), mPassword.getText().toString());
            }catch (Exception ex){
                Toast.makeText(LoginFuncionarioActivity.this, "Ocorreu um erro ao efetuar o login. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
            }

            }
        });



        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginFuncionarioActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginFuncionarioActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });


        criarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(LoginActivity.this, CadastrarClienteActivity.class);
                // TESTE CRIAR SERVICO
                Intent intent = new Intent(LoginFuncionarioActivity.this, CadastraFuncionario.class);
                startActivity(intent);
                finish();
            }
        });


    }

    // METODO onStart
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            UsuarioLogado();
            //mAuth.signOut();
        }
    }

    public void UsuarioLogado() {
        Intent intentCarregarFuncionario = new Intent(this, CarregarPerfilFuncionarioActivity.class);
        startActivity(intentCarregarFuncionario);
        finish();
    }


    public void EfetuarLogin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            UsuarioLogado();

                        }else {
                            Toast.makeText(LoginFuncionarioActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void feedbackAtivarLogin(){
        AlertDialog.Builder builderFeedbackSucesso = new AlertDialog.Builder(LoginFuncionarioActivity.this);
        builderFeedbackSucesso.setMessage("Por favor, verifique seu e-mail para ativar sua conta!");
        builderFeedbackSucesso.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builderFeedbackSucesso.setCancelable(false);
        AlertDialog alertFeedbackSucesso = builderFeedbackSucesso.create();
        alertFeedbackSucesso.show();

    }


}
