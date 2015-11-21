package ma.kapisoft.gjurd.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;












import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.control.CompilationUnit.SourceUnitOperation;
import org.primefaces.component.diagram.DiagramRenderer;
import org.primefaces.context.RequestContext;
import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.event.diagram.ConnectionChangeEvent;
import org.primefaces.event.diagram.DisconnectEvent;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;
import org.primefaces.model.diagram.overlay.ArrowOverlay;

import javax.annotation.PostConstruct;

import ma.kapisoft.gjurd.service.EtapeElement;
import ma.kapisoft.gjurd.service.WorkflowService;
import ma.kapisoft.gjurd.service.IWorkflowService;
import ma.kapisoft.gjurd.util.JsfUtil;
import ma.kapisoft.gjurd.entities.Action;
import ma.kapisoft.gjurd.entities.Condition;
import ma.kapisoft.gjurd.entities.Delimiteur;
import ma.kapisoft.gjurd.entities.Etape;
import ma.kapisoft.gjurd.entities.Workflow;
import ma.kapisoft.gjurd.exception.GenericException;


@ManagedBean
@SessionScoped
public class WorkflowController extends AbstractController<Workflow> {

	private static final Log log = LogFactory.getLog(WorkflowController.class);
	 
	private static final String EGAL = "=";
	private static final String DIFFERENT="<>"; 
	private static final String INFERIEUR="<"; 
	private static final String SUPERIEUR=">"; 
	private static final String INFERIEUR_EGAL="<="; 
	private static final String SUPERIEUR_EGAL=">="; 
	
	private static final String ATTRIBUTE_ETAT="ETAT"; 
	
	private static final String ATTRIBUTE_TYPE_STRING="STRING";
	private static final String ATTRIBUTE_TYPE_DATE="DATE";
	private static final String ATTRIBUTE_TYPE_VALUEATTRIBUTE="VALUEATTRIBUTE";
	
	private static final int ELEMENT_X_INIT=4;
	private static final int ELEMENT_Y_INIT=8;
	
	
	
	
	
	private DefaultDiagramModel model;
	
	private Condition condition;
	private Action action;
	private Delimiteur delimiteur;
	private Date datevaleur;
	private String stringvaleur;
	private String attributevaleur;
	
	
	
	List<String> operatorsState=null;
	List<String> operatorsDate=null;
	
	   
	
	@ManagedProperty(value="#{workflowService}")
    private IWorkflowService service;
	
    public WorkflowController() {
        super(Workflow.class);
    }
    
    

    public DefaultDiagramModel getModel() {
    	if(model==null)
    		initDiagram();
		return model;
	}




	public void setModel(DefaultDiagramModel model) {
		this.model = model;
	}


	public IWorkflowService getService() {
		return service;
	}

	public void setService(IWorkflowService service) {
		this.service = service;
	}


	public Condition getCondition() {
		return condition;
	}




	public void setCondition(Condition condition) {
		this.condition = condition;
	}




	public Action getAction() {
		return action;
	}




	public void setAction(Action action) {
		this.action = action;
	}


	

	public Delimiteur getDelimiteur() {
		return delimiteur;
	}




	public void setDelimiteur(Delimiteur delimiteur) {
		this.delimiteur = delimiteur;
	}




	public Date getDatevaleur() {
		return datevaleur;
	}




	public void setDatevaleur(Date datevaleur) {
		this.datevaleur = datevaleur;
	}




	public String getStringvaleur() {
		return stringvaleur;
	}




	public void setStringvaleur(String stringvaleur) {
		this.stringvaleur = stringvaleur;
	}




	public String getAttributevaleur() {
		return attributevaleur;
	}




	public void setAttributevaleur(String attributevaleur) {
		this.attributevaleur = attributevaleur;
	}




	public List<String> getOperatorsState() {
		if(operatorsState==null)
		{
			operatorsState=new ArrayList<String>();
			operatorsState.add(EGAL);
			operatorsState.add(DIFFERENT);
			
		}
		return operatorsState;
	}




	public void setOperatorsState(List<String> operatorsState) {
		this.operatorsState = operatorsState;
	}




