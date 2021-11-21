package com.epam.ticket.dao.api;

import com.epam.ticket.exception.EntityNotFoundException;

import java.util.List;

public interface BaseDAO<E> {
    List<E> findAll();

    E findOne(long id) throws EntityNotFoundException;

    E update(E entity) throws EntityNotFoundException;

    E create(E entity);

    boolean remove(long id) throws EntityNotFoundException;
}
