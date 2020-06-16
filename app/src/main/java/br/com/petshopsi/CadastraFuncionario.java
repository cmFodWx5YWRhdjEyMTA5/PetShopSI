package br.com.petshopsi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import br.com.petshopsi.classes.Cliente;
import br.com.petshopsi.classes.ConfiguracaoFirebase;
import br.com.petshopsi.classes.Funcionario;
import br.com.petshopsi.helper.Base64Custom;
import br.com.petshopsi.helper.Base64CustomFuncionario;
import br.com.petshopsi.helper.Preferencias;
import br.com.petshopsi.helper.PreferenciasFuncionario;

public class CadastraFuncionario extends AppCompatActivity {

    private EditText editNome,editdFuncao,editCpf,editRg,editPis,editMatricula,et_email,et_senha;
    private Button save;

    // USA CLASSE CLIENTE
    Funcionario funcionario;

    // USANDO REFERENCIA DO FirebaseAuth
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_funcionario);

        editNome = findViewById(R.id.editNome);
        editdFuncao = findViewById(R.id.editdFuncao);
        editCpf = findViewById(R.id.editCpf);
        editRg = findViewById(R.id.editRg);
        editPis = findViewById(R.id.editPis);
        editMatricula = findViewById(R.id.editMatricula);
        et_email = findViewById(R.id.et_email);
        et_senha = findViewById(R.id.et_senha);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // VALIDACOES
                    String nome = editNome.getText().toString();
                    String funcao = editdFuncao.getText().toString();
                    String cpf = editCpf.getText().toString();
                    String rg = editRg.getText().toString();
                    String pis = editPis.getText().toString();
                    String matricula = editMatricula.getText().toString();
                    String email = et_email.getText().toString();
                    String senha = et_senha.getText().toString();


                    if (nome.isEmpty() == true){
                        editNome.setError("Informe seu nome!");
                        return;
                    }
                    if (funcao.isEmpty() == true){
                        editdFuncao.setError("Informe sua função!");
                        return;
                    }
                    if (cpf.isEmpty() == true){
                        editCpf.setError("Informe seu CPF!");
                        return;
                    }

                    if (rg.isEmpty() == true){
                        editRg.setError("Informe seu RG!");
                        return;
                    }
                    if (pis.isEmpty() == true){
                        editPis.setError("Informe seu PIS!");
                        return;
                    }
                    if (matricula.isEmpty() == true){
                        editMatricula.setError("Informe sua matrícula!");
                        return;
                    }
                    if (email.isEmpty() == true){
                        et_email.setError("Informe seu e-mail");
                        return;
                    }
                    if (senha.isEmpty() == true){
                        et_senha.setError("Informe sua senha");
                        return;
                    }

                    funcionario = new Funcionario();
                    funcionario.setNome(nome);
                    funcionario.setFuncao(funcao);
                    funcionario.setCpf(cpf);
                    funcionario.setRg(rg);
                    funcionario.setPis(pis);
                    funcionario.setMatricula(matricula);
                    funcionario.setEmail(email);
                    funcionario.setSenha(senha);
                    funcionario.setPerfil("Funcionario");
                    cadastrarFuncionario();

                } catch (Exception ex){
                    Toast.makeText(CadastraFuncionario.this, "Ocorreu um erro, tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // CADASTRAR FUNCIONARIO
    private void cadastrarFuncionario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                funcionario.getEmail(),
                funcionario.getSenha()
        )
                .addOnCompleteListener(CadastraFuncionario.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(CadastraFuncionario.this, "Sucesso ao cadastrar funcionário!", Toast.LENGTH_SHORT).show();

                            String identificadorUsuario = Base64CustomFuncionario.codificarBase64(funcionario.getEmail());
                            funcionario.setId(identificadorUsuario);
                            funcionario.salvar();

                            // ESTUDO FIREBASE
                            PreferenciasFuncionario preferenciasFuncionario = new PreferenciasFuncionario(CadastraFuncionario.this);
                            preferenciasFuncionario.salvarDadosFuncionrio(identificadorUsuario);

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

                            Toast.makeText(CadastraFuncionario.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void abrirLoginUsuario(){
        Intent intent = new Intent(CadastraFuncionario.this, LoginActivity.class);
        startActivity(intent);
    }
}