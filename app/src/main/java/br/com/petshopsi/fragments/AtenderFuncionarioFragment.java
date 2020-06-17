package br.com.petshopsi.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import br.com.petshopsi.adapter.ServicosSolicitadosAdapter;
import br.com.petshopsi.classes.Cliente;
import br.com.petshopsi.classes.ConfiguracaoFirebase;
import br.com.petshopsi.classes.ServicosSolicitados;
import br.com.petshopsi.helper.Base64Custom;

public class AtenderFuncionarioFragment extends Fragment {

    private ListView list_view_atender_servicos_solicitados;
    private ArrayAdapter adapterASC;
    private ArrayList<ServicosSolicitados> atenderServicosSolicitadosArrayList;
    private DatabaseReference firebase;
    DatabaseReference firebaseCli;
    private FirebaseAuth usuarioFirebase;
    String identificadorFuncionario;
    private ValueEventListener valueEventListenerAtenderSolicitarServicos;

    public AtenderFuncionarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerAtenderSolicitarServicos);

    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerAtenderSolicitarServicos);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aternder_funcionario, container, false);


        // Monta list view e Adapter
        list_view_atender_servicos_solicitados = (ListView)view.findViewById(R.id.list_view_atender_servicos_solicitados);
        atenderServicosSolicitadosArrayList = new ArrayList<>();


        // Adapter Customizado
        adapterASC = new ServicosSolicitadosAdapter(getContext(), atenderServicosSolicitadosArrayList);
        list_view_atender_servicos_solicitados.setAdapter(adapterASC);

        // PEGA USUARIO LOGADO....
        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();
        // ESSA VARIAVEL TEM QUE SER O EMAIL DO FUNCIONARIO
        final String emailUsuarioLogado = usuarioFirebase.getCurrentUser().getEmail();
        //Debug
        /*Toast.makeText(getActivity(), "Email Logado: " + emailUsuarioLogado, Toast.LENGTH_SHORT).show();*/

        // verificar se o usuario ta cadastrado no bd
        identificadorFuncionario = Base64Custom.codificarBase64(emailUsuarioLogado);
        // Debug
        /*Toast.makeText(getActivity(), "Email Base64: " + identificadorFuncionario, Toast.LENGTH_SHORT).show();*/



        firebase = ConfiguracaoFirebase.getFirebase().child("ServicosSolicitados").child("dGVjLmFkcmlhbm8uYW5kcmFkZUBnbWFpbC5jb20=");

        // Listener para recuperar contatos
        valueEventListenerAtenderSolicitarServicos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Limpar lista
                atenderServicosSolicitadosArrayList.clear();
                // Listar Contatos
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    ServicosSolicitados ss = dados.getValue(ServicosSolicitados.class);
                    atenderServicosSolicitadosArrayList.add(ss);
                }
                adapterASC.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        return view;
    }
}