package com.ibm.academy.ruletaapi.repositories;

import com.ibm.academy.ruletaapi.entities.Ruleta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuletaRepository extends CrudRepository<Ruleta,Integer>
{

}
