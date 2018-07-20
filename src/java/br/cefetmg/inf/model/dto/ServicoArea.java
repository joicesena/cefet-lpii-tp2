package br.cefetmg.inf.model.dto;

public class ServicoArea {

    private String codServicoArea;
    private String nomServicoArea;

    public ServicoArea(String codServicoArea, String nomServicoArea) {
        this.codServicoArea = codServicoArea;
        this.nomServicoArea = nomServicoArea;
    }

    public String getCodServicoArea() {
        return codServicoArea;
    }

    public void setCodServicoArea(String codServicoArea) {
        this.codServicoArea = codServicoArea;
    }

    public String getNomServicoArea() {
        return nomServicoArea;
    }

    public void setNomServicoArea(String nomServicoArea) {
        this.nomServicoArea = nomServicoArea;
    }
}
