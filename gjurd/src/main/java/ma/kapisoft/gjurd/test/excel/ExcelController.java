package ma.kapisoft.gjurd.test.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import ma.kapisoft.gjurd.excel.MessageExcel;
import ma.kapisoft.gjurd.util.JsfUtil;
import ma.kapisoft.gjurd.util.StringUtil;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;







@ManagedBean
@RequestScoped
public class ExcelController {

	@ManagedProperty(value="#{excelService}")
	 IExcelService service;
	
	
	public ExcelController() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public IExcelService getService() {
		return service;
	}




	public void setService(IExcelService service) {
		this.service = service;
	}


private UploadedFile file; 
	
	List<MessageExcel> msgs =new ArrayList<MessageExcel>(); 
	
	 
	


public List<MessageExcel> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<MessageExcel> msgs) {
		this.msgs = msgs;
	}

public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	
	public void importer() throws IOException {
        if(file!=null)
        {
         if(StringUtil.getFileExtension(file.getFileName()).equals("xlsx")||StringUtil.getFileExtension(file.getFileName()).equals("XLSX"))
         {
           InputStream input = file.getInputstream();
           msgs=service.importerExcel(input);
            
            if(this.msgs.size()==0)
            {
            	JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FileExcelImportSuccess"));
            }
            else
            {
            	 JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FileExcelImportErrors"));
            }
    
         }
         else
         {
          JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("FileXLSError"));
         }
        }
        else
        {
          JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("FileNull"));   
        }
}

	

	public void exporter() throws IOException, ServletException	
	{
		System.out.println("exporter");
		ServletOutputStream fileOut = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse)fc.getExternalContext().getResponse();
		response.setContentType("application/xlsx");
		response.setHeader("Content-Disposition", " inline; filename=application.xlsx");
		XSSFWorkbook workbook = service.getExcelUsers();
		
		
		
	
		fileOut = response.getOutputStream();
		workbook.write(fileOut);
		FacesContext.getCurrentInstance().responseComplete();
		return;
	         
	
}


}
