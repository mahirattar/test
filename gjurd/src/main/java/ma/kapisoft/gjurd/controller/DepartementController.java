package ma.kapisoft.gjurd.controller;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.annotation.PostConstruct;

import ma.kapisoft.gjurd.service.DepartementService;
import ma.kapisoft.gjurd.service.IDepartementService;
import ma.kapisoft.gjurd.entities.Departement;


@ManagedBean
@SessionScoped
public class DepartementController extends AbstractController<Departement> {

	private static final Log log = LogFactory.getLog(DepartementController.class);
	   
	
	@ManagedProperty(value="#{departementService}")
    private IDepartementService service;
	
    public DepartementController() {
        super(Departement.class);
    }

    @PostConstruct
    public void init() {
      
   
    }

	public IDepartementService getService() {
		return service;
	}

	public void setService(IDepartementService service) {
		this.service = service;
	}
	
	
}