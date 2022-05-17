package com.ibm.academy.ruletaapi.services;


import com.ibm.academy.ruletaapi.entities.Apuesta;
import com.ibm.academy.ruletaapi.entities.Ruleta;

import java.util.List;
import java.util.Optional;

public interface RuletaDAO extends GenericoDAO<Ruleta>
{
    public Integer guardarRuleta(Ruleta ruleta);
    public Boolean abrirRuleta(Ruleta ruleta);
    public List<Apuesta> cerrarRuleta(Ruleta ruleta);
}
