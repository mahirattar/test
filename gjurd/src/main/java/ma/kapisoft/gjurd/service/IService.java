package ma.kapisoft.gjurd.service;

import java.util.List;

import ma.kapisoft.gjurd.exception.GenericException;

import org.springframework.transaction.annotation.Transactional;

public interface IService<T> {

	 public void add(T entity) throws GenericException;
	 public void edit(T entity) throws GenericException, Exception;
	 public void remove(T entity)  throws GenericException;
     public T find(Integer id)  ;
     public List<T> list() ;
}
