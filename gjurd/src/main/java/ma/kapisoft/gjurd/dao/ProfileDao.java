package ma.kapisoft.gjurd.dao;


import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ma.kapisoft.gjurd.entities.CompteUtilisateur;
import ma.kapisoft.gjurd.entities.Profile;
import ma.kapisoft.gjurd.entities.Utilisateur;



/**
class d'accès aux données DAO du Profile
*/
@Repository
public class ProfileDao extends AbstractDao<Profile>{
private static final Log log = LogFactory.getLog(ProfileDao.class);	

	@Autowired
	private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public ProfileDao() {
		super(Profile.class);
	}


	public Profile findByLibelle(String libelle) {
		// TODO Auto-generated method stub
		log.debug("getting Profile instance with login: " + libelle);
		List<Profile> list  = getSessionFactory().getCurrentSession().createQuery("SELECT p from Profile p WHERE p.libelle=:l").setParameter("l", libelle).list();
		if(list.size()>0)
			return list.get(0);
		return null;
	}	
}
