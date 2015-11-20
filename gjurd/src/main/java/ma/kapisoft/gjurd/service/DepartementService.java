package ma.kapisoft.gjurd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ma.kapisoft.gjurd.dao.DepartementDao;
import ma.kapisoft.gjurd.entities.Departement;



/**
class service du Departement
*/
@Service
public class DepartementService extends AbstractService<Departement> implements IDepartementService {
private static final Log log = LogFactory.getLog(DepartementService.class);	
	@Autowired
	private DepartementDao dao;
	
	public DepartementDao getDao() {
		return dao;
	}

	public void setDao(DepartementDao dao) {
		this.dao = dao;
	}


	
}
