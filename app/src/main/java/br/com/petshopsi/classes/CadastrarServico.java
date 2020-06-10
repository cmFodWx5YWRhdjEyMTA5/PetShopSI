package br.com.petshopsi.classes;

public class CadastrarServico {

    public String Servico, Descricao, Valor, Obs;

    public CadastrarServico() {
    }

    public CadastrarServico(String Servico, String Descricao, String Valor, String Obs) {
        this.Servico = Servico;
        this.Descricao = Descricao;
        this.Valor = Valor;
        this.Obs = Obs;
    }

    public String getServico() {
        return Servico;
    }

    public void setServico(String Servico) {
        this.Servico = Servico;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }

    public String getObs() {
        return Obs;
    }

    public void setObs(String Obs) {
        this.Obs = Obs;
    }

}


