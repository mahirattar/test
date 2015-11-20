package ma.kapisoft.gjurd.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;

import ma.kapisoft.gjurd.dao.CompteUtilisateurDao;
import ma.kapisoft.gjurd.entities.CompteUtilisateur;
import ma.kapisoft.gjurd.entities.Utilisateur;
import ma.kapisoft.gjurd.exception.GenericException;
import ma.kapisoft.gjurd.util.Hashage;



/**
class service du CompteUtilisateur
*/
@Service
public class CompteUtilisateurService extends AbstractService<CompteUtilisateur> implements ICompteUtilisateurService {
	
	private static final Log log = LogFactory.getLog(CompteUtilisateurService.class);	
	private static final String HASHAGE_TYPE="SHA-256";
	private static final String HASHAGE_ERROR="CompteUtilisateurPassHashError";
	private static final String LOGIN_UNIQUE_ERROR="CompteUtilisateurUniqueError";
	private static final String DELETE_ERROR_INTEGRITE="CompteUtilisateurDeleteIntegriteError";
	private static final String PASSWORD_CURRENT_ERROR="CurrentUserPasswordError";
	private static final String UTILISATEUR_REQUIRED="CompteUtilisateurUtilisateurRequired";
	
	@Autowired
	private CompteUtilisateurDao dao;
	
	public CompteUtilisateurDao getDao() {
		return dao;
	}

	public void setDao(CompteUtilisateurDao dao) {
		this.dao = dao;
	}

	public CompteUtilisateur getCompteUtilisateur(String login){
		// TODO Auto-generated method stub
		return dao.findByLogin(login);
	}
	
	@Transactional(readOnly = false,rollbackFor=GenericException.class)
	public void add(CompteUtilisateur selected)  throws GenericException{
		log.debug("add "+selected.getLogin());
		try{
			if(selected.getUtilisateur()==null && selected.getProfile()!=null && selected.getProfile().getHaveuser())
			{
				throw new GenericException(UTILISATEUR_REQUIRED);
			}
			else
			{
				selected.setPassword(Hashage.encode(selected.getPassword(), HASHAGE_TYPE));
				selected.setDatepassword(new Date());
				getDao().add(selected);
			}
		}catch(NoSuchAlgorithmException ex)
		{
			throw new GenericException(HASHAGE_ERROR);
		}
		catch(ConstraintViolationException  me)
		{
			log.warn(me.getMessage());
			throw new GenericException(LOGIN_UNIQUE_ERROR);
		}
		
	
	}
	
	 @Transactional(readOnly = false,rollbackFor=GenericException.class)
	    public void edit(CompteUtilisateur selected)  throws Exception,GenericException {
		 log.debug("edit "+selected.getLogin());
		 try{
			 if(selected.getUtilisateur()==null && selected.getProfile()!=null && selected.getProfile().getHaveuser())
				{
					throw new GenericException(UTILISATEUR_REQUIRED);
				}
				else
				{
					getDao().modify(selected);
				}
		 }catch(org.springframework.dao.DataIntegrityViolationException  me)
		 {
			 log.warn(me.getMessage());
			 throw new GenericException(LOGIN_UNIQUE_ERROR);
		 }	 
		    }
	
	@Transactional(readOnly = false,rollbackFor=GenericException.class)
	public void editPassword(CompteUtilisateur selected)  throws GenericException
	{
		log.debug("editPassword "+selected.getLogin());
		try{
			selected.setPassword(Hashage.encode(selected.getPassword(), HASHAGE_TYPE));
			selected.setDatepassword(new Date());
			dao.modify(selected);
		}catch(NoSuchAlgorithmException ex)
		{
			throw new GenericException(HASHAGE_ERROR);
		}
		
	}
	
	 
	@Transactional(readOnly = false,rollbackFor=GenericException.class)
	public void editPasswordCurrentUser(CompteUtilisateur selected,
			String ancienpassword, String newpassword) throws GenericException {
		log.debug("editPasswordCurrentUser "+selected.getLogin());
		try{
			if(selected.getPassword().equals(Hashage.encode(ancienpassword, HASHAGE_TYPE)))
			{
				selected.setPassword(Hashage.encode(newpassword, HASHAGE_TYPE));
				selected.setDatepassword(new Date());
				dao.modify(selected);
			}
			else
			{
				throw new GenericException(PASSWORD_CURRENT_ERROR);
			}
		}catch(NoSuchAlgorithmException ex)
		{
			throw new GenericException(HASHAGE_ERROR);
		}
		
	}



	
}
