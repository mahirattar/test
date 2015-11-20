package ma.kapisoft.gjurd.controller;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.annotation.PostConstruct;

import ma.kapisoft.gjurd.service.MessageService;
import ma.kapisoft.gjurd.service.IMessageService;
import ma.kapisoft.gjurd.entities.Message;


@ManagedBean
@SessionScoped
public class MessageController extends AbstractController<Message> {

	private static final Log log = LogFactory.getLog(MessageController.class);
	   
	
	@ManagedProperty(value="#{messageService}")
    private IMessageService service;
	
    public MessageController() {
        super(Message.class);
    }

    @PostConstruct
    public void init() {   
    }

	public IMessageService getService() {
		return service;
	}

	public void setService(IMessageService service) {
		this.service = service;
	}
	
	
}