	public List<String> getOperatorsDate() {
		if(operatorsDate==null)
		{
			operatorsDate=new ArrayList<String>();
			operatorsDate.add(EGAL);
			operatorsDate.add(DIFFERENT);
			operatorsDate.add(INFERIEUR);
			operatorsDate.add(INFERIEUR_EGAL);
			operatorsDate.add(SUPERIEUR);
			operatorsDate.add(SUPERIEUR_EGAL);
			
		}
		return operatorsDate;
	}




	public void setOperatorsDate(List<String> operatorsDate) {
		this.operatorsDate = operatorsDate;
	}
	
	
	public List<String> getOperators() {
		try{
			if(!condition.getAttribut().equals(ATTRIBUTE_ETAT))
			{
				return getOperatorsDate();
			}else
			{
				return getOperatorsState();
			}
		}catch(Exception e)
		{
			return getOperatorsState();
		}
	}

    public void save()
    {
    	try{
    		service.save(this.getSelected(),model);
    		
    	}catch(GenericException e)
    	{
    		
    	}
    }


	@PostConstruct
    public void init() {
      
   
    }
	
	public void changeAttribute()
	{
		if(condition !=null)
		{
			if(condition.getAttribut().equals(ATTRIBUTE_ETAT))
			{
				condition.setTypevaleur(ATTRIBUTE_TYPE_STRING);
			}
			else
			{
				condition.setTypevaleur(ATTRIBUTE_TYPE_DATE);
			}
		}
	}
	
	  
	public void deleteEtape(ActionEvent event) {
		System.out.println("deleteEtape");
	}
	
	public void prepareCreateAction(ActionEvent event) {
	    	action=new Action();
		  
	    }
	  
