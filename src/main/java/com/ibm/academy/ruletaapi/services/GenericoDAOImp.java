package com.ibm.academy.ruletaapi.services;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class GenericoDAOImp <E,R extends CrudRepository<E,Integer>> implements GenericoDAO<E>
{
    protected final R repository;

    public GenericoDAOImp(R repository) {
        this.repository = repository;
    }

    @Override
    public Optional<E> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public E guardar(E entidad) {
        return repository.save(entidad);
    }

    @Override
    public Iterable<E> mostrarTodos() {
        return repository.findAll();
    }

    @Override
    public void borrar(Integer id) {
        repository.deleteById(id);
    }
}
