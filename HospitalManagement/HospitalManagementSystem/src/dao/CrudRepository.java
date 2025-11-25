package dao;

import java.util.List;

public interface CrudRepository<T> {
    T save(T t) throws Exception;
    T findById(int id) throws Exception;
    List<T> findAll() throws Exception;
    void delete(int id) throws Exception;
}