	  public void prepareCreateCondition(ActionEvent event) {
	    	condition=new Condition();
	    	condition.setAttribut(ATTRIBUTE_ETAT);
	    	condition.setTypevaleur("STRING");
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
	  
	  public void createAction() {
	    	log.trace("create Action");
	    	action.setX(ELEMENT_X_INIT);
	    	action.setY(ELEMENT_Y_INIT);
	    	
	    	service.createAction(action, model);
	            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ActionCreateSucess"));
	         }
	  
	  public void createCondition() {
	    	log.trace("create Condition");
	    
	    	condition.setX(ELEMENT_X_INIT);
	    	condition.setY(ELEMENT_Y_INIT);
	    	
	    	if(condition.getTypevaleur().equals(ATTRIBUTE_TYPE_STRING))
	    	{
	    		condition.setValeur(stringvaleur);
	    	}else if(condition.getTypevaleur().equals(ATTRIBUTE_TYPE_DATE))
	    	{
	    		condition.setValeur(datevaleur.toString());
	    	}
	    	else if(condition.getTypevaleur().equals(ATTRIBUTE_TYPE_VALUEATTRIBUTE))
	    	{
	    		condition.setValeur(attributevaleur);
	    	}
	    			
	    	
	    	service.createCondition(condition, model);
	    	
	            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ConditionCreateSucess"));
	    }
	  
	  public void createEtapeFin() {
	    	log.trace("create Delimiteur");
	    	delimiteur=new Delimiteur();
	    	delimiteur.setFin(true);
	    	delimiteur.setX(ELEMENT_X_INIT);
	    	delimiteur.setY(ELEMENT_Y_INIT);
	        service.createEtatFinale(delimiteur, model);
	          
	        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DelimiteurCreateSucess"));
	        
	  }
	
	  
	public String viewDiagram()
	{
		model=null;
		return "diagram";
	}
	  
	private void initDiagram()
	{
		  model = new DefaultDiagramModel();
	      service.toDiagram(getSelected(), model);
		  
		  /* 
		  model.setMaxConnections(-1);
	              
	        model.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
	        StraightConnector connector = new StraightConnector();
	        connector.setPaintStyle("{strokeStyle:'#98AFC7', lineWidth:3}");
	        connector.setHoverPaintStyle("{strokeStyle:'#5C738B'}");
	        model.setDefaultConnector(connector);
	        
	        
	        Element element = new Element(new EtapeElement("", "etatinitiale.png"), "34em", "5em");	
	        EndPoint endPointCA = createRectangleEndPoint(EndPointAnchor.BOTTOM);
	          endPointCA.setSource(true);
	        
	          element.addEndPoint(endPointCA);
	          element.setStyleClass("classetatinitiale");
	          model.addElement(element);
	          
	         
	     
	        Element ceo = new Element("CEO", "25em", "6em");
	        ceo.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));
	        model.addElement(ceo);
	         
	        //CFO
	        Element cfo = new Element("CFO", "10em", "18em");
	        cfo.addEndPoint(createEndPoint(EndPointAnchor.TOP));
	        cfo.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));
	         
	        Element fin = new Element("FIN", "5em", "30em");
	        fin.addEndPoint(createEndPoint(EndPointAnchor.TOP));
	         
	        Element pur = new Element("PUR", "20em", "30em");
	        pur.addEndPoint(createEndPoint(EndPointAnchor.TOP));
	         
	        model.addElement(cfo);
	        model.addElement(fin);
	        model.addElement(pur);
	         
	        //CTO
	        Element cto = new Element("CTO", "40em", "18em");
	        cto.addEndPoint(createEndPoint(EndPointAnchor.TOP));
	        cto.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));
	         
	        Element dev = new Element("DEV", "35em", "30em");
	        dev.addEndPoint(createEndPoint(EndPointAnchor.TOP));
	         
	        Element tst = new Element("TST", "50em", "30em");
	        tst.addEndPoint(createEndPoint(EndPointAnchor.TOP));
	         
	        model.addElement(cto);
	        model.addElement(dev);
	        model.addElement(tst);
	         
	                              
	        //connections
	        model.connect(new Connection(ceo.getEndPoints().get(0), cfo.getEndPoints().get(0), connector));        
	        model.connect(new Connection(ceo.getEndPoints().get(0), cto.getEndPoints().get(0), connector));
	        model.connect(new Connection(cfo.getEndPoints().get(1), fin.getEndPoints().get(0), connector));
	        model.connect(new Connection(cfo.getEndPoints().get(1), pur.getEndPoints().get(0), connector));
	        model.connect(new Connection(cto.getEndPoints().get(1), dev.getEndPoints().get(0), connector));
	        model.connect(new Connection(cto.getEndPoints().get(1), tst.getEndPoints().get(0), connector));
			*/
	}

	private EndPoint createEndPoint(EndPointAnchor anchor) {
        DotEndPoint endPoint = new DotEndPoint(anchor);
        endPoint.setStyle("{fillStyle:'#404a4e'}");
        endPoint.setHoverStyle("{fillStyle:'#20282b'}");
        endPoint.setMaxConnections(1);
        return endPoint;
    }
	
	public void onNodeMove(ActionEvent param) {
	      Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	      String id = params.get("node_id");
	      String x = params.get("node_x");
	      String y = params.get("node_y");
	      int pos = id.indexOf("-"); // Remove Client ID part
	      if (pos != -1) {
	         id = id.substring(pos + 1);
	      }
	      Element element = model.findElement(id);
	      if (element != null) {
	         element.setX(x);
	         element.setY(y);
	      } else {
	         log.error("Didn't find element for ID " + id);
	      }
	   }
	
	
	
	 public void onConnect(ConnectEvent event) {
	      
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Connected", 
	                    "From " + event.getSourceElement().getData()+ " To " + event.getTargetElement().getData());
	     
	            EtapeElement source=(EtapeElement) event.getSourceElement().getData();
	            EtapeElement target=(EtapeElement) event.getTargetElement().getData();
	            
				Etape et=source.getEtape();
				if(et instanceof Delimiteur && !((Delimiteur) et).getFin())
				{
					((Delimiteur)et).setEtape(target.getEtape());
				}else if(et instanceof Action)
				{
					((Action)et).setEtape(target.getEtape());
				}else if(et instanceof Condition)
				{
					Condition condition=(Condition)et;
					if(event.getSourceEndPoint().getAnchor().equals(EndPointAnchor.RIGHT))
					{
						condition.setEtape1(target.getEtape());
					}
					else{
						condition.setEtape2(target.getEtape());
					}
				}
	        //   model.disconnect(findConnection(event.getSourceEndPoint(), event.getTargetEndPoint()));
	         //  DiagramRenderer dr=new DiagramRenderer();
	            
	            prevetConnection();  
	           
	    }
	 
