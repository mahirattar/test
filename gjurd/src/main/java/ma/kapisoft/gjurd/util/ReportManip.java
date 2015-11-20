package ma.kapisoft.gjurd.util;

import java.io.Serializable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ma.kapisoft.gjurd.controller.ProfileController;
import ma.kapisoft.gjurd.entities.Utilisateur;
import ma.kapisoft.gjurd.service.UtilisateurService;
import net.sf.jasperreports.engine.JRAbstractExporter;
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

public class ReportManip implements Serializable{
	private static final Log log = LogFactory.getLog(ReportManip.class);
	
	public final static String PDF="PDF";
	public final static String DOCX="DOCX";
	public final static String ODT="ODT";
	public final static String XLSX="XLSX";
	public final static String PPTX="PPTX";
	
    private final static String PATH_REPORTS="/WEB-INF/reports/";
    private final static String SUFFIXE_REPORTS=".jasper";
    private String report="";
    private JasperPrint jasperPrint=null;
    Map<String,Object> parameterMap = new HashMap<String,Object>();
    private List datasource;
    
    
    
    
    public ReportManip(String report, Map<String, Object> parameterMap,
			List datasource) {
		super();
		this.report = report;
		this.parameterMap = parameterMap;
		this.datasource = datasource;
	}

	public ReportManip() {
		super();
	}
    
    //getters et setters
    
     

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public List getDatasource() {
		return datasource;
	}

	public void setDatasource(List datasource) {
		this.datasource = datasource;
	}

	public JasperPrint getJasperPrint() throws JRException{

    	 if(jasperPrint==null)  
    		 jasperPrint=JasperFillManager.fillReport(getPATH(), parameterMap,getDataSource());
    	 return jasperPrint;
     
    }
     
     protected JRBeanCollectionDataSource getDataSource()
     {
         JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(datasource); 
         return beanCollectionDataSource;
     }
     
     protected String getPATH()
     {
    	 ServletContext sc = (ServletContext) FacesContext.getCurrentInstance()
         		.getExternalContext().getContext();
    	 return sc.getRealPath("/")+PATH_REPORTS+report+""+SUFFIXE_REPORTS;
     }
     
     
     public JRAbstractExporter getExporter(String type)
     {
    	 JRAbstractExporter exporter = null;
    	 if(type.equals(PDF))
    	 {
    		 exporter=new JRPdfExporter();
    	 }else if(type.equals(DOCX))
    	 {
    		 exporter=new JRDocxExporter();
    	 }else if(type.equals(XLSX))
    	 {
    		 exporter=new JRXlsxExporter();
    	 }
    	 else if(type.equals(PPTX))
    	 {
    		 exporter=new JRPptxExporter();
    	 }
    	 else if(type.equals(ODT))
    	 {
    		 exporter=new JROdtExporter();
    	 }
    	 
    	 try {
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrint());
		} catch (JRException e) {
			log.error(e);
			return null;
		}catch (Exception e) {
			log.error(e);
 			return null;
 		}
    	 return exporter;
     }
     
     
     public static void export(String name,JRAbstractExporter exporter) throws JRException, IOException
     {
    	   
         HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename="+name);
         ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
         exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
         exporter.exportReport();
         FacesContext.getCurrentInstance().responseComplete();

     }
     
     
   
     
     

}