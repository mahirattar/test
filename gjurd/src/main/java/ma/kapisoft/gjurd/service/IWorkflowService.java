package ma.kapisoft.gjurd.service;

import org.primefaces.model.diagram.DefaultDiagramModel;

import ma.kapisoft.gjurd.entities.Action;
import ma.kapisoft.gjurd.entities.Condition;
import ma.kapisoft.gjurd.entities.Delimiteur;
import ma.kapisoft.gjurd.entities.Workflow;
/**
interface service de Workflow
*/
public interface IWorkflowService extends IService<Workflow> {

	
	public void createAction(Action etape,DefaultDiagramModel model);
	public void toDiagram(Workflow w,DefaultDiagramModel model);
	public void createEtatInitiale(Delimiteur etape,DefaultDiagramModel model);
	public void createEtatFinale(Delimiteur etape,DefaultDiagramModel model);
	 public void createCondition(Condition etape,DefaultDiagramModel model);
	
}
