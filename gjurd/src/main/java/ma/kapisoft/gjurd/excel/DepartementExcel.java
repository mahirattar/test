package ma.kapisoft.gjurd.excel;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import ma.kapisoft.gjurd.dao.DepartementDao;


import ma.kapisoft.gjurd.dao.UtilisateurDao;
import ma.kapisoft.gjurd.dao.CabinetDao;
import ma.kapisoft.gjurd.entities.Departement;


import ma.kapisoft.gjurd.entities.Utilisateur;
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
public class DepartementExcel extends ManipExcel<Departement> {
	private static final Log log = LogFactory.getLog(DepartementExcel.class);
	
	private static final int ATTRIBUTE_UNIQUE_NUM=0;
	

	
	
private static final String ATTRIBUTE_ID="id";
private static final String ATTRIBUTE_NOM="nom";
private static final String ATTRIBUTE_DESCRIPTION="description";
private static final String ATTRIBUTE_RESPONSABLE="responsable";
private static final String ATTRIBUTE_CABINET="cabinet";

	

	
	@Autowired
	private DepartementDao dao;
	
    

@Autowired
private UtilisateurDao daoUtilisateur;

@Autowired
private CabinetDao daoCabinet;

	
	public DepartementDao getDao() {
		return dao;
	}

	public void setDao(DepartementDao dao) {
		this.dao = dao;
	}
	
	 


	public UtilisateurDao getDaoUtilisateur() {
	
		return daoUtilisateur;
	}

	public void setDaoUtilisateur(UtilisateurDao daoUtilisateur) {
		this.daoUtilisateur = daoUtilisateur;
	}

	public CabinetDao getDaoCabinet() {
	
		return daoCabinet;
	}

	public void setDaoCabinet(CabinetDao daoCabinet) {
		this.daoCabinet = daoCabinet;
	}
	


	public DepartementExcel() {
		super(Departement.class);
	
	}


    


	protected boolean readResponsableCell(XSSFCell cell, String attribute, Departement t,List<MessageExcel> msgs, int rownum)
	{		
		try{
			if(cell==null||cell.getStringCellValue().trim().equals(""))
			{
				t.setResponsable(null);
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
				t.setResponsable(p);
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

	protected boolean readCabinetCell(XSSFCell cell, String attribute, Departement t,List<MessageExcel> msgs, int rownum)
	{		
		try{
			if(cell==null||cell.getStringCellValue().trim().equals(""))
			{
				t.setCabinet(null);
			}else
			{
				Cabinet p=null;
				p=daoCabinet.findByNom(cell.getStringCellValue());
				if(p==null)
				{
					MessageExcel msg=new MessageExcel(rownum,ERROR_OBJECT_INEXISTANT);
					String[] params={StringUtil.lastStringAfterSeparator(Cabinet.class.getCanonicalName(), "."),getLabelAttribute(attribute)};
					msg.setParemetres(params);
					msgs.add(msg);
					return false;
				}
				t.setCabinet(p);
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
	protected boolean readCell(XSSFCell cell, String attribute, Departement t,
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

		else if(attribute.equals(ATTRIBUTE_RESPONSABLE))
		{
			error=readResponsableCell(cell, attribute, t, msgs, rownum);
		}

		else if(attribute.equals(ATTRIBUTE_CABINET))
		{
			error=readCabinetCell(cell, attribute, t, msgs, rownum);
		}


		return error;
		
	}
	
	

	@Override
	protected Departement rechercher(XSSFRow row) {
		// TODO Auto-generated method stub
		return dao.findByNom(row.getCell(ATTRIBUTE_UNIQUE_NUM).getStringCellValue());
	}

	

	

}
