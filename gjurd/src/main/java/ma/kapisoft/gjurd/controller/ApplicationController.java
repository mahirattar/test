package ma.kapisoft.gjurd.controller;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import ma.kapisoft.gjurd.service.IProfileService;
import ma.kapisoft.gjurd.entities.Previlege;




@ManagedBean
@ApplicationScoped
public class ApplicationController {

	@ManagedProperty(value="#{profileService}")
	private IProfileService profileService;
	
	
	private List<Previlege> previleges=null;
	
	
	public ApplicationController() {
		// TODO Auto-generated constructor stub
	}


	public IProfileService getProfileService() {
		return profileService;
	}


	public void setProfileService(IProfileService profileService) {
		this.profileService = profileService;
	}


	public List<Previlege> getPrevileges() {
		if(previleges==null)
			previleges=profileService.listPrevileges();	
		return previleges;
	}


	public void setPrevileges(List<Previlege> previleges) {
		this.previleges = previleges;
	}
	
	
	

}
