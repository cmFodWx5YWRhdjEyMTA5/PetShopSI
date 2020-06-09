package br.com.petshopsi;

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
import com.google.firebase.database.FirebaseDatabase;

import br.com.petshopsi.classes.Funcionario;

public class CadastraFuncionario extends AppCompatActivity {

    private EditText editNome,editdFuncao,editCpf,editRg,editPis,editMatricula;
    private Button save;

    private Funcionario func;


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
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = editNome.getText().toString();
                String funcao = editdFuncao.getText().toString();
                String cpf = editCpf.getText().toString();
                String rg = editRg.getText().toString();
                String pis = editPis.getText().toString();
                String matricula = editMatricula.getText().toString();
                String perfil = "Funcionario";




                func = new Funcionario();
                func.nome = (nome);
                func.funcao = (funcao);
                func.cpf = (cpf);
                func.rg = (rg);
                func.pis = (pis);
                func.matricula = (matricula);
                func.perfil = (perfil);


                FirebaseDatabase.getInstance().getReference().child("Funcionario").push()
                        .setValue(func)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.i("jfbvkj", "onComplete: ");
                                //Log.d("tag", "Serviço Cadastrado com sucesso");

                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Log.i("jfbvkj", "onFailure: "+e.toString());
                                Toast.makeText(CadastraFuncionario.this, "Erro ao Cadastrar o Funcionário", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.i("jfbvkj", "onSuccess: ");
                        //Log.d("tag", "Serviço Cadastrado com sucesso");

                        Toast.makeText(CadastraFuncionario.this, "Funcionário cadastrado com sucesso.", Toast.LENGTH_SHORT).show();

                        editNome.getText().clear();
                        editdFuncao.getText().clear();
                        editCpf.getText().clear();
                        editRg.getText().clear();
                        editPis.getText().clear();
                        editMatricula.getText().clear();

                    }
                });



            }
        });

    }
}
