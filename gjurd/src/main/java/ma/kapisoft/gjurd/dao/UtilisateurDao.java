package ma.kapisoft.gjurd.dao;


import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ma.kapisoft.gjurd.entities.CompteUtilisateur;
import ma.kapisoft.gjurd.entities.Utilisateur;



/**
class d'accès aux données DAO du Utilisateur
*/
@Repository
public class UtilisateurDao extends AbstractDao<Utilisateur>{
private static final Log log = LogFactory.getLog(UtilisateurDao.class);	

	@Autowired
	private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public UtilisateurDao() {
		super(Utilisateur.class);
	}


	public Utilisateur findByEmail(String email) {
		// TODO Auto-generated method stub
		log.debug("getting Utilisateur instance with email: " + email);
		List<Utilisateur> list  = getSessionFactory().getCurrentSession().createQuery("SELECT u from Utilisateur u WHERE u.email=:l").setParameter("l", email).list();
		if(list.size()>0)
			return list.get(0);
		return null;
	}


	
}
