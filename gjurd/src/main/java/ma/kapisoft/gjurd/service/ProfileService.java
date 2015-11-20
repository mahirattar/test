package ma.kapisoft.gjurd.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;

import ma.kapisoft.gjurd.dao.PrevilegeDao;
import ma.kapisoft.gjurd.dao.ProfileDao;
import ma.kapisoft.gjurd.entities.CompteUtilisateur;
import ma.kapisoft.gjurd.entities.Previlege;
import ma.kapisoft.gjurd.entities.Profile;
import ma.kapisoft.gjurd.exception.GenericException;
import ma.kapisoft.gjurd.util.Hashage;



/**
class service du Profile
*/
@Service
public class ProfileService extends AbstractService<Profile> implements IProfileService {
private static final Log log = LogFactory.getLog(ProfileService.class);	
private static final String LIBELLE_UNIQUE_ERROR="ProfileUniqueError";
private static final String INTEGRITE_ERROR="DeleteIntegriteError";
private static final String PROFILE_STANDARD_ERROR="ProfileStandardDeleteError";

	@Autowired
	private ProfileDao dao;
	
	@Autowired
	private PrevilegeDao daoPrevilege;
	
	public ProfileDao getDao() {
		return dao;
	}

	public void setDao(ProfileDao dao) {
		this.dao = dao;
	}
	
	

	public PrevilegeDao getDaoPrevilege() {
		return daoPrevilege;
	}

	public void setDaoPrevilege(PrevilegeDao daoPrevilege) {
		this.daoPrevilege = daoPrevilege;
	}

	public List<Previlege> listPrevileges() {
		// TODO Auto-generated method stub
		return daoPrevilege.list();
	}
	
	@Transactional(readOnly = false,rollbackFor=GenericException.class)
	public void add(Profile selected)  throws GenericException{
		log.debug("add "+selected.getLibelle());
		try{
			getDao().add(selected);
		}
		catch(ConstraintViolationException  me)
		{
			log.warn(me.getMessage());
			throw new GenericException(LIBELLE_UNIQUE_ERROR);
		}
		
	
	}
	
	

	 @Transactional(readOnly = false,rollbackFor=GenericException.class)
	    public void remove(Profile selected)  throws GenericException {
			 
				if(!selected.getStandard())
				{
					getDao().delete(selected);
				}
				else
				{
					throw new GenericException(PROFILE_STANDARD_ERROR);	
				}
					
			
	    }

	public Previlege findPrevilege(Integer key) {
		return daoPrevilege.findById(key);
	}

	
}
