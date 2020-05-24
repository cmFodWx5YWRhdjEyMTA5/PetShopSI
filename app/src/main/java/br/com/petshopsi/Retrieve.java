package br.com.petshopsi;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Retrieve extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Servico servico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        setTitle("Lista"); 

        servico = new Servico();

        listView = (ListView) findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Usuario");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.servico_info,R.id.servicoInfo, list);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Pega todos os filhos que estão dentro do nó pai Serviço
                for(DataSnapshot data: dataSnapshot.getChildren()){

                    servico = data.getValue(Servico.class);
                    //servico.toString();
                   // servico = ds.getValue(Servico.class);
                    list.add(servico.getNome().toString());
                }

                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
