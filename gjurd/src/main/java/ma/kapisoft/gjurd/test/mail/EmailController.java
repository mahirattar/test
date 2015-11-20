package ma.kapisoft.gjurd.test.mail;

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
public class EmailController {

	@ManagedProperty(value="#{emailService}")
	 IEmailService service;
	private String subject;
	private String msg;
	private String to;
	
	
	public EmailController() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public String getSubject() {
		return subject;
	}




	public void setSubject(String subject) {
		this.subject = subject;
	}




	public String getMsg() {
		return msg;
	}




	public void setMsg(String msg) {
		this.msg = msg;
	}




	public String getTo() {
		return to;
	}




	public void setTo(String to) {
		this.to = to;
	}




	public IEmailService getService() {
		return service;
	}




	public void setService(IEmailService service) {
		this.service = service;
	}


	
	
	public void envoyer()  {
       service.sendMail(to, subject, msg);
}

	

	


}
