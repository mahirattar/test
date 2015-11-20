package ma.kapisoft.gjurd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ma.kapisoft.gjurd.dao.MessageDao;
import ma.kapisoft.gjurd.entities.Message;



/**
class service du Message
*/
@Service
public class MessageService extends AbstractService<Message> implements IMessageService {
private static final Log log = LogFactory.getLog(MessageService.class);	
	@Autowired
	private MessageDao dao;
	
	public MessageDao getDao() {
		return dao;
	}

	public void setDao(MessageDao dao) {
		this.dao = dao;
	}


	
}
