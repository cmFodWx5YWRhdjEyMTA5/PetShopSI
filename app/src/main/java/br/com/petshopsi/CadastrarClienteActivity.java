package br.com.petshopsi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.core.app.NavUtils;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.petshopsi.classes.ConfiguracaoFirebase;

import br.com.petshopsi.classes.Cliente;
import br.com.petshopsi.helper.Base64Custom;
import br.com.petshopsi.helper.Preferencias;

public class CadastrarClienteActivity extends AppCompatActivity {

    EditText edtNome, edtEndereco, edtTelefone, edtEmail, edtSenha, edtDataNascimento, edtCpf, edtObservacoes;
    Button btnCadastrarCliente;

    // BARRA DE PROGRESSO
    ProgressBar progressBar;

    // USA CLASSE CLIENTE
    Cliente cliente;

    // USANDO REFERENCIA DO FirebaseAuth
    private FirebaseAuth autenticacao;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_cliente);

        // CASTs
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
                        edtSenha.setError("Informe sua senha");
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
                    cliente.setData_nascimento(dataNascimento);
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
                .addOnCompleteListener(CadastrarClienteActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(CadastrarClienteActivity.this, "Sucesso ao cadastrar cliente", Toast.LENGTH_SHORT).show();

                            String identificadorUsuario = Base64Custom.codificarBase64(cliente.getEmail());
                            cliente.setId(identificadorUsuario);
                            cliente.salvar();

                            // ESTUDO FIREBASE
                            Preferencias preferencias = new Preferencias(CadastrarClienteActivity.this);
                            preferencias.salvarDados(identificadorUsuario);

                            abrirLoginUsuario();

                        }else {

                            String erroExcecao = "";

                            try {
                                throw  task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                erroExcecao = "Digite a senha mais forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erroExcecao = "O e-mail digitado é inválido!";
                            } catch (FirebaseAuthUserCollisionException e) {
                                erroExcecao = "Esse e-mail já esta em uso pelo App!";
                            } catch (Exception e) {
                                erroExcecao = "Ao cadastrar usuário!";
                            }

                            Toast.makeText(CadastrarClienteActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void abrirLoginUsuario(){
        Intent intent = new Intent(CadastrarClienteActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
