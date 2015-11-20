package ma.kapisoft.gjurd.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ma.kapisoft.gjurd.entities.Consultation;



/**
class d'accès aux données DAO du Consultation
*/
@Repository
public class ConsultationDao extends AbstractDao<Consultation>{
private static final Log log = LogFactory.getLog(ConsultationDao.class);	

	@Autowired
	private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public ConsultationDao() {
		super(Consultation.class);
	}	
}
