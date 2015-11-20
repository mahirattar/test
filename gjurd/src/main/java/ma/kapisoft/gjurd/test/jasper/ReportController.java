package ma.kapisoft.gjurd.test.jasper;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import ma.kapisoft.gjurd.util.ReportManip;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;


@ManagedBean
@SessionScoped
public class ReportController {
	
	
	@ManagedProperty(value="#{reportService}")
	IReportService service;

	
	
	
	public IReportService getService() {
		return service;
	}




	public void setService(IReportService service) {
		this.service = service;
	}




	public ReportController() {
		// TODO Auto-generated constructor stub
	}
	
	
	  public void PPTX(ActionEvent actionEvent) throws JRException, IOException{
	  ReportManip.export("utilisateurs.pptx", service.exportReportComptesUtilisateurs(ReportManip.PPTX));     
	  }
	  
	  public void PDF(ActionEvent actionEvent) throws JRException, IOException{
		  ReportManip.export("utilisateurs.pdf", service.exportReportComptesUtilisateurs(ReportManip.PDF));     
		  }
	  
	  public void DOCX(ActionEvent actionEvent) throws JRException, IOException{
		  ReportManip.export("utilisateurs.docx", service.exportReportComptesUtilisateurs(ReportManip.DOCX));     
		  }
	  
	  public void ODT(ActionEvent actionEvent) throws JRException, IOException{
		  ReportManip.export("utilisateurs.odt", service.exportReportComptesUtilisateurs(ReportManip.ODT));     
		  }
	  public void XLSX(ActionEvent actionEvent) throws JRException, IOException{
		  ReportManip.export("utilisateurs.xlsx", service.exportReportComptesUtilisateurs(ReportManip.XLSX));     
		  }

}
