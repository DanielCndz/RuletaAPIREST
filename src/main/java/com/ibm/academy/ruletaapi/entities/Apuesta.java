package com.ibm.academy.ruletaapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibm.academy.ruletaapi.enums.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "apuestas",schema = "ruletasdb")
public class Apuesta implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DecimalMin(value = "0",message = "Los numeros van del 0 al 36")
    @DecimalMax(value = "36",message = "Los numeros van del 0 al 36")
    @Column(name = "numero_apuesta")
    private Integer numeroApuesta;

    @Column(name = "color_apuesta")
    @Enumerated(EnumType.STRING)
    private Color colorApuesta;

    @DecimalMin(value = "1",message = "Las apuestas van de los 1 a 10000 dolares")
    @DecimalMax(value = "10000",message = "Las apuestas van de los 1 a 10000 dolares")
    @Column(name = "dinero_apostado")
    private Integer dineroApostado;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @ManyToOne(optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ruleta_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "apuestas"})
    private Ruleta ruleta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apuesta apuesta = (Apuesta) o;
        return Objects.equals(id, apuesta.id);
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

    private static final long serialVersionUID = 125010407035650633L;
}
