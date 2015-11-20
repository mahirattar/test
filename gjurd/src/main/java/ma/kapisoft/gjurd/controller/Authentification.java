package ma.kapisoft.gjurd.controller;




import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;













import ma.kapisoft.gjurd.service.ICompteUtilisateurService;
import ma.kapisoft.gjurd.service.INotificationService;
import ma.kapisoft.gjurd.dao.CompteUtilisateurDao;
import ma.kapisoft.gjurd.entities.CompteUtilisateur;
import ma.kapisoft.gjurd.entities.Notification;
import ma.kapisoft.gjurd.entities.Utilisateur;
import ma.kapisoft.gjurd.exception.GenericException;

/**
 *
 * @author khalid
 */
@ManagedBean
@SessionScoped
public class Authentification {
	private static final Log log = LogFactory.getLog(Authentification.class);
     
	CompteUtilisateur user;
	List<Notification> notifications=null;
	
	
	
	@ManagedProperty(value="#{compteUtilisateurService}")
    private ICompteUtilisateurService service;

	@ManagedProperty(value="#{notificationService}")
    private INotificationService notificationService;

	
	
	public ICompteUtilisateurService getService() {
		return service;
	}
	public void setService(ICompteUtilisateurService service) {
		this.service = service;
	}
	
	public INotificationService getNotificationService() {
		return notificationService;
	}
	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}
	public CompteUtilisateur getUser() {
		if(!isAuthenticated())
			user=null;
		else
		{
			if(user==null)
			{
				user=service.getCompteUtilisateur(getLogin());
			
			}
				
		}
		return user;
	}
	public void setUser(CompteUtilisateur user) {
		this.user = user;
	}
	
	
	
	
	public List<Notification> getNotifications() {
		if(notifications==null)
		{
		     if(getUser()!=null)
		     {
		    	 try {
					notifications=notificationService.getNotifications(user);
				} catch (GenericException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		     }
		}
		return notifications;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	public String logout()
	{
		return "/j_spring_security_logout?faces-redirect=true";
	}
	public Authentification() {
		
        
    }
    public boolean isAuthenticated()
        {
    	try{
    	 Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
              return auth.isAuthenticated() && !"anonymousUser".equals(auth.getName());
    	   }catch(Exception e)
    	   {
    		   return false;
    	   }
        }
    public String getLogin()
    {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public boolean havePrivilege(String privilege)
    {
    	log.trace("havePrivilege "+privilege);
    	privilege="ROLE_"+privilege;
    	 try{
    		 String previlegeWRITE=privilege;
    	    	if(privilege.endsWith("DELETE"))
    	    	{
    	    		previlegeWRITE=previlegeWRITE.replace("DELETE", "WRITE");
    	    	}
    	    	else if(privilege.endsWith("ADD"))
    	    	{
    	    		previlegeWRITE=previlegeWRITE.replace("ADD", "WRITE");
    	    	}
    	    	else if(privilege.endsWith("EDIT"))
    	    	{
    	    		previlegeWRITE=previlegeWRITE.replace("EDIT", "WRITE");
    	    	}
       	  Collection<GrantedAuthority> y=  (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
             for(GrantedAuthority g:y)
             {
           	  if(g.getAuthority().equals(privilege) ||g.getAuthority().equals(previlegeWRITE))
                 {
               	   return true;
                 }
             }
                return false;
       }catch(Exception e)
   	{
    	   log.error("havePrivilege "+privilege+": "+e.getMessage());
       	return false;
   	}
    }
	public boolean haveNotification(String string) {
		// TODO Auto-generated method stub
		if(getNotifications()==null)
			return false;
		for(Notification notif:getNotifications())
		{
			if(notif.getMessage().equals(string))
				return true;
		}
		return false;
	}
	
	public void initNotification()
	{
		notifications=null;
	}
      
 
}
