package br.com.petshopsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SolicitarServicoActivity extends AppCompatActivity {

    Spinner spinner;
    private CheckBox checkBoxTransporte;
    private EditText datasolicitacao;

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Servico servico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_servico);

        // cast
        spinner = (Spinner) findViewById(R.id.spinner);
        checkBoxTransporte = (CheckBox) findViewById(R.id.checkTransporte);
        datasolicitacao = (EditText) findViewById(R.id.data_solicitacao);
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


        List<String> list = new ArrayList<String>();
        list.add("Busca");
        list.add("Entrega");
        list.add("Busca e Entrega");



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        //Validar se o checkbox de transporte esta selecionado
        //spinner ser apresentado desabilitado ao carregar a tela
        spinner.setEnabled(false);

        checkBoxTransporte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    spinner.setEnabled(true);
                    //Toast.makeText(SolicitarServicoActivity.this, "Marcado", Toast.LENGTH_SHORT).show();
                } else {
                    spinner.setEnabled(false);
                    //Toast.makeText(SolicitarServicoActivity.this, "Não marcado", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}
