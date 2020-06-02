package br.com.petshopsi.classes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class SolicitarServico {

    private String id , ServicoSelecionado, checkBoxTransporte, transporteSelecionado, dataServico, horaServico, valorServico;


    public SolicitarServico() {

    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("SolicitarServico").child(getId()).setValue(SolicitarServico.this);
    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServicoSelecionado() {
        return ServicoSelecionado;
    }

    public void setServicoSelecionado(String servicoSelecionado) {
        ServicoSelecionado = servicoSelecionado;
    }

    public String getCheckBoxTransporte() {
        return checkBoxTransporte;
    }

    public void setCheckBoxTransporte(String checkBoxTransporte) {
        this.checkBoxTransporte = checkBoxTransporte;
    }

    public String getTransporteSelecionado() {
        return transporteSelecionado;
    }

    public void setTransporteSelecionado(String transporteSelecionado) {
        this.transporteSelecionado = transporteSelecionado;
    }

    public String getDataServico() {
        return dataServico;
    }

    public void setDataServico(String dataServico) {
        this.dataServico = dataServico;
    }

    public String getHoraServico() {
        return horaServico;
    }

    public void setHoraServico(String horaServico) {
        this.horaServico = horaServico;
    }

    public String getValorServico() {
        return valorServico;
    }

    public void setValorServico(String valorServico) {
        this.valorServico = valorServico;
    }
}


