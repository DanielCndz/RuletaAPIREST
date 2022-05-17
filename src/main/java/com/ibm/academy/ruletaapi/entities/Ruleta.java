package com.ibm.academy.ruletaapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "ruletas",schema = "ruletasdb")
public class Ruleta implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "estado_ruleta")
    private Boolean estadoRuleta;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @ToString.Exclude
    @OneToMany(mappedBy  = "ruleta", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "ruleta"})
    private Set<Apuesta> apuestas;

    public Ruleta(Integer id, Boolean estadoRuleta, Set<Apuesta> apuestas) {
        this.id = id;
        this.estadoRuleta = estadoRuleta;
        this.apuestas = apuestas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ruleta ruleta = (Ruleta) o;
        return Objects.equals(id, ruleta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PrePersist
    private void antesPersistir()
    {
        this.fechaCreacion=new Date();
    }

    @PreUpdate
    private void antesActualizar()
    {
        this.fechaModificacion=new Date();
    }

    private static final long serialVersionUID = -6367307561218372277L;
}
