package com.trainee2.dominio;

public class Curriculo {
    private String curso;
    private String instituicao;
    private String area;
    private Estagiario estagiario;
    private long id;

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public String getInstituicao() { return instituicao; }
    public void setInstituicao(String instituicao) { this.instituicao = instituicao; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Estagiario getEstagiario() {
        return estagiario; }
    public void setEstagiario(Estagiario estagiario) { this.estagiario = estagiario; }
}
