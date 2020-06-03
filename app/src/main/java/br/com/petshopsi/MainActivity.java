package br.com.petshopsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.petshopsi.classes.Cliente;
import br.com.petshopsi.classes.Funcionario;
import br.com.petshopsi.classes.SolicitarServico;

public class MainActivity extends AppCompatActivity {

    Button btnTelaCadastrarCliente, btnTelaSolicitarServico, btnTelaLogin, btnCadastrarServico, btnCadastrarFuncionario;
    private void testar(){
        FirebaseApp.initializeApp(this);
        FirebaseDatabase bd = FirebaseDatabase.getInstance();
        DatabaseReference bdRef = bd.getReference();


        SolicitarServico ss = new SolicitarServico();
        ss.setDataServico("10/10/2020");

        Cliente cli = new Cliente();
        cli.nome = ("Bruno");
        cli.email = ("bruno@brn.com");
        cli.cpf = ("12312332198");

        Funcionario func = new Funcionario();
        func.nome = "João";
        func.cpf = "123";
        func.funcao = "Administrador";

        Servico serv = new Servico();
        serv.setNome("Tosa");
        serv.setPreco("40");
        ss.setCliente(cli);
        //ss.setFuncionario(func);
        ss.setServico(serv);
        bdRef.child("ServicosSolicitados").push().setValue(ss);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testar();
        /*btnTelaCadastrarCliente = (Button)findViewById(R.id.btnTelaCadastrarCliente);
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
        });*/



    }
}
