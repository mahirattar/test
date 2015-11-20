package ma.kapisoft.gjurd.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import ma.kapisoft.gjurd.entities.Condition;



/**
class d'accès aux données DAO du Condition
*/
@Repository
public class ConditionDao extends AbstractDao<Condition>{
private static final Log log = LogFactory.getLog(ConditionDao.class);	

	@Autowired
	private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public ConditionDao() {
		super(Condition.class);
	}	
	
	
	

public List<Condition> findByAttribut(String attribut) {
		// TODO Auto-generated method stub
		log.debug("getting getCondition instances with attribut: " + attribut);
		List<Condition> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Condition o WHERE o.attribut=:p").setParameter("p", attribut).list();
		
		return list;
	}	

public List<Condition> findByOperateur(String operateur) {
		// TODO Auto-generated method stub
		log.debug("getting getCondition instances with operateur: " + operateur);
		List<Condition> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Condition o WHERE o.operateur=:p").setParameter("p", operateur).list();
		
		return list;
	}	

public List<Condition> findByValeur1(String valeur1) {
		// TODO Auto-generated method stub
		log.debug("getting getCondition instances with valeur1: " + valeur1);
		List<Condition> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Condition o WHERE o.valeur1=:p").setParameter("p", valeur1).list();
		
		return list;
	}	

public List<Condition> findByValeur2(String valeur2) {
		// TODO Auto-generated method stub
		log.debug("getting getCondition instances with valeur2: " + valeur2);
		List<Condition> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Condition o WHERE o.valeur2=:p").setParameter("p", valeur2).list();
		
		return list;
	}	

public List<Condition> findByEtape1(String etape1) {
		// TODO Auto-generated method stub
		log.debug("getting getCondition instances with etape1: " + etape1);
		List<Condition> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Condition o WHERE o.etape1=:p").setParameter("p", etape1).list();
		
		return list;
	}	

public List<Condition> findByEtape2(String etape2) {
		// TODO Auto-generated method stub
		log.debug("getting getCondition instances with etape2: " + etape2);
		List<Condition> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Condition o WHERE o.etape2=:p").setParameter("p", etape2).list();
		
		return list;
	}	

public List<Condition> findByEtape3(String etape3) {
		// TODO Auto-generated method stub
		log.debug("getting getCondition instances with etape3: " + etape3);
		List<Condition> list  = getSessionFactory().getCurrentSession().createQuery("SELECT o from Condition o WHERE o.etape3=:p").setParameter("p", etape3).list();
		
		return list;
	}	
	
	
}
