package ma.kapisoft.gjurd.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import ma.kapisoft.gjurd.entities.Cabinet;



/**
class d'accès aux données DAO du Cabinet
*/
@Repository
public class CabinetDao extends AbstractDao<Cabinet>{
private static final Log log = LogFactory.getLog(CabinetDao.class);	

	@Autowired
	private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public CabinetDao() {
		super(Cabinet.class);
	}	
	
	
	public Cabinet findByNom(String nom) {
		// TODO Auto-generated method stub
		log.debug("getting getCabinet instance with nom: " + nom);
		List<Cabinet> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Cabinet o WHERE o.nom=:p").setParameter("p", nom).list();
		if(list.size()>0)
			return list.get(0);
		return null;
	}	

public List<Cabinet> findByDescription(String description) {
		// TODO Auto-generated method stub
		log.debug("getting getCabinet instances with description: " + description);
		List<Cabinet> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Cabinet o WHERE o.description=:p").setParameter("p", description).list();
		
		return list;
	}	

public List<Cabinet> findByEmail(String email) {
		// TODO Auto-generated method stub
		log.debug("getting getCabinet instances with email: " + email);
		List<Cabinet> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Cabinet o WHERE o.email=:p").setParameter("p", email).list();
		
		return list;
	}	

public List<Cabinet> findByTel(String tel) {
		// TODO Auto-generated method stub
		log.debug("getting getCabinet instances with tel: " + tel);
		List<Cabinet> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Cabinet o WHERE o.tel=:p").setParameter("p", tel).list();
		
		return list;
	}	

public List<Cabinet> findByContact(String contact) {
		// TODO Auto-generated method stub
		log.debug("getting getCabinet instances with contact: " + contact);
		List<Cabinet> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Cabinet o WHERE o.contact=:p").setParameter("p", contact).list();
		
		return list;
	}	
	
	
}
