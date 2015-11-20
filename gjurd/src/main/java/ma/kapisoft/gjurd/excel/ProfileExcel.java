package ma.kapisoft.gjurd.excel;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import ma.kapisoft.gjurd.dao.ProfileDao;


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
public class ProfileExcel extends ManipExcel<Profile> {
	private static final Log log = LogFactory.getLog(ProfileExcel.class);
	
	private static final int ATTRIBUTE_UNIQUE_NUM=0;
	

	
	
private static final String ATTRIBUTE_ID="id";
private static final String ATTRIBUTE_LIBELLE="libelle";
private static final String ATTRIBUTE_DESCRITION="descrition";
private static final String ATTRIBUTE_STANDARD="standard";
private static final String ATTRIBUTE_HAVEUSER="haveuser";
private static final String ATTRIBUTE_PREVILEGES="previleges";

	

	
	@Autowired
	private ProfileDao dao;
	
    

	
	public ProfileDao getDao() {
		return dao;
	}

	public void setDao(ProfileDao dao) {
		this.dao = dao;
	}
	
	 

	


	public ProfileExcel() {
		super(Profile.class);
	
	}


    

	
		
	
	@Override
	protected boolean readCell(XSSFCell cell, String attribute, Profile t,
			List<MessageExcel> msgs, int rownum) {
		boolean error=true;
		if(attribute.equals(ATTRIBUTE_ID))
		{
			error=readNumberNotNull(cell, attribute, t, msgs, rownum);
		}
		
		else if(attribute.equals(ATTRIBUTE_LIBELLE))
		{
			error=readNumberNull(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_DESCRITION))
		{
			error=readNumberNull(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_STANDARD))
		{
			error=readBooleanNull(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_HAVEUSER))
		{
			error=readBooleanNull(cell, attribute, t, msgs, rownum);
		}


		return error;
		
	}
	
	

	@Override
	protected Profile rechercher(XSSFRow row) {
		// TODO Auto-generated method stub
		return dao.findByLibelle(row.getCell(ATTRIBUTE_UNIQUE_NUM).getStringCellValue());
	}

	

	

}
