/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.kapisoft.gjurd.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import ma.kapisoft.gjurd.entities.Utilisateur;

/**
 *
 * @author khalid
 */
public abstract class AbstractDao<T> implements Serializable{
	private static final Log log = LogFactory.getLog(AbstractDao.class);
	
    private Class<T> entityClass;

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract SessionFactory getSessionFactory();
   
    public void add(T entity) {
    	log.debug("persisting "+entityClass.getSimpleName()+" instance");
		try {
			getSessionFactory().getCurrentSession().persist(entity);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
    }
    
    public void delete(T persistentInstance) {
		log.debug("deleting "+entityClass.getSimpleName()+" instance");
		try {
			getSessionFactory().getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public T modify(T detachedInstance) {
		log.debug("merging "+entityClass.getSimpleName()+" instance");
		try {
			getSessionFactory().getCurrentSession().merge(detachedInstance);
			
			log.debug("merge successful");
			return detachedInstance;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	public T findById(java.lang.Integer id) {
		log.debug("getting "+entityClass.getSimpleName()+" instance with id: " + id);
		try {
			T instance = (T) getSessionFactory().getCurrentSession().get(
					entityClass.getName(), id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	public List<T> list() {
		log.debug("getting list of "+entityClass.getSimpleName());
			 List<T> list = getSessionFactory().getCurrentSession().createQuery("from "+entityClass.getSimpleName()).list();
			  return list;
		}


  
}
