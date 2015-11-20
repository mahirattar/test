package ma.kapisoft.gjurd.controller;


import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;

import ma.kapisoft.gjurd.service.ConsultationService;
import ma.kapisoft.gjurd.service.IConsultationService;
import ma.kapisoft.gjurd.util.JsfUtil;
import ma.kapisoft.gjurd.entities.Consultation;


@ManagedBean
@SessionScoped
public class ConsultationController extends AbstractController<Consultation> {

	private static final Log log = LogFactory.getLog(ConsultationController.class);
	   
	
	@ManagedProperty(value="#{consultationService}")
    private IConsultationService service;
	
    public ConsultationController() {
        super(Consultation.class);
    }

    @PostConstruct
    public void init() {   
    }

	public IConsultationService getService() {
		return service;
	}

	public void setService(IConsultationService service) {
		this.service = service;
	}
	
	public void create(ActionEvent event) {
    	log.trace("create Consultation");
    		super.create(event);    
    		//
    }
	
	
}