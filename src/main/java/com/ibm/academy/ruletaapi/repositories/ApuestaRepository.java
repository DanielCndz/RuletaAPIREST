package com.ibm.academy.ruletaapi.repositories;

import com.ibm.academy.ruletaapi.entities.Apuesta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApuestaRepository extends CrudRepository<Apuesta,Integer>
{

}
