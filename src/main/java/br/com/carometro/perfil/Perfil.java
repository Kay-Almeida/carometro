package br.com.carometro.perfil;

import jakarta.persistence.*;
import br.com.carometro.usuario.*;

@Entity
@Table(name = "perfil")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String curso;
    private String urlLinkedIn;
    private String urlGitHub;
    private String urlLattes;
    private String localTrabalho;
    private String funcao;
    private String tempoTrabalho;
    private String comentarioFoto;
    private String comentarioLivre;
    private String urlFoto;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getUrlLinkedIn() {
        return urlLinkedIn;
    }

    public void setUrlLinkedIn(String urlLinkedIn) {
        this.urlLinkedIn = urlLinkedIn;
    }

    public String getUrlGitHub() {
        return urlGitHub;
    }

    public void setUrlGitHub(String urlGitHub) {
        this.urlGitHub = urlGitHub;
    }

    public String getUrlLattes() {
        return urlLattes;
    }

    public void setUrlLattes(String urlLattes) {
        this.urlLattes = urlLattes;
    }

    public String getLocalTrabalho() {
        return localTrabalho;
    }

    public void setLocalTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getTempoTrabalho() {
        return tempoTrabalho;
    }

    public void setTempoTrabalho(String tempoTrabalho) {
        this.tempoTrabalho = tempoTrabalho;
    }

    public String getComentarioFoto() {
        return comentarioFoto;
    }

    public void setComentarioFoto(String comentarioFoto) {
        this.comentarioFoto = comentarioFoto;
    }

    public String getComentarioLivre() {
        return comentarioLivre;
    }

    public void setComentarioLivre(String comentarioLivre) {
        this.comentarioLivre = comentarioLivre;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
