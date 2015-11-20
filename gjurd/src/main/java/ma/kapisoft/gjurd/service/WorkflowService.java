package ma.kapisoft.gjurd.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.primefaces.component.diagram.Diagram;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;


import ma.kapisoft.gjurd.dao.WorkflowDao;
import ma.kapisoft.gjurd.entities.Action;
import ma.kapisoft.gjurd.entities.Condition;
import ma.kapisoft.gjurd.entities.Delimiteur;
import ma.kapisoft.gjurd.entities.Etape;
import ma.kapisoft.gjurd.entities.Workflow;
import ma.kapisoft.gjurd.exception.GenericException;



/**
class service du Workflow
*/
@Service
public class WorkflowService extends AbstractService<Workflow> implements IWorkflowService {
	private static final Log log = LogFactory.getLog(WorkflowService.class);	
  
	private static final String NOM_UNIQUE_ERROR="WorkflowUniqueError";
	
	
	private static final int X_ETAT_INITIALE=34;
	private static final int Y_ETAT_INITIALE=5;

	@Autowired
	private WorkflowDao dao;
	
	public WorkflowDao getDao() {
		return dao;
	}

	public void setDao(WorkflowDao dao) {
		this.dao = dao;
	}
	
	
	 @Transactional(readOnly = false,rollbackFor=GenericException.class)
	    public void add(Workflow entity)  throws GenericException{
		 try{
		 	 entity.setEtat(Workflow.DRAFT);
		 	 Delimiteur d=createEtatInitiale(entity);
		 	 
			 getDao().add(entity);
		 }catch(ConstraintViolationException  me)
		 {
				log.warn(me.getMessage());
				throw new GenericException(NOM_UNIQUE_ERROR);
		 }
	    }
	 
	 
	 private Delimiteur createEtatInitiale(Workflow w)
	 {
		 Delimiteur d=new Delimiteur();
		 d.setFin(false);
		 d.setWorkflow(w);
		 d.setX(X_ETAT_INITIALE);
		 d.setY(Y_ETAT_INITIALE);
		 
		 return d;	 
		 
	 }
	 
	 
	   private EndPoint createDotEndPoint(EndPointAnchor anchor) {
	        DotEndPoint endPoint = new DotEndPoint(anchor);
	        endPoint.setMaxConnections(1);
	        endPoint.setScope("network");
	        endPoint.setTarget(true);
	        endPoint.setStyle("{fillStyle:'#98AFC7'}");
	        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
	         
	        return endPoint;
	    }
	     
	    private EndPoint createRectangleEndPoint(EndPointAnchor anchor) {
	        RectangleEndPoint endPoint = new RectangleEndPoint(anchor);
	        endPoint.setMaxConnections(1);
	        endPoint.setScope("network");
	        endPoint.setSource(true);
	        endPoint.setStyle("{fillStyle:'#98AFC7'}");
	        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
	         
	        return endPoint;
	    }
	 
	 
	 public void toDiagram(Workflow w,DefaultDiagramModel model)
	 {
		    model.setMaxConnections(-1);
	              
	        model.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
	        StraightConnector connector = new StraightConnector();
	        connector.setPaintStyle("{strokeStyle:'#98AFC7', lineWidth:3}");
	        connector.setHoverPaintStyle("{strokeStyle:'#5C738B'}");
	        model.setDefaultConnector(connector);
	        
	        for(Etape e:w.getEtapes())
	        {
	        	if(e instanceof Action)
	        	{
	        		createAction((Action)e, model);
	        	}else if(e instanceof Condition)
	        	{
	        		createCondition((Condition)e, model);
	        	}else if(e instanceof Delimiteur)
	        	{
	        		Delimiteur d=(Delimiteur)e;
	        		if(d.getFin())
	        		{
	        			createEtatFinale(d, model);
	        		}else
	        		{
	        			createEtatInitiale(d, model);
	        		}
	        		
	        	}
	        	
	        }
	        
	        
	        
	        
	   	    
	 }
	 
