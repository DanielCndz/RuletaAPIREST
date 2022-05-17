package com.ibm.academy.ruletaapi.services;

import com.ibm.academy.ruletaapi.entities.Apuesta;
import com.ibm.academy.ruletaapi.entities.Ruleta;
import com.ibm.academy.ruletaapi.repositories.RuletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RuletaDAOImp extends GenericoDAOImp<Ruleta, RuletaRepository> implements RuletaDAO
{
    @Autowired
    public RuletaDAOImp(RuletaRepository repository) {
        super(repository);
    }

    @Override
    public Integer guardarRuleta(Ruleta ruleta)
    {
        Integer idRuleta =(repository.save(ruleta)).getId();
        return idRuleta;
    }

    @Override
    public Boolean abrirRuleta(Ruleta ruleta)
    {
        ruleta.setEstadoRuleta(Boolean.TRUE);
        repository.save(ruleta);
        return Boolean.TRUE;
    }

    @Override
    public List<Apuesta> cerrarRuleta(Ruleta ruleta)
    {
        ruleta.setEstadoRuleta(Boolean.FALSE);
        List<Apuesta> apuestas = (List<Apuesta>) ruleta.getApuestas();
        apuestas.clear();
        ruleta.setApuestas((Set<Apuesta>) apuestas);
        repository.save(ruleta);
        return apuestas;
    }

}
