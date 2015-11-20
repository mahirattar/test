package ma.kapisoft.gjurd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ma.kapisoft.gjurd.dao.UtilisateurDao;
import ma.kapisoft.gjurd.entities.Utilisateur;



/**
class service du Utilisateur
*/
@Service
public class UtilisateurService extends AbstractService<Utilisateur> implements IUtilisateurService {
private static final Log log = LogFactory.getLog(UtilisateurService.class);	
	@Autowired
	private UtilisateurDao dao;
	
	public UtilisateurDao getDao() {
		return dao;
	}

	public void setDao(UtilisateurDao dao) {
		this.dao = dao;
	}


	
}
