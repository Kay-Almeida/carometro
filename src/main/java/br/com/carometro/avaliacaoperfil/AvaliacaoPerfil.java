package br.com.carometro.avaliacaoperfil;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import br.com.carometro.perfil.*;
import br.com.carometro.usuario.*;

@Entity
@Table(name = "avaliacao_perfil")
public class AvaliacaoPerfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "avaliador_id", nullable = false)
    private Usuario avaliador; // Coordenador ou Admin

    private String comentario;

    @Enumerated(EnumType.STRING)
    private StatusPerfil status; // APROVADO, REPROVADO, etc.

    private LocalDateTime dataHora;

    public AvaliacaoPerfil() {
        this.dataHora = LocalDateTime.now();
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Usuario getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(Usuario avaliador) {
        this.avaliador = avaliador;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public StatusPerfil getStatus() {
        return status;
    }

    public void setStatus(StatusPerfil status) {
        this.status = status;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}

