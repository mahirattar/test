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
class d'accès aux données DAO du CompteUtilisateur
*/
@Repository
public class CompteUtilisateurDao extends AbstractDao<CompteUtilisateur>{
private static final Log log = LogFactory.getLog(CompteUtilisateurDao.class);	

	@Autowired
	private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public CompteUtilisateurDao() {
		super(CompteUtilisateur.class);
	}


	public CompteUtilisateur findByLogin(String login) {
		// TODO Auto-generated method stub
		log.debug("getting getCompteUtilisateur instance with login: " + login);
		List<CompteUtilisateur> list  = getSessionFactory().getCurrentSession().createQuery("SELECT u from CompteUtilisateur u WHERE u.login=:l").setParameter("l", login).list();
		if(list.size()>0)
			return list.get(0);
		return null;
	}	
}
