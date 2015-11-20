package ma.kapisoft.gjurd.test.jasper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.kapisoft.gjurd.dao.CompteUtilisateurDao;
import ma.kapisoft.gjurd.util.ReportManip;
import net.sf.jasperreports.engine.JRAbstractExporter;
@Service
@Transactional(readOnly = true)
public class ReportService implements IReportService {
	
	
	private final static String REPORT_USERS="report_users"; 
	private final static String TITLE_PARAM="TITLE"; 
	
	@Autowired
	private CompteUtilisateurDao dao;
	
	public CompteUtilisateurDao getDao() {
		return dao;
	}

	public void setDao(CompteUtilisateurDao dao) {
		this.dao = dao;
	}

	public ReportService() {
		// TODO Auto-generated constructor stub
	}

	public JRAbstractExporter exportReportComptesUtilisateurs(String type) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(TITLE_PARAM, "utilisateurs");
		
	ReportManip rmanip=new ReportManip(REPORT_USERS, map, dao.list());
		return rmanip.getExporter(type);
	}

	public JRAbstractExporter exportReportProfiles(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public JRAbstractExporter exportReportUtilisateurs(String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
