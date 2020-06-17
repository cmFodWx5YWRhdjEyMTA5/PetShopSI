package br.com.petshopsi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.com.petshopsi.R;
import br.com.petshopsi.SolicitarServicoActivity;

public class ServicosFuncionarioFragment extends Fragment {

    ListView listV_Servicos;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    String identificadorContato;
    private FirebaseAuth usuarioFirebase;
    private DatabaseReference firebase;


    public ServicosFuncionarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servicos_funcionario, container, false);

        // CAST
        listV_Servicos = (ListView)view.findViewById(R.id.listV_Servicos);


        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);




        return view;
    }




}
