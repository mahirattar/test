package ma.kapisoft.gjurd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ma.kapisoft.gjurd.dao.ConsultationDao;
import ma.kapisoft.gjurd.entities.Consultation;



/**
class service du Consultation
*/
@Service
public class ConsultationService extends AbstractService<Consultation> implements IConsultationService {
private static final Log log = LogFactory.getLog(ConsultationService.class);	
	@Autowired
	private ConsultationDao dao;
	
	public ConsultationDao getDao() {
		return dao;
	}

	public void setDao(ConsultationDao dao) {
		this.dao = dao;
	}


	
}
