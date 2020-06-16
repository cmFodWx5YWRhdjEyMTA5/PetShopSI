package br.com.petshopsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.petshopsi.classes.Cliente;
import br.com.petshopsi.classes.Funcionario;

public class MainActivity extends AppCompatActivity {


    Button btnTelaCadastrarCliente, btnTelaSolicitarServico, btnTelaLogin, btnCadastrarServico, btnCadastrarFuncionario;


    // Profile
    DatabaseReference databaseRef, profileUserRef;
    FirebaseAuth autenticacao;
    String currentUserId;
    TextView txtNomex, txtPerfilx, txtData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNomex = (TextView)findViewById(R.id.txtNomex);
        txtPerfilx = (TextView)findViewById(R.id.txtPerfilx);
        txtData = (TextView)findViewById(R.id.txtData);

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

    private void testar(){
        // MELO
        FirebaseApp.initializeApp(this);
        FirebaseDatabase bd = FirebaseDatabase.getInstance();
        final DatabaseReference bdRef = bd.getReference();




        /*// USUARIO LOGADO PEGANDO SEU ID (SESSAO)
        autenticacao = FirebaseAuth.getInstance();
        currentUserId = autenticacao.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Usuario").child(currentUserId);
        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String nome = dataSnapshot.child("nome").getValue().toString();
                    String perfil = dataSnapshot.child("perfil").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String endereco = dataSnapshot.child("endereco").getValue().toString();
                    String telefone = dataSnapshot.child("telefone").getValue().toString();



                    //
                    SolicitarServico ss = new SolicitarServico();
                    // DATA ATUAL
                    SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
                    Date data = new Date();
                    String dataFormatada = formataData.format(data);
                    // -->
                    ss.setDataSolicitacao(dataFormatada);

                    Cliente cli = new Cliente();
                    cli.nome = (nome);
                    cli.email = (email);
                    cli.endereco = (endereco);
                    cli.telefone = (telefone);

                    Funcionario func = new Funcionario();
                    func.nome = "João";
                    func.cpf = "123";
                    func.funcao = "Administrador";

                    Servico serv = new Servico();
                    serv.setNome("Tosa");
                    serv.setPreco("40");
                    
                    // SALVA NOS OBJETOS
                    ss.setCliente(cli);
                    //ss.setFuncionario(func);
                    ss.setServico(serv);

                    // TEMP PARA MOSTRAR DADOS DO PERFIL NA TELA MAIN
                    txtNomex.setText("Nome: "+nome);
                    txtPerfilx.setText("Perfil: "+perfil);
                    txtData.setText("Data da Solicitação: "+dataFormatada);

                    // SALVA NO BANCO
                    bdRef.child("ServicosSolicitados").push().setValue(ss);

                }else{
                    Toast.makeText(MainActivity.this, "Nenhum usuário logado!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


    }

}
