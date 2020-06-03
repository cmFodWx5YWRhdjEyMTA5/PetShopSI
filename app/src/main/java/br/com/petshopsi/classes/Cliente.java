package br.com.petshopsi.classes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import br.com.petshopsi.classes.ConfiguracaoFirebase;

public class Cliente extends Usuario {
private String perfil;

    public Cliente() {
    }

    public void salvar(){
        this.perfil = "Cliente";
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("Usuario").child(id).setValue(Cliente.this);
    }

}
