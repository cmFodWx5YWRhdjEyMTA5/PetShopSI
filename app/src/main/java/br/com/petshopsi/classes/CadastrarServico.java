package br.com.petshopsi.classes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class CadastrarServico {

    public String id, servico, descricao, valor, obs;


    public CadastrarServico() {
    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("Servicos'").child(getId()).setValue(CadastrarServico.this);
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String Servico) {
        this.servico = servico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String Descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String Valor) {
        this.valor = valor;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String Obs) {
        this.obs = obs;
    }

}


