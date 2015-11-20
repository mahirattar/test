/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.kapisoft.gjurd.test.jasper;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import ma.kapisoft.gjurd.entities.Utilisateur;
import ma.kapisoft.gjurd.service.UtilisateurService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;



/**
 *
 * @author ramki
 */
@ManagedBean
@SessionScoped
public class DemoBean implements Serializable{
    private List<Utilisateur> listOfUser=new ArrayList<Utilisateur>();

    @ManagedProperty(value="#{utilisateurService}")
	UtilisateurService	utilisateurService;

    
    public UtilisateurService getUtilisateurService() {
		return utilisateurService;
	}
	public void setUtilisateurService(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	public List<Utilisateur> getListOfUser() {
		if(utilisateurService!=null)
        listOfUser=utilisateurService.list();
        return listOfUser;
    }

    public void setListOfUser(List<Utilisateur> listOfUser) {
        this.listOfUser = listOfUser;
    }
    JasperPrint jasperPrint;
    public void init() throws JRException{
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(listOfUser);
       // jasperPrint=JasperFillManager.fillReport("C:\\Users\\khalid\\Desktop\\reports\\report.jasper", new HashMap(),beanCollectionDataSource);
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance()
        		.getExternalContext().getContext();
       jasperPrint=JasperFillManager.fillReport(sc.getRealPath("/")+"/WEB-INF/reports/report.jasper", new HashMap(),beanCollectionDataSource);
       //  jasperPrint=JasperFillManager.fillReport(sc.getRealPath("reports2/report.jasper"), new HashMap(),beanCollectionDataSource);
 
    }
    
   public String PDF() throws JRException, IOException{
       init();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRPdfExporter jrpdf=new JRPdfExporter();
         jrpdf.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       jrpdf.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
     //  jrpdf.setParameter(JRPdfExporter.OUTPUT_STREAM, servletOutputStream);
       jrpdf.exportReport();
       	FacesContext.getCurrentInstance().responseComplete();
		return "a.pdf";
    //   JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
   }
    public void DOCX(ActionEvent actionEvent) throws JRException, IOException{
        init();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.docx");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRDocxExporter docxExporter=new JRDocxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
   }
     public void XLSX(ActionEvent actionEvent) throws JRException, IOException{
        init();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.xlsx");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRXlsxExporter docxExporter=new JRXlsxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
   }
      public void ODT(ActionEvent actionEvent) throws JRException, IOException{
       init();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.odt");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JROdtExporter docxExporter=new JROdtExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
   }
       public void PPT(ActionEvent actionEvent) throws JRException, IOException{
       init();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pptx");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRPptxExporter docxExporter=new JRPptxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
   }
    
}
