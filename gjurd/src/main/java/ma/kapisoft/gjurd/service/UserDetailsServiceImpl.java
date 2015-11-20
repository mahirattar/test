package ma.kapisoft.gjurd.service;



import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.kapisoft.gjurd.dao.CompteUtilisateurDao;
import ma.kapisoft.gjurd.entities.CompteUtilisateur;
import ma.kapisoft.gjurd.entities.Previlege;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService{
	private static final Log log = LogFactory.getLog(CompteUtilisateurService.class);
	
	@Autowired
	private CompteUtilisateurDao dao;

	public CompteUtilisateurDao getDao() {
		return dao;
	}

	public void setDao(CompteUtilisateurDao dao) {
		this.dao = dao;
	}
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {
			log.debug("loadUserByUsername "+login);
		// TODO Auto-generated method stub
		    boolean enabled = true;  
	        boolean accountNonExpired = true;  
	        boolean credentialsNonExpired = true;  
	        boolean accountNonLocked = true;  
	        CompteUtilisateur u=null;
	        		try{
	        			u =dao.findByLogin(login);}
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        	log.error("loadUserByUsername "+e.getMessage());
	        }
	        if(u==null)
	        {
	        	return null;
	        }
	        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        if(u.getProfile()!=null)
	        {
	        for(Previlege r:u.getProfile().getPrevileges())
	           authorities.add((GrantedAuthority)(new SimpleGrantedAuthority("ROLE_"+r.getNom())));
	        }
		return new User(
				u.getLogin(),
				u.getPassword(),
				u.getActif(),
				accountNonExpired,
				credentialsNonExpired,
				accountNonLocked,
				authorities
				);
	}

	
}
