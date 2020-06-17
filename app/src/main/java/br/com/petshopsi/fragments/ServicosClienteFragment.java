package br.com.petshopsi.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.petshopsi.R;
import br.com.petshopsi.SolicitarServicoActivity;
import br.com.petshopsi.adapter.ServicosSolicitadosAdapter;
import br.com.petshopsi.classes.ConfiguracaoFirebase;
import br.com.petshopsi.classes.ServicosSolicitados;
import br.com.petshopsi.helper.Base64Custom;

public class ServicosClienteFragment extends Fragment {

    FloatingActionButton fab_solicitar_servico;
    private ListView list_view_servicos_solicitados;
    private ArrayAdapter adapterSC;
    private ArrayList<ServicosSolicitados> servicosSolicitadosArrayList;
    private DatabaseReference firebase;
    private FirebaseAuth usuarioFirebase;
    String identificadorCliente;
    private ValueEventListener valueEventListenerSolicitarServicos;



    public ServicosClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerSolicitarServicos);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerSolicitarServicos);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servicos_cliente, container, false);


        // CASTs
        fab_solicitar_servico = (FloatingActionButton)view.findViewById(R.id.fab_solicitar_servico);

        // IR PARA TELA DE SOLICITAR SERVICO
        fab_solicitar_servico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SolicitarServicoActivity.class);
                startActivity(intent);
            }
        });

        // Monta list view e Adapter
        list_view_servicos_solicitados = (ListView)view.findViewById(R.id.list_view_servicos_solicitados);
        servicosSolicitadosArrayList = new ArrayList<>();

        // Adapter Customizado
        adapterSC = new ServicosSolicitadosAdapter(getContext(), servicosSolicitadosArrayList);
        list_view_servicos_solicitados.setAdapter(adapterSC);

        // PEGA USUARIO LOGADO....
        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();
        // ESSA VARIAVEL TEM QUE SER O EMAIL DO CLIENTE
        final String emailUsuarioLogado = usuarioFirebase.getCurrentUser().getEmail();
        //Debug
        /*Toast.makeText(getActivity(), "Email Logado: " + emailUsuarioLogado, Toast.LENGTH_SHORT).show();*/

        // verificar se o usuario ta cadastrado no bd
        identificadorCliente = Base64Custom.codificarBase64(emailUsuarioLogado);
        // Debug
        /*Toast.makeText(getActivity(), "Email Base64: " + identificadorCliente, Toast.LENGTH_SHORT).show();*/






        firebase = ConfiguracaoFirebase.getFirebase()
                .child("ServicosSolicitados")
                .child(identificadorCliente);

        // Listener para recuperar contatos
        valueEventListenerSolicitarServicos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Limpar lista
                servicosSolicitadosArrayList.clear();
                // Listar Contatos
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    ServicosSolicitados ss = dados.getValue(ServicosSolicitados.class);
                    servicosSolicitadosArrayList.add(ss);
                }
                adapterSC.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        return view;
    }




}
