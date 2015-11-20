package ma.kapisoft.gjurd.test.jasper;

import net.sf.jasperreports.engine.JRAbstractExporter;

public interface IReportService {

	
	public JRAbstractExporter exportReportComptesUtilisateurs(String type);
	public JRAbstractExporter exportReportProfiles(String type);
	public JRAbstractExporter exportReportUtilisateurs(String type);
	
	
}
