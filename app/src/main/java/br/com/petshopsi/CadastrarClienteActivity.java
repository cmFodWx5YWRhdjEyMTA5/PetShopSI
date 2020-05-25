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
import android.widget.ProgressBar;
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

import br.com.petshopsi.classes.ConfiguracaoFirebase;

import br.com.petshopsi.classes.Cliente;

public class CadastrarClienteActivity extends AppCompatActivity {

    EditText edtNome, edtEndereco, edtTelefone, edtEmail, edtSenha, edtDataNascimento, edtCpf, edtObservacoes;
    Button btnCadastrarCliente;

    // BARRA DE PROGRESSO
    ProgressBar progressBar;

    // USA CLASSE CLIENTE
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
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        btnCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // VALIDACOES
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
                    cliente.setPerfil("Cliente");

                    cadastrarCliente();

                } catch (Exception ex){
                    Toast.makeText(CadastrarClienteActivity.this, "Ocorreu um erro, tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // CADASTRAR CLIENTE
    private void cadastrarCliente(){
        progressBar.setVisibility(View.VISIBLE);
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

                            // FAZ ENVIO DO EMAIL PARA ATIVAÇÂO DE CONTA
                            autenticacao.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        // FORÇA LOGOFF
                                        autenticacao.signOut();
                                        progressBar.setVisibility(View.GONE);
                                        limparCampos();
                                        // ALERTA DE USUÁRIO CRIADO COM SUCESSO
                                        Toast.makeText(CadastrarClienteActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                                    }else {
                                        progressBar.setVisibility(View.GONE);
                                        limparCampos();
                                        Toast.makeText(CadastrarClienteActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else {
                            // MSG DE EMAIL DUPLICADO
                            edtEmail.setError("Esse e-mail já esta em uso.");
                            return;
                        }
                    }
                });
    }

    public void limparCampos(){
        edtNome.setText("");
        edtEndereco.setText("");
        edtTelefone.setText("");
        edtEmail.setText("");
        edtSenha.setText("");
        edtDataNascimento.setText("");
        edtCpf.setText("");
        edtObservacoes.setText("");
    }

}
