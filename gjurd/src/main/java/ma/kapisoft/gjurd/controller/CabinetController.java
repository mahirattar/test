package ma.kapisoft.gjurd.controller;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.annotation.PostConstruct;

import ma.kapisoft.gjurd.service.CabinetService;
import ma.kapisoft.gjurd.service.ICabinetService;
import ma.kapisoft.gjurd.entities.Cabinet;


@ManagedBean
@SessionScoped
public class CabinetController extends AbstractController<Cabinet> {

	private static final Log log = LogFactory.getLog(CabinetController.class);
	   
	
	@ManagedProperty(value="#{cabinetService}")
    private ICabinetService service;
	
    public CabinetController() {
        super(Cabinet.class);
    }

    @PostConstruct
    public void init() {
      
   
    }

	public ICabinetService getService() {
		return service;
	}

	public void setService(ICabinetService service) {
		this.service = service;
	}
	
	
}