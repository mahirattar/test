package ma.kapisoft.gjurd.dao;


import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ma.kapisoft.gjurd.entities.Previlege;



/**
class d'accès aux données DAO du Previlege
*/
@Repository
public class PrevilegeDao extends AbstractDao<Previlege>{
private static final Log log = LogFactory.getLog(PrevilegeDao.class);	

	@Autowired
	private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public PrevilegeDao() {
		super(Previlege.class);
	}	
	
	
	public Previlege findByNom(String nom) {
		// TODO Auto-generated method stub
		log.debug("getting getPrevilege instance with nom: " + nom);
		List<Previlege> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Previlege o WHERE o.nom=:p").setParameter("p", nom).list();
		if(list.size()>0)
			return list.get(0);
		return null;
	}	
	
	
}
