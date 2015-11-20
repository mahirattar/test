package ma.kapisoft.gjurd.controller;




import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ma.kapisoft.gjurd.exception.GenericException;
import ma.kapisoft.gjurd.service.AbstractService;
import ma.kapisoft.gjurd.service.IService;
import ma.kapisoft.gjurd.util.JsfUtil;


/**
 * Represents an abstract shell of to be used as JSF Controller to be used in
 * AJAX-enabled applications. No outcomes will be generated from its methods
 * since handling is designed to be done inside one page.
 */
public abstract class AbstractController<T> implements Serializable{

	private static final Log log = LogFactory.getLog(AbstractController.class);
	
    private Class<T> itemClass;
    private T selected;
    private List<T> items;

    public AbstractController() {
    	log.trace("AbstractController Constructeur");
    }

    
    
    public abstract IService<T> getService();



	

	public AbstractController(Class<T> itemClass) {
        this.itemClass = itemClass;
    }

 

   
	public T getSelected() {
		log.trace("getSelected "+itemClass.getSimpleName());
    	  return selected;
    }

    public void setSelected(T selected) {
    	log.trace("setSelected "+itemClass.getSimpleName());
    	this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        // Nothing to do if entity does not have any embeddable key.
    }

    ;

    protected void initializeEmbeddableKey() {
        // Nothing to do if entity does not have any embeddable key.
    }

    /**
     * Returns all items in a List object
     *
     * @return
     */
    public List<T> getItems() {
    	log.trace("getItems "+itemClass.getSimpleName());
    	if (items == null) {
            items = this.getService().list();
        }
        return items;
    }

    public void edit(ActionEvent event) {
    	log.trace("edit "+itemClass.getSimpleName());
    	try{ 
	    	if (selected != null) {
	            this.setEmbeddableKeys();
	            this.getService().edit(selected);
	            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString(itemClass.getSimpleName()+"EditSucess"));
	            initList(); // Invalidate list of items to trigger re-query.

	    	 }
       }
       catch(GenericException ge)
        {
         	  JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(ge.getMessage()));
        }	
    	catch(org.springframework.dao.DataIntegrityViolationException  me)
	    {
	     	 messageEditIntegriteException(me);
	    }
    	catch(Exception e)
       {
    	   log.error(null, e);
    	   JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(itemClass.getSimpleName()+"EditError"));
       }
    	
    }

    public void create(ActionEvent event) {
    	log.trace("create "+itemClass.getSimpleName());
    	try{
    		getService().add(selected);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString(itemClass.getSimpleName()+"CreateSucess"));
            initList(); // Invalidate list of items to trigger re-query.
			
		}
    	catch(GenericException ge)
        {
         	  JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(ge.getMessage()));
        }
    	catch(Exception e)
		{
			log.error(null, e);
			JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(itemClass.getSimpleName()+"CreateError"));
		}
    	
       
    }

    public void delete(ActionEvent event) {
    	log.trace("delete "+itemClass.getSimpleName());
        if (selected != null) {
    
        	try{
        		 this.getService().remove(selected);
        		 selected = null; // Remove selection
                  initList(); // Invalidate list of items to trigger re-query.
            
               JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString(itemClass.getSimpleName()+"DeleteSucess"));
           	
   		}
        catch(GenericException ge)
        {
        	  JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(ge.getMessage()));
        }
		catch(org.springframework.dao.DataIntegrityViolationException  me)
	    {
	     	 messageDeleteIntegriteException(me);
	    }	
        catch(Exception e)
   		{
   			log.error(null, e);
   			JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(itemClass.getSimpleName()+"DeleteError"));	
   		}
        	
        	
      }
    }
    
    protected void messageDeleteIntegriteException(org.springframework.dao.DataIntegrityViolationException  me)
    {
    	 JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(itemClass.getSimpleName()+"DeleteIntegriteError"));
    }
    
    protected void messageEditIntegriteException(org.springframework.dao.DataIntegrityViolationException  me)
    {
    	 JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString(itemClass.getSimpleName()+"UniqueError"));
    }

    /**
     * Creates a new instance of an underlying entity and assigns it to Selected
     * property.
     *
     * @return
     */
    public T prepareCreate(ActionEvent event) {
    	log.trace("prepareCreate "+itemClass.getSimpleName());
        T newItem;
        try {
            newItem = itemClass.newInstance();
            this.selected = newItem;
            initializeEmbeddableKey();
            return newItem;
        } catch (InstantiationException ex) {
           log.error(null, ex);
        } catch (IllegalAccessException ex) {
           log.error(null, ex);
        }
        return null;
    }
    
    public void initList()
    {
    	log.trace("initList "+itemClass.getSimpleName());
      	items =null;
    }

  
}
