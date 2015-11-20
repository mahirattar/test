package ma.kapisoft.gjurd.excel;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import ma.kapisoft.gjurd.dao.CompteUtilisateurDao;


import ma.kapisoft.gjurd.dao.UtilisateurDao;
import ma.kapisoft.gjurd.dao.ProfileDao;
import ma.kapisoft.gjurd.entities.CompteUtilisateur;


import ma.kapisoft.gjurd.entities.Utilisateur;
import ma.kapisoft.gjurd.entities.Profile;
import ma.kapisoft.gjurd.exception.GenericException;
import ma.kapisoft.gjurd.util.Hashage;
import ma.kapisoft.gjurd.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class CompteUtilisateurExcel extends ManipExcel<CompteUtilisateur> {
	private static final Log log = LogFactory.getLog(CompteUtilisateurExcel.class);
	
	private static final int ATTRIBUTE_UNIQUE_NUM=0;
	

	
	
private static final String ATTRIBUTE_ID="id";
private static final String ATTRIBUTE_LOGIN="login";
private static final String ATTRIBUTE_PASSWORD="password";
private static final String ATTRIBUTE_ACTIF="actif";
private static final String ATTRIBUTE_DATEPASSWORD="datepassword";
private static final String ATTRIBUTE_UTILISATEUR="utilisateur";
private static final String ATTRIBUTE_PROFILE="profile";

	

	
	@Autowired
	private CompteUtilisateurDao dao;
	
    

@Autowired
private UtilisateurDao daoUtilisateur;

@Autowired
private ProfileDao daoProfile;

	
	public CompteUtilisateurDao getDao() {
		return dao;
	}

	public void setDao(CompteUtilisateurDao dao) {
		this.dao = dao;
	}
	
	 


	public UtilisateurDao getDaoUtilisateur() {
	
		return daoUtilisateur;
	}

	public void setDaoUtilisateur(UtilisateurDao daoUtilisateur) {
		this.daoUtilisateur = daoUtilisateur;
	}

	public ProfileDao getDaoProfile() {
	
		return daoProfile;
	}

	public void setDaoProfile(ProfileDao daoProfile) {
		this.daoProfile = daoProfile;
	}
	


	public CompteUtilisateurExcel() {
		super(CompteUtilisateur.class);
	
	}


    


	protected boolean readUtilisateurCell(XSSFCell cell, String attribute, CompteUtilisateur t,List<MessageExcel> msgs, int rownum)
	{		
		try{
			if(cell==null||cell.getStringCellValue().trim().equals(""))
			{
				t.setUtilisateur(null);
			}else
			{
				Utilisateur p=null;
				p=daoUtilisateur.findByEmail(cell.getStringCellValue());
				if(p==null)
				{
					MessageExcel msg=new MessageExcel(rownum,ERROR_OBJECT_INEXISTANT);
					String[] params={StringUtil.lastStringAfterSeparator(Utilisateur.class.getCanonicalName(), "."),getLabelAttribute(attribute)};
					msg.setParemetres(params);
					msgs.add(msg);
					return false;
				}
				t.setUtilisateur(p);
			}
		}catch(Exception e)
		{
			MessageExcel msg=new MessageExcel(rownum,ERROR_READ_CELL);
			String[] params={getLabelAttribute(attribute)};
			msg.setParemetres(params);
			msgs.add(msg);
			return false;
		}
		
		return true;
	}

	protected boolean readProfileCell(XSSFCell cell, String attribute, CompteUtilisateur t,List<MessageExcel> msgs, int rownum)
	{		
		try{
			if(cell==null||cell.getStringCellValue().trim().equals(""))
			{
				t.setProfile(null);
			}else
			{
				Profile p=null;
				p=daoProfile.findByLibelle(cell.getStringCellValue());
				if(p==null)
				{
					MessageExcel msg=new MessageExcel(rownum,ERROR_OBJECT_INEXISTANT);
					String[] params={StringUtil.lastStringAfterSeparator(Profile.class.getCanonicalName(), "."),getLabelAttribute(attribute)};
					msg.setParemetres(params);
					msgs.add(msg);
					return false;
				}
				t.setProfile(p);
			}
		}catch(Exception e)
		{
			MessageExcel msg=new MessageExcel(rownum,ERROR_READ_CELL);
			String[] params={getLabelAttribute(attribute)};
			msg.setParemetres(params);
			msgs.add(msg);
			return false;
		}
		
		return true;
	}
	
		
	
	@Override
	protected boolean readCell(XSSFCell cell, String attribute, CompteUtilisateur t,
			List<MessageExcel> msgs, int rownum) {
		boolean error=true;
		if(attribute.equals(ATTRIBUTE_ID))
		{
			error=readNumberNotNull(cell, attribute, t, msgs, rownum);
		}
		
		else if(attribute.equals(ATTRIBUTE_LOGIN))
		{
			error=readNumberNotNull(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_PASSWORD))
		{
			error=readNumberNotNull(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_ACTIF))
		{
			error=readBooleanNull(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_DATEPASSWORD))
		{
			error=readDateNull(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_UTILISATEUR))
		{
			error=readUtilisateurCell(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_PROFILE))
		{
			error=readProfileCell(cell, attribute, t, msgs, rownum);
		}


		return error;
		
	}
	
	

	@Override
	protected CompteUtilisateur rechercher(XSSFRow row) {
		// TODO Auto-generated method stub
		return dao.findByLogin(row.getCell(ATTRIBUTE_UNIQUE_NUM).getStringCellValue());
	}

	

	

}
