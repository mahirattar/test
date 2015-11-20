package ma.kapisoft.gjurd.controller;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.annotation.PostConstruct;

import ma.kapisoft.gjurd.service.UtilisateurService;
import ma.kapisoft.gjurd.service.IUtilisateurService;
import ma.kapisoft.gjurd.entities.Utilisateur;


@ManagedBean
@SessionScoped
public class UtilisateurController extends AbstractController<Utilisateur> {

	private static final Log log = LogFactory.getLog(UtilisateurController.class);
	   
	
	@ManagedProperty(value="#{utilisateurService}")
    private IUtilisateurService service;
	
    public UtilisateurController() {
        super(Utilisateur.class);
    }

    @PostConstruct
    public void init() {
       
   
    }

	public IUtilisateurService getService() {
		return service;
	}

	public void setService(IUtilisateurService service) {
		this.service = service;
	}
	
	
}