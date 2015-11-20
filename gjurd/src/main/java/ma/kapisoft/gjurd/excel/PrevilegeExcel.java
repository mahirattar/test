package ma.kapisoft.gjurd.excel;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import ma.kapisoft.gjurd.dao.PrevilegeDao;


import ma.kapisoft.gjurd.entities.Previlege;


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
public class PrevilegeExcel extends ManipExcel<Previlege> {
	private static final Log log = LogFactory.getLog(PrevilegeExcel.class);
	
	private static final int ATTRIBUTE_UNIQUE_NUM=0;
	

	
	
private static final String ATTRIBUTE_ID="id";
private static final String ATTRIBUTE_NOM="nom";
private static final String ATTRIBUTE_DESCRIPTION="description";

	

	
	@Autowired
	private PrevilegeDao dao;
	
    

	
	public PrevilegeDao getDao() {
		return dao;
	}

	public void setDao(PrevilegeDao dao) {
		this.dao = dao;
	}
	
	 

	


	public PrevilegeExcel() {
		super(Previlege.class);
	
	}


    

	
		
	
	@Override
	protected boolean readCell(XSSFCell cell, String attribute, Previlege t,
			List<MessageExcel> msgs, int rownum) {
		boolean error=true;
		if(attribute.equals(ATTRIBUTE_ID))
		{
			error=readNumberNotNull(cell, attribute, t, msgs, rownum);
		}
		
		else if(attribute.equals(ATTRIBUTE_NOM))
		{
			error=readNumberNull(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_DESCRIPTION))
		{
			error=readNumberNull(cell, attribute, t, msgs, rownum);
		}


		return error;
		
	}
	
	

	@Override
	protected Previlege rechercher(XSSFRow row) {
		// TODO Auto-generated method stub
		return dao.findByNom(row.getCell(ATTRIBUTE_UNIQUE_NUM).getStringCellValue());
	}

	

	

}
