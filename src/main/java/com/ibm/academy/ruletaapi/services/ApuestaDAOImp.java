package com.ibm.academy.ruletaapi.services;

import com.ibm.academy.ruletaapi.entities.Apuesta;
import com.ibm.academy.ruletaapi.entities.Ruleta;
import com.ibm.academy.ruletaapi.repositories.ApuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApuestaDAOImp extends GenericoDAOImp<Apuesta, ApuestaRepository> implements ApuestaDAO
{
    @Autowired
    public ApuestaDAOImp(ApuestaRepository repository) {
        super(repository);
    }

    @Override
    public void asignarRuleta(Apuesta apuesta, Ruleta ruleta) {
        apuesta.setRuleta(ruleta);
        repository.save(apuesta);
    }
}
