package ma.kapisoft.gjurd.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import ma.kapisoft.gjurd.entities.Action;



/**
class d'accès aux données DAO du Action
*/
@Repository
public class ActionDao extends AbstractDao<Action>{
private static final Log log = LogFactory.getLog(ActionDao.class);	

	@Autowired
	private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public ActionDao() {
		super(Action.class);
	}	
	
	
	

public List<Action> findByNom(String nom) {
		// TODO Auto-generated method stub
		log.debug("getting getAction instances with nom: " + nom);
		List<Action> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Action o WHERE o.nom=:p").setParameter("p", nom).list();
		
		return list;
	}	

public List<Action> findByDescription(String description) {
		// TODO Auto-generated method stub
		log.debug("getting getAction instances with description: " + description);
		List<Action> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Action o WHERE o.description=:p").setParameter("p", description).list();
		
		return list;
	}	

public List<Action> findByService(String service) {
		// TODO Auto-generated method stub
		log.debug("getting getAction instances with service: " + service);
		List<Action> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Action o WHERE o.service=:p").setParameter("p", service).list();
		
		return list;
	}	

public List<Action> findByProfile(String profile) {
		// TODO Auto-generated method stub
		log.debug("getting getAction instances with profile: " + profile);
		List<Action> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Action o WHERE o.profile=:p").setParameter("p", profile).list();
		
		return list;
	}	

public List<Action> findByEtape(String etape) {
		// TODO Auto-generated method stub
		log.debug("getting getAction instances with etape: " + etape);
		List<Action> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Action o WHERE o.etape=:p").setParameter("p", etape).list();
		
		return list;
	}	
	
	
}
