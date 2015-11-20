package ma.kapisoft.gjurd.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import ma.kapisoft.gjurd.entities.Delimiteur;



/**
class d'accès aux données DAO du Delimiteur
*/
@Repository
public class DelimiteurDao extends AbstractDao<Delimiteur>{
private static final Log log = LogFactory.getLog(DelimiteurDao.class);	

	@Autowired
	private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public DelimiteurDao() {
		super(Delimiteur.class);
	}	
	
	
	

public List<Delimiteur> findByFin(String fin) {
		// TODO Auto-generated method stub
		log.debug("getting getDelimiteur instances with fin: " + fin);
		List<Delimiteur> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Delimiteur o WHERE o.fin=:p").setParameter("p", fin).list();
		
		return list;
	}	
	
	
}
