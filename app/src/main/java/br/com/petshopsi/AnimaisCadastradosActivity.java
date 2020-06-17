package br.com.petshopsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.petshopsi.adapter.AnimalAdapter;
import br.com.petshopsi.classes.Animal;
import br.com.petshopsi.classes.ConfiguracaoFirebase;
import br.com.petshopsi.helper.Base64Custom;

public class AnimaisCadastradosActivity extends AppCompatActivity {

    private ListView lv_animais;
    private ArrayAdapter adapter;
    private ArrayList<Animal> animais;
    private DatabaseReference firebase;
    private FirebaseAuth usuarioFirebase;
    String identificadorCliente;
    String identificadorAnimal;
    private ValueEventListener valueEventListenerAnimais;
    private FloatingActionButton btn_fab;



    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerAnimais);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerAnimais);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animais_cadastrados);

        //Cast Botao FAB
        btn_fab = findViewById(R.id.btn_fab);
        //Cast
        // Monta list view e Adapter
        lv_animais = findViewById(R.id.lv_animais);
        animais = new ArrayList<>();

        adapter = new AnimalAdapter(this, animais);
        lv_animais.setAdapter(adapter);

        //adiciona o email
        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String uidCliente = usuarioFirebase.getCurrentUser().getEmail();

        /* Convertendo email do cliente logado em Base64 */
        identificadorCliente = Base64Custom.codificarBase64(uidCliente);

        firebase = ConfiguracaoFirebase.getFirebase()
                .child("Animal")
                .child(identificadorCliente);

        // Listener para recuperar dados dos animais
        valueEventListenerAnimais = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    // Limpar lista
                    animais.clear();
                    // Listar Cartoes
                    for (DataSnapshot dados: dataSnapshot.getChildren()){
                        Animal animal = dados.getValue(Animal.class);
                        animais.add(animal);

                    }
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    Toast.makeText(AnimaisCadastradosActivity.this, "Ainda não existem animais cadastrados.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        // Adicionar evento de clique na lista de empresas
        lv_animais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Animal animal = animais.get(position);

                // PEGANDO EMAIL DO CLIENTE E CODIFICANDO EM BASE64
                String uidAnimal = animal.getEmailCliente();

                identificadorAnimal = Base64Custom.codificarBase64(uidAnimal);


                Intent intent = new Intent(AnimaisCadastradosActivity.this, TelaCrudAnimaisActivity.class);

                intent.putExtra("idAnimal", animal.getId());
                intent.putExtra("emailCliente", animal.getEmailCliente());
                intent.putExtra("nomeAnimal", animal.getNomeAnimal());
                intent.putExtra("especieAnimal", animal.getEspecieAnimal());
                intent.putExtra("racaAnimal", animal.getRaca());
                intent.putExtra("idadeAnimal", animal.getIdade());
                intent.putExtra("corAnimal", animal.getCor());
                intent.putExtra("porteAnimal", animal.getPorte());
                startActivity(intent);


            }
        });


        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimaisCadastradosActivity.this, CadastrarAnimalActivity.class);
                startActivity(intent);
            }
        });

    }
}