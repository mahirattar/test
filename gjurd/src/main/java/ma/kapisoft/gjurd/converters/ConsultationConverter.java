package ma.kapisoft.gjurd.converters;



import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ma.kapisoft.gjurd.service.ConsultationService;
import ma.kapisoft.gjurd.service.IConsultationService;
import ma.kapisoft.gjurd.util.JsfUtil;
import ma.kapisoft.gjurd.entities.Consultation;




/* converter de Consultation
 * PERMET DE RETOURNER UN Consultation à partir l'id 
 
*/


@ManagedBean
@RequestScoped
public class ConsultationConverter implements Converter {
	private static final Log log = LogFactory.getLog(ConsultationConverter.class);
	
	@ManagedProperty(value="#{consultationService}")
	 IConsultationService	service;
	
	


	public IConsultationService getService() {
		return service;
	}

	public void setService(IConsultationService service) {
		this.service = service;
	}

	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
          return null;
        }
         try{
         return this.service.find(getKey(value));
		 }catch(Exception e)
	     {
	     	return null;
	     }
      
    }

    java.lang.Integer getKey(String value) {
        java.lang.Integer key;
        key = Integer.valueOf(value);
        return key;
    }

    String getStringKey(java.lang.Integer value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        return sb.toString();
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof Consultation) {
            Consultation o = (Consultation) object;
            return getStringKey(o.getId());
        } else {
            log.error("object "+object+" is of type "+ object.getClass().getName()+"; expected type: "+Consultation.class.getName());
            return null;
        }
    }

}
