package br.com.petshopsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnTelaCadastrarCliente, btnTelaSolicitarServico, btnTelaLogin, btnCadastrarServico, btnCadastrarFuncionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTelaCadastrarCliente = (Button)findViewById(R.id.btnTelaCadastrarCliente);
        btnTelaSolicitarServico = (Button)findViewById(R.id.btnTelaSolicitarServico);
        btnTelaLogin = (Button)findViewById(R.id.btnTelaLogin);
        btnCadastrarServico = (Button)findViewById(R.id.btnCadastrarServico);
        btnCadastrarFuncionario = (Button)findViewById(R.id.btnCadastrarFuncionario);

        btnTelaCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastrarClienteActivity.class);
                startActivity(intent);
            }
        });

        btnTelaSolicitarServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SolicitarServicoActivity.class);
                startActivity(intent);
            }
        });

        btnTelaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        btnCadastrarServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastrarServicoActivity.class);
                startActivity(intent);
            }
        });

        btnCadastrarFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastraFuncionario.class);
                startActivity(intent);
            }
        });

    }
}
