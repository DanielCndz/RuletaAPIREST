package com.ibm.academy.ruletaapi.services;


import com.ibm.academy.ruletaapi.entities.Apuesta;
import com.ibm.academy.ruletaapi.entities.Ruleta;

public interface ApuestaDAO extends GenericoDAO<Apuesta>
{
    public void asignarRuleta(Apuesta apuesta , Ruleta ruleta);
}
