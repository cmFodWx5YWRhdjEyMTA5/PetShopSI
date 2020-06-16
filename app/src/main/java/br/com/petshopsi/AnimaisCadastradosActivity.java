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
    String identificadorCampanha;
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
        usuarioFirebase = FirebaseAuth.getInstance();
        final String uidCliente = usuarioFirebase.getCurrentUser().getEmail();

        /* Convertendo email do cliente logado em Base64 */
        identificadorCliente = Base64Custom.codificarBase64(uidCliente);

        firebase = ConfiguracaoFirebase.getFirebase()
                .child("Animal")
                .child(identificadorCliente);

        // Listener para recuperar dados dos cartoes
        valueEventListenerAnimais = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Limpar lista
                animais.clear();
                // Listar Cartoes
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    Animal animal = dados.getValue(Animal.class);
                    animais.add(animal);
                }
                adapter.notifyDataSetChanged();
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
                //String uidCampanha = cartao.getCampanha();
                identificadorAnimal = Base64Custom.codificarBase64(uidAnimal);


                Intent intent = new Intent(AnimaisCadastradosActivity.this, TelaCrudAnimaisActivity.class);
//                intent.putExtra("nomeCampanha", cartao.getCampanha());
//                intent.putExtra("emailCliente", cartao.getCliente());
//                intent.putExtra("descricaoCampanha", cartao.getDescricaoCampanha());
//                intent.putExtra("nomeEmpresa", cartao.getEmpresa());
//                intent.putExtra("nomeProduto", cartao.getNomeProduto());
//                intent.putExtra("selosMarcados", cartao.getSelosMarcados());
//                intent.putExtra("selosTotal", cartao.getSelosTotal());
//                intent.putExtra("status", cartao.getStatus());
                startActivity(intent);

                /* Debug para testar se o clique no item da lista pega o email em base64 */
                /*Toast.makeText(getActivity(), "UID-CARTAO: " + identificadorCartao, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "UID-CAMPANHA: " + identificadorCampanha, Toast.LENGTH_SHORT).show();*/

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