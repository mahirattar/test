package ma.kapisoft.gjurd.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;




import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;

import ma.kapisoft.gjurd.service.ProfileService;
import ma.kapisoft.gjurd.service.IProfileService;
import ma.kapisoft.gjurd.util.JsfUtil;
import ma.kapisoft.gjurd.entities.Profile;
import ma.kapisoft.gjurd.entities.Previlege;
import ma.kapisoft.gjurd.exception.GenericException;


@ManagedBean
@SessionScoped
public class ProfileController extends AbstractController<Profile> {

	private static final Log log = LogFactory.getLog(ProfileController.class);
	 
	@ManagedProperty(value="#{applicationController}")
	private ApplicationController application;
	
	@ManagedProperty(value="#{profileService}")
    private IProfileService service;
	private DualListModel<Previlege> previleges=new DualListModel<Previlege>(new ArrayList<Previlege>(), new ArrayList<Previlege>());
	
	
    public ProfileController() {
        super(Profile.class);
    }

    @PostConstruct
    public void init() {
    
   
    }

    
    
	public ApplicationController getApplication() {
		return application;
	}

	public void setApplication(ApplicationController application) {
		this.application = application;
	}

	public IProfileService getService() {
		return service;
	}

	public void setService(IProfileService service) {
		this.service = service;
	}

	public DualListModel<Previlege> getPrevileges() {
		return previleges;
	}

	public void setPrevileges(DualListModel<Previlege> previleges) {
		this.previleges = previleges;
	}
	@Override
	public Profile prepareCreate(ActionEvent event) {
        List<Previlege> pSource = application.getPrevileges(); 
        List<Previlege> pTarget = new ArrayList<Previlege>();  
        previleges=new DualListModel<Previlege>(pSource, pTarget);
     return  super.prepareCreate(event);
       
    }
    public void prepareEdit(ActionEvent event) {
    	 List<Previlege> pSource = application.getPrevileges();
        
        List<Previlege> pTarget = getSelected().getPrevileges();
        pSource.removeAll(pTarget);
        previleges=new DualListModel<Previlege>(pSource, pTarget);
        
     
    }
    public void edit(ActionEvent event) {
    	this.getSelected().setPrevileges(previleges.getTarget());
    	super.edit(event);
    	
    }

    public void create(ActionEvent event) {
    	this.getSelected().setPrevileges(previleges.getTarget());
    	super.create(event);
    	
       
    }
	
}