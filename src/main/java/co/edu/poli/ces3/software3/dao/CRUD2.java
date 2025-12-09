package co.edu.poli.ces3.software3.dao;

import java.util.List;

public interface CRUD2<T, id> {

    public T insert (id id, T t);
    public void insert (id id,List<T> t);

    public T update(id id, T t);
    public boolean delete(id id, id a);

    public List<T> findAll(id id);


    public List<T> findById(id id);

    public boolean updatePartial(T t);

}
