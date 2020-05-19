package br.com.petshopsi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.core.app.NavUtils;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.petshopsi.classes.Cliente;
import br.com.petshopsi.firebase.ConfiguracaoFirebase;

public class CadastrarClienteActivity extends AppCompatActivity {

    EditText edtNome, edtEndereco, edtTelefone, edtEmail, edtSenha, edtDataNascimento, edtCpf, edtObservacoes;
    Button btnCadastrarCliente;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Cliente");

    private Cliente cliente;

    // USANDO REFERENCIA DO FIREBASE
    private DatabaseReference referenciaFirebase;
    // USANDO REFERENCIA DO FirebaseAuth
    private FirebaseAuth autenticacao;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_cliente);


        edtNome = (EditText)findViewById(R.id.edtNome);
        edtEndereco = (EditText)findViewById(R.id.edtEndereco);
        edtTelefone = (EditText)findViewById(R.id.edtTelefone);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        edtDataNascimento = (EditText)findViewById(R.id.edtDataNascimento);
        edtCpf = (EditText)findViewById(R.id.edtCpf);
        edtObservacoes = (EditText)findViewById(R.id.edtObservacoes);
        btnCadastrarCliente = (Button) findViewById(R.id.btnCadastrarCliente);

        btnCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edtNome.getText().toString();
                String endereco = edtEndereco.getText().toString();
                String telefone = edtTelefone.getText().toString();
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();
                String dataNascimento = edtDataNascimento.getText().toString();
                String cpf = edtCpf.getText().toString();
                String observacoes = edtObservacoes.getText().toString();

                if (nome.isEmpty() == true){
                    edtNome.setError("Informe seu nome!");
                    return;
                }
                if (endereco.isEmpty() == true){
                    edtEndereco.setError("Informe seu endereço");
                    return;
                }
                if (telefone.isEmpty() == true){
                    edtTelefone.setError("Informe seu telefone");
                    return;
                }
                if (email.isEmpty() == true){
                    edtEmail.setError("Informe seu e-mail");
                    return;
                }
                if (senha.isEmpty() == true){
                    edtEmail.setError("Informe sua senha");
                    return;
                }
                if (dataNascimento.isEmpty() == true){
                    edtDataNascimento.setError("Informe sua data de nascimento");
                    return;
                }
                if (cpf.isEmpty() == true){
                    edtCpf.setError("Informe seu CPF");
                    return;
                }
                cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setEndereco(endereco);
                cliente.setTelefone(telefone);
                cliente.setEmail(email);
                cliente.setSenha(senha);
                cliente.setData(dataNascimento);
                cliente.setCpf(cpf);
                cliente.setObservacoes(observacoes);

                cadastrarCliente();

            }
        });

    }

    // CADASTRAR CLIENTE
    private void cadastrarCliente(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                cliente.getEmail(),
                cliente.getSenha()
        )
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            // CADASTRA OS DADOS NO REAL DATABASE
                            // Recebe o mesmo ID do Usuário para o cadastro no banco
                            FirebaseUser clienteFirebase = task.getResult().getUser();
                            cliente.setId(clienteFirebase.getUid());
                            cliente.salvar();
                            autenticacao.signOut();
                            Toast.makeText(CadastrarClienteActivity.this, "Cliente cadastrado com sucesso", Toast.LENGTH_SHORT).show();

                        }else {
                            // MSG DE EMAIL DUPLICADO
                            Toast.makeText(CadastrarClienteActivity.this, "Ocorreu um erro. Tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }

}
