package ma.kapisoft.gjurd.test.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.kapisoft.gjurd.dao.CompteUtilisateurDao;
import ma.kapisoft.gjurd.entities.CompteUtilisateur;
import ma.kapisoft.gjurd.excel.CompteUtilisateurExcel;
import ma.kapisoft.gjurd.excel.ManipExcel;
import ma.kapisoft.gjurd.excel.MessageExcel;
import ma.kapisoft.gjurd.exception.GenericException;
import ma.kapisoft.gjurd.service.CompteUtilisateurService;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExcelService implements IExcelService {

	@Autowired
	private CompteUtilisateurDao dao;
	
	@Autowired
	private CompteUtilisateurExcel excel;
	
	public CompteUtilisateurDao getDao() {
		return dao;
	}

	public void setDao(CompteUtilisateurDao dao) {
		this.dao = dao;
	}
	
	
	
	public CompteUtilisateurExcel getExcel() {
		return excel;
	}

	public void setExcel(CompteUtilisateurExcel excel) {
		this.excel = excel;
	}

	public ExcelService() {
		// TODO Auto-generated constructor stub
	}

	public XSSFWorkbook getExcelUsers() {
		// TODO Auto-generated method stub
		String[] attributes={"login","actif","profile"};
		List<String> atts=new ArrayList<String>();
		Map<String,String> map=new HashMap<String, String>();
		map.put("Profile", "libelle");
		map.put("Utilisateur","email");
		return excel.getWorkbook("Utilisateurs", dao.list(),map);
	}
	@Transactional(readOnly = false,rollbackFor=GenericException.class)
	public List<MessageExcel> importerExcel(InputStream input)
	{
		List<MessageExcel> msgs=new ArrayList<MessageExcel>();
		 try {
			XSSFWorkbook wb=new XSSFWorkbook(input);
			Map<String,String> map=new HashMap<String, String>();
			map.put("Profile", "libelle");
			map.put("Utilisateur","email");
			excel.importSheet(wb, "Utilisateurs", map, msgs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return msgs;
	}

}
