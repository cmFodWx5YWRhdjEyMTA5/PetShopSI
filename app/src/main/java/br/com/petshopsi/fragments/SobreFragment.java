package br.com.petshopsi.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.petshopsi.AnimaisCadastradosActivity;
import br.com.petshopsi.CadastrarAnimalActivity;
import br.com.petshopsi.LoginActivity;
import br.com.petshopsi.R;
import br.com.petshopsi.classes.ConfiguracaoFirebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class SobreFragment extends Fragment {

    // USANDO REFERENCIA DO FirebaseAuth
    private FirebaseAuth autenticacao;
    private String currentUserId;
    private DatabaseReference databaseRef;
    private DatabaseReference referenciaFirebase, profileUserRef;


    public SobreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sobre, container, false);

        MaterialButton btnSair = (MaterialButton)view.findViewById(R.id.btnSair);
        Button btnanimais = (Button)view.findViewById(R.id.btnanimais);

        final TextView txtNome = (TextView)view.findViewById(R.id.txtNome);
        final TextView txtPerfil = (TextView)view.findViewById(R.id.txtPerfil);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogout();
            }
        });

        btnanimais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AnimaisCadastradosActivity.class);
                startActivity(intent);
            }
        });



        // USUARIO LOGADO PEGANDO SEU ID (SESSAO)
        autenticacao = FirebaseAuth.getInstance();
        currentUserId = autenticacao.getCurrentUser().getUid();
        profileUserRef = FirebaseDatabase.getInstance().getReference().child("Usuario").child(currentUserId);
        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String nome = dataSnapshot.child("nome").getValue().toString();
                    String perfil = dataSnapshot.child("perfil").getValue().toString();
                    txtNome.setText(nome);
                    txtPerfil.setText(perfil);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    public void doLogout(){
        referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        // Encerra Fragment
        getActivity().finish();
    }
}
