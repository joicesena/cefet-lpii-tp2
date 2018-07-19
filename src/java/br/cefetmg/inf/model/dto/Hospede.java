package br.cefetmg.inf.model.dto;

public class Hospede {

    private String codCPF;
    private String nomHospede;
    private String nroTelefone;
    private String desEmail;

    public Hospede(String codCPF, String nomHospede, String nroTelefone, String desEmail) {
        this.codCPF = codCPF;
        this.nomHospede = nomHospede;
        this.nroTelefone = nroTelefone;
        this.desEmail = desEmail;
    }

    public String getCodCPF() {
        return codCPF;
    }

    public void setCodCPF(String codCPF) {
        this.codCPF = codCPF;
    }

    public String getNomHospede() {
        return nomHospede;
    }

    public void setNomHospede(String nomHospede) {
        this.nomHospede = nomHospede;
    }

    public String getNroTelefone() {
        return nroTelefone;
    }

    public void setNroTelefone(String nroTelefone) {
        this.nroTelefone = nroTelefone;
    }

    public String getDesEmail() {
        return desEmail;
    }

    public void setDesEmail(String desEmail) {
        this.desEmail = desEmail;
    }

}
