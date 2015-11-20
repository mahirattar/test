package ma.kapisoft.gjurd.controller;



import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;



import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;


import ma.kapisoft.gjurd.service.ICompteUtilisateurService;

import ma.kapisoft.gjurd.util.JsfUtil;
import ma.kapisoft.gjurd.entities.CompteUtilisateur;
import ma.kapisoft.gjurd.exception.GenericException;


@ManagedBean
@SessionScoped
public class CompteUtilisateurController extends AbstractController<CompteUtilisateur> {

	private static final Log log = LogFactory.getLog(CompteUtilisateurController.class);
	
	private static final String PassConfirmNotEquals=  "CompteUtilisateurPassConfirmNotEquals";
	private static final String EditPasswordSucess ="CompteUtilisateurEditPasswordSucess";
	private static final String EditError ="CompteUtilisateurEditError";
	
    private String confirm="";
    private String ancienpassword="";
    private String newpassword="";
	

	@ManagedProperty(value="#{compteUtilisateurService}")
    private ICompteUtilisateurService service;
	
	@ManagedProperty(value="#{authentification}")
    private Authentification authentification;
	
    public CompteUtilisateurController() {
        super(CompteUtilisateur.class);
    }

    @PostConstruct
    public void init() {
       
   
    }

	public ICompteUtilisateurService getService() {
		return service;
	}

	public void setService(ICompteUtilisateurService service) {
		this.service = service;
	}
	
	
	
	public Authentification getAuthentification() {
		return authentification;
	}

	public void setAuthentification(Authentification authentification) {
		this.authentification = authentification;
	}

	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
	
	
	public String getAncienpassword() {
		return ancienpassword;
	}

	public void setAncienpassword(String ancienpassword) {
		this.ancienpassword = ancienpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public void create(ActionEvent event) {
	    	log.trace("create CompteUtilisateur");
	    	if(getSelected().getPassword().equals(confirm))
	    	{
	    		super.create(event);      
	    	}
	    	else
	    	{
	    		JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(PassConfirmNotEquals));
		        
	    	}
	    	
	    }
	 public void editPassword(ActionEvent event)
		{
		 log.trace("editPassword");
			if(getSelected().getPassword().equals(confirm))
	        {
	           
			    try {
					service.editPassword(getSelected());
					JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString(EditPasswordSucess));
						
				}catch (GenericException e) {
					
					JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(e.getMessage()));
				}
			    catch (Exception e) {
					log.error(e.getMessage());
					JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(EditError));
				}
				
	        }else
	        {
	        	JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(PassConfirmNotEquals));
	        }
		}
	 
	 public void editPasswordCurrentUser(ActionEvent event)
		{
		 log.trace("editPassword");
			if(newpassword.equals(confirm))
	        {
	           
			    try {
					service.editPasswordCurrentUser(authentification.getUser(),ancienpassword,newpassword);
					JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString(EditPasswordSucess));
						
				}catch (GenericException e) {
					
					JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(e.getMessage()));
				}
			    catch (Exception e) {
					log.error(e.getMessage());
					JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(EditError));
				}
				
	        }else
	        {
	        	JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(PassConfirmNotEquals));
	        }
		}
	
	 public String editPasswordCurrentUser()
		{
		 log.trace("editPassword");
			if(newpassword.equals(confirm))
	        {
	           
			    try {
					service.editPasswordCurrentUser(authentification.getUser(),ancienpassword,newpassword);
					JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString(EditPasswordSucess));
					authentification.initNotification();
					return "index";	
				}catch (GenericException e) {
					
					JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(e.getMessage()));
				}
			    catch (Exception e) {
					log.error(e.getMessage());
					JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(EditError));
				}
				
	        }else
	        {
	        	JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(PassConfirmNotEquals));
	        }
			return null;
		}
	
}