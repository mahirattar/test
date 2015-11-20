package ma.kapisoft.gjurd.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import ma.kapisoft.gjurd.entities.Etape;



/**
class d'accès aux données DAO du Etape
*/
@Repository
public class EtapeDao extends AbstractDao<Etape>{
private static final Log log = LogFactory.getLog(EtapeDao.class);	

	@Autowired
	private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public EtapeDao() {
		super(Etape.class);
	}	
	
	
	

public List<Etape> findByCabinet(String cabinet) {
		// TODO Auto-generated method stub
		log.debug("getting getEtape instances with cabinet: " + cabinet);
		List<Etape> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Etape o WHERE o.cabinet=:p").setParameter("p", cabinet).list();
		
		return list;
	}	

public List<Etape> findByX(String x) {
		// TODO Auto-generated method stub
		log.debug("getting getEtape instances with x: " + x);
		List<Etape> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Etape o WHERE o.x=:p").setParameter("p", x).list();
		
		return list;
	}	

public List<Etape> findByY(String y) {
		// TODO Auto-generated method stub
		log.debug("getting getEtape instances with y: " + y);
		List<Etape> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Etape o WHERE o.y=:p").setParameter("p", y).list();
		
		return list;
	}	

public List<Etape> findByWorkflow(String workflow) {
		// TODO Auto-generated method stub
		log.debug("getting getEtape instances with workflow: " + workflow);
		List<Etape> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Etape o WHERE o.workflow=:p").setParameter("p", workflow).list();
		
		return list;
	}	
	
	
}
