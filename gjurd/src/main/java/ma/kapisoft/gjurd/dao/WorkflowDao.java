package ma.kapisoft.gjurd.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import ma.kapisoft.gjurd.entities.Workflow;



/**
class d'accès aux données DAO du Workflow
*/
@Repository
public class WorkflowDao extends AbstractDao<Workflow>{
private static final Log log = LogFactory.getLog(WorkflowDao.class);	

	@Autowired
	private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public WorkflowDao() {
		super(Workflow.class);
	}	
	
	
	public Workflow findByNom(String nom) {
		// TODO Auto-generated method stub
		log.debug("getting getWorkflow instance with nom: " + nom);
		List<Workflow> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Workflow o WHERE o.nom=:p").setParameter("p", nom).list();
		if(list.size()>0)
			return list.get(0);
		return null;
	}	

public List<Workflow> findByDescription(String description) {
		// TODO Auto-generated method stub
		log.debug("getting getWorkflow instances with description: " + description);
		List<Workflow> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Workflow o WHERE o.description=:p").setParameter("p", description).list();
		
		return list;
	}	

public List<Workflow> findByEtat(String etat) {
		// TODO Auto-generated method stub
		log.debug("getting getWorkflow instances with etat: " + etat);
		List<Workflow> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Workflow o WHERE o.etat=:p").setParameter("p", etat).list();
		
		return list;
	}	

public List<Workflow> findByEtapes(String etapes) {
		// TODO Auto-generated method stub
		log.debug("getting getWorkflow instances with etapes: " + etapes);
		List<Workflow> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Workflow o WHERE o.etapes=:p").setParameter("p", etapes).list();
		
		return list;
	}	
	
	
}
