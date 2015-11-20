package ma.kapisoft.gjurd.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import ma.kapisoft.gjurd.dao.AbstractDao;
import ma.kapisoft.gjurd.exception.GenericException;



@Transactional(readOnly = true)
public abstract class AbstractService<T> implements Serializable {
	private static final Log log = LogFactory.getLog(AbstractService.class);
	
	    public AbstractService() {
	        
	    }

	    public abstract AbstractDao<T> getDao();

	
		
		 @Transactional(readOnly = false,rollbackFor=GenericException.class)
	    public void add(T entity)  throws GenericException{
			 getDao().add(entity);
	    }
		 @Transactional(readOnly = false,rollbackFor=GenericException.class)
	    public void edit(T entity)  throws GenericException, Exception {
			 getDao().modify(entity);
	    }
		 @Transactional(readOnly = false,rollbackFor=GenericException.class)
	    public void remove(T entity)  throws GenericException {
			 getDao().delete(entity);;
	    }

	    public T find(Integer id) {
	        return getDao().findById(id);
	    }

	    public List<T> list()  {
	      
	        return getDao().list();
	    }

	 

}