	 public void prevetConnection()
	 {
		 
	 }
	    
	 
	 private Connection findConnection(EndPoint source, EndPoint target) {
	        Connection connection = null;
	        
	        if(model != null) {
	            List<Connection> connections = model.getConnections();
	            if(connections != null) {
	                int connectionsSize = connections.size();
	                for(int i = 0; i < connectionsSize; i++) {
	                    Connection conn = connections.get(i);

	                    if(conn.getSource().equals(source) && conn.getTarget().equals(target)) {
	                        connection = conn;
	                        break;
	                    }
	                }
	            }
	        }
	        
	        return connection;
	    }
	 
	 
	    public void onDisconnect(DisconnectEvent event) {
	        EtapeElement source=(EtapeElement) event.getSourceElement().getData();
            EtapeElement target=(EtapeElement) event.getTargetElement().getData();
            
			Etape et=source.getEtape();
			if(et instanceof Delimiteur && !((Delimiteur) et).getFin())
			{
				((Delimiteur)et).setEtape(null);
			}else if(et instanceof Action)
			{
				((Action)et).setEtape(null);
			}else if(et instanceof Condition)
			{
				Condition condition=(Condition)et;
				if(event.getSourceEndPoint().getAnchor().equals(EndPointAnchor.RIGHT))
				{
					condition.setEtape1(null);
				}
				else{
					condition.setEtape2(null);
				}
			}
	    }
	     
	    public void onConnectionChange(ConnectionChangeEvent event) {
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Connection Changed", 
	                    "Original Source:" + event.getOriginalSourceElement().getData() + 
	                    ", New Source: " + event.getNewSourceElement().getData() + 
	                    ", Original Target: " + event.getOriginalTargetElement().getData() + 
	                    ", New Target: " + event.getNewTargetElement().getData());
	         
	      if(event.getNewSourceEndPoint().equals(event.getOriginalSourceEndPoint()))
	      {
	    	  EtapeElement source=(EtapeElement) event.getNewSourceElement().getData();
	            EtapeElement target=(EtapeElement) event.getNewTargetElement().getData();
	            
				Etape et=source.getEtape();
				if(et instanceof Delimiteur && !((Delimiteur) et).getFin())
				{
					((Delimiteur)et).setEtape(target.getEtape());
				}else if(et instanceof Action)
				{
					((Action)et).setEtape(target.getEtape());
				}else if(et instanceof Condition)
				{
					Condition condition=(Condition)et;
					if(event.getNewSourceEndPoint().getAnchor().equals(EndPointAnchor.RIGHT))
					{
						condition.setEtape1(target.getEtape());
					}
					else{
						condition.setEtape2(target.getEtape());
					}
				}
	      }else
	      {
	    	  EtapeElement source=(EtapeElement) event.getNewSourceElement().getData();
	    	  EtapeElement source2=(EtapeElement) event.getOriginalSourceElement().getData();
	            EtapeElement target=(EtapeElement) event.getNewTargetElement().getData();
	            
	            Etape et=source2.getEtape();
				if(et instanceof Delimiteur && !((Delimiteur) et).getFin())
				{
					((Delimiteur)et).setEtape(null);
				}else if(et instanceof Action)
				{
					((Action)et).setEtape(null);
				}else if(et instanceof Condition)
				{
					Condition condition=(Condition)et;
					if(event.getOriginalSourceEndPoint().getAnchor().equals(EndPointAnchor.RIGHT))
					{
						condition.setEtape1(null);
					}
					else{
						condition.setEtape2(null);
					}
				}
	            
	            
	            
	            
	            
	            
				et=source.getEtape();
				if(et instanceof Delimiteur && !((Delimiteur) et).getFin())
				{
					((Delimiteur)et).setEtape(target.getEtape());
				}else if(et instanceof Action)
				{
					((Action)et).setEtape(target.getEtape());
				}else if(et instanceof Condition)
				{
					Condition condition=(Condition)et;
					if(event.getNewSourceEndPoint().getAnchor().equals(EndPointAnchor.RIGHT))
					{
						condition.setEtape1(target.getEtape());
					}
					else{
						condition.setEtape2(target.getEtape());
					}
				}
	      }
	      
	    }
	
	
	

	
}