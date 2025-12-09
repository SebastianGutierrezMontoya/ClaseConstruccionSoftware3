package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.model.DetalleMateria;

import java.util.List;

public interface CRUD<T, id> {

    public T insert (id id, T t);
    public T update(id id, T t);
    public boolean delete(id id);
    public List<T> findAll();
    public T findById(id id);
    public boolean updatePartial(T t);

}
