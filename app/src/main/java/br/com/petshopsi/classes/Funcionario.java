package br.com.petshopsi.classes;

import com.google.firebase.database.DatabaseReference;

public class Funcionario extends Usuario{
    public String funcao, pis, matricula, rg, perfil;

    public void salvar(){
        this.perfil = "Funcionario";
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("Funcionario").child(id).setValue(Funcionario.this);
    }

}
