package ma.kapisoft.gjurd.excel;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import ma.kapisoft.gjurd.dao.CabinetDao;


import ma.kapisoft.gjurd.entities.Cabinet;


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
public class CabinetExcel extends ManipExcel<Cabinet> {
	private static final Log log = LogFactory.getLog(CabinetExcel.class);
	
	private static final int ATTRIBUTE_UNIQUE_NUM=0;
	

	
	
private static final String ATTRIBUTE_ID="id";
private static final String ATTRIBUTE_NOM="nom";
private static final String ATTRIBUTE_DESCRIPTION="description";
private static final String ATTRIBUTE_EMAIL="email";
private static final String ATTRIBUTE_TEL="tel";
private static final String ATTRIBUTE_CONTACT="contact";

	

	
	@Autowired
	private CabinetDao dao;
	
    

	
	public CabinetDao getDao() {
		return dao;
	}

	public void setDao(CabinetDao dao) {
		this.dao = dao;
	}
	
	 

	


	public CabinetExcel() {
		super(Cabinet.class);
	
	}


    

	
		
	
	@Override
	protected boolean readCell(XSSFCell cell, String attribute, Cabinet t,
			List<MessageExcel> msgs, int rownum) {
		boolean error=true;
		if(attribute.equals(ATTRIBUTE_ID))
		{
			error=readNumberNotNull(cell, attribute, t, msgs, rownum);
		}
		
		else if(attribute.equals(ATTRIBUTE_NOM))
		{
			error=readNumberNotNull(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_DESCRIPTION))
		{
			error=readNumberNull(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_EMAIL))
		{
			error=readNumberNull(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_TEL))
		{
			error=readNumberNull(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_CONTACT))
		{
			error=readNumberNull(cell, attribute, t, msgs, rownum);
		}


		return error;
		
	}
	
	

	@Override
	protected Cabinet rechercher(XSSFRow row) {
		// TODO Auto-generated method stub
		return dao.findByNom(row.getCell(ATTRIBUTE_UNIQUE_NUM).getStringCellValue());
	}

	

	

}
