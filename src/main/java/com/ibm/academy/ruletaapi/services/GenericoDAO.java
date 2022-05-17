package com.ibm.academy.ruletaapi.services;

import java.util.Optional;

public interface GenericoDAO<E>
{
    public Optional<E> buscarPorId(Integer id);
    public E guardar(E entidad);
    public Iterable<E> mostrarTodos();
    public void borrar(Integer id);
}
