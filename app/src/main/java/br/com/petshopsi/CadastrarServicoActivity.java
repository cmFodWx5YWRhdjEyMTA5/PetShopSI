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

import java.util.HashMap;

public class CadastrarServicoActivity extends AppCompatActivity {

    private EditText editServico,editdDscricao,editValor,editObs;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastraservico);

        editServico = findViewById(R.id.editServico);
        editdDscricao = findViewById(R.id.editdDscricao);
        editValor = findViewById(R.id.editValor);
        editObs = findViewById(R.id.editObs);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String,Object> map = new HashMap<>();
                map.put("Serviço: ",editServico.getText().toString());
                map.put("Descrição:",editdDscricao.getText().toString());
                map.put("Valor:",editValor.getText().toString());
                map.put("Observação:",editObs.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("Cadastrar Serviço").push()
                        .setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.i("jfbvkj", "onComplete: ");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("jfbvkj", "onFailure: "+e.toString());
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CadastrarServicoActivity.this, "Serviço cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
                        editServico.getText().clear();
                        editdDscricao.getText().clear();
                        editValor.getText().clear();
                        editObs.getText().clear();
                    }
                });


            }
        });

    }
}
