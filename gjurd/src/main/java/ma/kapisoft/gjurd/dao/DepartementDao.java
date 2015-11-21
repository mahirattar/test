package ma.kapisoft.gjurd.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import ma.kapisoft.gjurd.entities.Departement;
import ma.kapisoft.gjurd.entities.Utilisateur;



/**
class d'accès aux données DAO du Departement
*/
@Repository
public class DepartementDao extends AbstractDao<Departement>{
private static final Log log = LogFactory.getLog(DepartementDao.class);	

	

	@Autowired
	private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public DepartementDao() {
		super(Departement.class);
	}	
	
	
	public Departement findByNom(String nom) {
		// TODO Auto-generated method stub
		log.debug("getting getDepartement instance with nom: " + nom);
		List<Departement> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Departement o WHERE o.nom=:p").setParameter("p", nom).list();
		if(list.size()>0)
			return list.get(0);
		return null;
	}	

public List<Departement> findByDescription(String description) {
		// TODO Auto-generated method stub
		log.debug("getting getDepartement instances with description: " + description);
		List<Departement> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Departement o WHERE o.description=:p").setParameter("p", description).list();
		
		return list;
	}	

	public List<Departement> findByResponsable(Utilisateur responsable) {
		// TODO Auto-generated method stub
		log.debug("getting getDepartement instances with responsable: " + responsable);
		List<Departement> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Departement o WHERE o.responsable=:p").setParameter("p", responsable).list();
		
		return list;
	}	
	
	public Utilisateur findResponsableServiceJuridique()
	{
		return (Utilisateur) getSessionFactory().getCurrentSession().createQuery("SELECT o.responsable from Departement o WHERE o.nom=:p").setParameter("p", Departement.SERVICE_JURIQIQUE).uniqueResult();
	}
	

public List<Departement> findByCabinet(String cabinet) {
		// TODO Auto-generated method stub
		log.debug("getting getDepartement instances with cabinet: " + cabinet);
		List<Departement> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Departement o WHERE o.cabinet=:p").setParameter("p", cabinet).list();
		
		return list;
	}	
	
	
}