	 public void createAction(Action etape,DefaultDiagramModel model)
	 {
		 EtapeElement etapeElement=new EtapeElement();
		 etapeElement.setEtape(etape);
		 etapeElement.setHaveimage(false);
		 etapeElement.setName(etape.getNom());
		 
		 Element element = new Element(etapeElement, etape.getX()+"em", etape.getY()+"em");	
   	  	 EndPoint endPointCA = createRectangleEndPoint(EndPointAnchor.BOTTOM);
         endPointCA.setSource(true);
         element.addEndPoint(endPointCA);
         
         EndPoint endPointSA = createDotEndPoint(EndPointAnchor.TOP);
         endPointSA.setTarget(true);
         element.addEndPoint(endPointSA);
         element.setStyleClass("classaction");
         model.addElement(element);
   	
	 }
	 
	 public void createEtatInitiale(Delimiteur etape,DefaultDiagramModel model)
	 {
		 EtapeElement etapeElement=new EtapeElement();
		 etapeElement.setEtape(etape);
		 etapeElement.setHaveimage(true);
		 etapeElement.setImage("etatinitiale.png");
		 etapeElement.setName("");
		 
		   
		 Element element = new Element(etapeElement, etape.getX()+"em", etape.getY()+"em");	
	   	  	    EndPoint endPointCA = createRectangleEndPoint(EndPointAnchor.BOTTOM);
	          endPointCA.setSource(true);
	        
	          element.addEndPoint(endPointCA);
	         
		 
		 
		 
		 
        
	 }
	 public void createEtatFinale(Delimiteur etape,DefaultDiagramModel model)
	 {
		 EtapeElement etapeElement=new EtapeElement();
		 etapeElement.setEtape(etape);
		 etapeElement.setHaveimage(true);
		 etapeElement.setImage("etatfinale.png");
		 etapeElement.setName("");
		 
		   
		 Element element = new Element(etapeElement, etape.getX()+"em", etape.getY()+"em");	
		 EndPoint endPointSA = createDotEndPoint(EndPointAnchor.TOP);
         endPointSA.setTarget(true);
         element.addEndPoint(endPointSA);

        
         element.setStyleClass("classetatfinale");

	         
		 
		 
		 
		 
        
	 }
	 
	 public void createCondition(Condition etape,DefaultDiagramModel model)
	 {
		 EtapeElement etapeElement=new EtapeElement();
		 etapeElement.setEtape(etape);
		 etapeElement.setHaveimage(true);
		 etapeElement.setImage("condition.png");
		 etapeElement.setName("");
		 
		 Element element = new Element(etapeElement, etape.getX()+"em", etape.getY()+"em");	
    	 EndPoint endPointSA = createDotEndPoint(EndPointAnchor.TOP);
          endPointSA.setTarget(true);
          element.addEndPoint(endPointSA);


          
          EndPoint endPointCA = createRectangleEndPoint(EndPointAnchor.LEFT);
          endPointCA.setSource(true);
          element.addEndPoint(endPointCA);
          
           endPointCA = createRectangleEndPoint(EndPointAnchor.RIGHT);
          endPointCA.setSource(true);
          element.addEndPoint(endPointCA);
          element.setStyleClass("classcondition");
          
          
    	  model.addElement(element);

   	
	 }
	 private boolean valider(Workflow w)
	 {
		 Set<Etape> etapes=w.getEtapes();
		 Map<Etape,Boolean> map=new HashMap<Etape,Boolean>();
		 
		 for(Etape e:etapes)
		 {
			 if(map.get(e)==null)
			 {
				 if(!e.valide())
				 {
					return false;
				 }
				 else
				 {
					 if(e instanceof Action)
					 {
						 Etape e1=((Action)e).getEtape();
						 if(map.get(e1)==null)
						 {
							if(!e1.valide())
								return false;
							map.put(e1, true);
						 }
					 }else if(e instanceof Condition)
					 {
						 Etape e1=((Condition)e).getEtape1();
						 Etape e2=((Condition)e).getEtape2();
						 if(map.get(e1)==null)
						 {
							if(!e1.valide())
								return false;
							map.put(e1, true);
						 }
						 if(map.get(e2)==null)
						 {
							if(!e2.valide())
								return false;
							map.put(e2, true);
						 }
					 }
					 else if(e instanceof Delimiteur)
					 {
						 Delimiteur d=(Delimiteur)e;
						 if(!d.getFin())
						 {
							 Etape e1=((Delimiteur)e).getEtape();
							 if(map.get(e1)==null)
							 {
								if(!e1.valide())
									return false;
								map.put(e1, true);
							 }
						 }
					 }
				 }
				 map.put(e, true);
			 }
		 }
		 return true;
		 
	 }


	
}
