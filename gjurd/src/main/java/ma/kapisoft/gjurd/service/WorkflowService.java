package ma.kapisoft.gjurd.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.component.diagram.Diagram;
import org.primefaces.model.diagram.Connection;
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
	
	
	private static final int X_ETAT_INITIALE=421;
	private static final int Y_ETAT_INITIALE=16;

	private static final String DIAGRAM_UNITE="px";
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
		 	 entity.setValide(false);
		 	 Delimiteur d=createEtatInitiale(entity);
		 	 Set<Etape> etapes=new HashSet<Etape>();
		 	 etapes.add(d);
		 	 entity.setEtapes(etapes);
		 	 
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
	        Map<Etape,Element> map=new HashMap<Etape,Element>();
	        for(Etape e:w.getEtapes())
	        {
	        	if(e instanceof Action)
	        	{
	        		createAction((Action)e, model,map);
	        	}else if(e instanceof Condition)
	        	{
	        		createCondition((Condition)e, model,map);
	        	}else if(e instanceof Delimiteur)
	        	{
	        		Delimiteur d=(Delimiteur)e;
	        		if(d.getFin())
	        		{
	        			createEtatFinale(d, model,map);
	        		}else
	        		{
	        			createEtatInitiale(d, model,map);
	        		}
	        		
	        	}
	        	
	        }
	        for(Etape e:w.getEtapes())
	        {
	        	if(e instanceof Action && ((Action) e).getEtape()!=null)
	        	{
	        		 model.connect(new Connection(map.get(e).getEndPoints().get(0),map.get(((Action) e).getEtape()).getEndPoints().get(0 )));        
	      	       
	        	}else if(e instanceof Condition)
	        	{
	        		Condition c=(Condition)e;
	        		if(c.getEtape1()!=null)
	        		{
	        			 model.connect(new Connection(map.get(e).getEndPoints().get(1),map.get(c.getEtape1()).getEndPoints().get(0 )));
	        		}
	        		
	        		if(c.getEtape2()!=null)
	        		{
	        			model.connect(new Connection(map.get(e).getEndPoints().get(2),map.get(c.getEtape2()).getEndPoints().get(0 )));
	        		}
	        	}else if(e instanceof Delimiteur &&!((Delimiteur) e).getFin())
	        	{
	        		
	        		 model.connect(new Connection(map.get(e).getEndPoints().get(0),map.get(((Delimiteur) e).getEtape()).getEndPoints().get(0 )));        	  
	        	}
	        }
	          
	        
	        
	        
	   	    
	 }
	 public void createAction(Action etape,DefaultDiagramModel model)
	 {
		 createAction(etape, model, null);
	 }
	 public void createAction(Action etape,DefaultDiagramModel model,Map<Etape,Element> map)
	 {
		 EtapeElement etapeElement=new EtapeElement();
		 etapeElement.setEtape(etape);
		 etapeElement.setHaveimage(false);
		 etapeElement.setName(etape.getNom());
		 
		 Element element = new Element(etapeElement, etape.getX()+DIAGRAM_UNITE, etape.getY()+DIAGRAM_UNITE);	
		 EndPoint endPointSA = createDotEndPoint(EndPointAnchor.TOP);
         endPointSA.setTarget(true);
         element.addEndPoint(endPointSA);
		 
		 
		 EndPoint endPointCA = createRectangleEndPoint(EndPointAnchor.BOTTOM);
         endPointCA.setSource(true);
         element.addEndPoint(endPointCA);
         
        
         element.setStyleClass("classaction");
         model.addElement(element);
         if(map!=null)
        	 map.put(etape, element);
   	
	 }
	 
	 public void createEtatInitiale(Delimiteur etape,DefaultDiagramModel model)
	 {
		 createEtatInitiale(etape, model, null);
	 }
	 
	 public void createEtatInitiale(Delimiteur etape,DefaultDiagramModel model,Map<Etape,Element> map)
	 {
		 EtapeElement etapeElement=new EtapeElement();
		 etapeElement.setEtape(etape);
		 etapeElement.setHaveimage(true);
		 etapeElement.setImage("etatinitiale.png");
		 etapeElement.setName("");
		 
		   
		 Element element = new Element(etapeElement, etape.getX()+DIAGRAM_UNITE, etape.getY()+DIAGRAM_UNITE);	
	   	  	    EndPoint endPointCA = createRectangleEndPoint(EndPointAnchor.BOTTOM);
	          endPointCA.setSource(true);
	        
	          element.addEndPoint(endPointCA);
	         
	          model.addElement(element);
	       
	          if(map!=null)
	         	 map.put(etape, element);
		 
		 
        
	 }
	 
	 public void createEtatFinale(Delimiteur etape,DefaultDiagramModel model)
	 {
		 createEtatFinale(etape,model,null);
	 }
	 
	 public void createEtatFinale(Delimiteur etape,DefaultDiagramModel model,Map<Etape,Element> map)
	 {
		 EtapeElement etapeElement=new EtapeElement();
		 etapeElement.setEtape(etape);
		 etapeElement.setHaveimage(true);
		 etapeElement.setImage("etatfinale.png");
		 etapeElement.setName("");
		 
		   
		 Element element = new Element(etapeElement, etape.getX()+DIAGRAM_UNITE, etape.getY()+DIAGRAM_UNITE);	
		 EndPoint endPointSA = createDotEndPoint(EndPointAnchor.TOP);
         endPointSA.setTarget(true);
         element.addEndPoint(endPointSA);

        
         element.setStyleClass("classetatfinale");
         model.addElement(element);
	         
		 
         if(map!=null)
         	 map.put(etape, element);
		 
		 
        
	 }
	 public void createCondition(Condition etape,DefaultDiagramModel model)
	 {
		 createCondition(etape, model, null);
	 }
	 public void createCondition(Condition etape,DefaultDiagramModel model,Map<Etape,Element> map)
	 {
		 EtapeElement etapeElement=new EtapeElement();
		 etapeElement.setEtape(etape);
		 etapeElement.setHaveimage(true);
		 etapeElement.setImage("condition.png");
		 etapeElement.setName("");
		 
		 Element element = new Element(etapeElement, etape.getX()+DIAGRAM_UNITE, etape.getY()+DIAGRAM_UNITE);	
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
    	  
    	  if(map!=null)
	         	 map.put(etape, element);

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
	 @Transactional(readOnly = false,rollbackFor=GenericException.class)
	public void save(Workflow selected, DefaultDiagramModel model)
			throws GenericException {
		// TODO Auto-generated method stub
		Set<Etape> set=new HashSet<Etape>();
		for(Element e:model.getElements())
		{
			EtapeElement etelement=(EtapeElement) e.getData();
			Etape et=etelement.getEtape();
			int x=Integer.parseInt(e.getX().substring(0,e.getX().length()-2));
			int y=Integer.parseInt(e.getY().substring(0,e.getY().length()-2));
			log.error(e.getX()+"    "+e.getY());
			et.setX(x);
			et.setY(y);
			et.setWorkflow(selected);
			/*List<EndPoint> endpoints =	e.getEndPoints();
			model.get
			if(et instanceof Delimiteur && !((Delimiteur) et).getFin())
			{
				for(EndPoint ep:endpoints)
			    {
			    	if(ep.isSource() && ep.getAnchor().equals(EndPointAnchor.BOTTOM))
			    	{
			    		//ep.get
			    	}
			    }
			}else if(et instanceof Action)
			{
				
			}else if(et instanceof Condition)
			{
				
			}*/
		    set.add(et);
			
		}
		selected.setEtapes(set);
		dao.modify(selected);
		
	}


	
}
