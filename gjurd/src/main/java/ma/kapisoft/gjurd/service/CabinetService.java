package ma.kapisoft.gjurd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ma.kapisoft.gjurd.dao.CabinetDao;
import ma.kapisoft.gjurd.entities.Cabinet;



/**
class service du Cabinet
*/
@Service
public class CabinetService extends AbstractService<Cabinet> implements ICabinetService {
private static final Log log = LogFactory.getLog(CabinetService.class);	
	@Autowired
	private CabinetDao dao;
	
	public CabinetDao getDao() {
		return dao;
	}

	public void setDao(CabinetDao dao) {
		this.dao = dao;
	}


	
}
