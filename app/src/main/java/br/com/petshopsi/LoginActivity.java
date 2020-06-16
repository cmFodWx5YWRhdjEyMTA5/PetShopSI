package br.com.petshopsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import br.com.petshopsi.classes.Cliente;
import br.com.petshopsi.classes.Funcionario;

public class LoginActivity extends AppCompatActivity {

    TextView tv_login_cliente,tv_login_funcionario;
    ProgressBar progressBar;
    private FirebaseAuth autenticacao;
    private FirebaseAuth fAuth;
    Cliente cliente;
    Funcionario funcionario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_login_cliente = findViewById(R.id.tv_login_cliente);
        tv_login_funcionario = findViewById(R.id.tv_login_funcionario);

        tv_login_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginClienteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tv_login_funcionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginFuncionarioActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }




}
