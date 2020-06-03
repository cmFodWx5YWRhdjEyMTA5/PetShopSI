package br.com.petshopsi.classes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import br.com.petshopsi.Servico;

public class SolicitarServico {

    private String id , ServicoSelecionado, checkBoxTransporte, transporteSelecionado, dataServico, horaServico, valorServico;
    // OBS -> Remover ServicoSelecionado, n fiz pq esta dando erros no cod.
    private Cliente cliente;

    private Servico servico;
    private Funcionario funcionario;

    public SolicitarServico() {
        this.cliente = new Cliente();
        this.servico = new Servico();
        this.funcionario = new Funcionario();
    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("SolicitarServico").child(getId()).setValue(SolicitarServico.this);
    }

    public Cliente getCliente() {
        return cliente;
    }



    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
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


