package ma.kapisoft.gjurd.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ManagedBean
@SessionScoped
public class ChangeLocale implements Serializable{
	private static final Log log = LogFactory.getLog(ChangeLocale.class);
  // la locale des pages
  private String locale="fr";
  
  public ChangeLocale() {
	log.trace(locale);  
  }
  
  public String setFrenchLocale(){
	  log.trace("setFrenchLocale" +" "+locale);  
    locale="fr";
    return null;
  }
  
  public String setEnglishLocale(){
	  log.trace("setEnglishLocale" +" "+locale);  
    locale="en";
    return null;
  }

  public String getLocale() {
	  log.trace("getLocale" +" "+locale);  
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }
  
  
}
