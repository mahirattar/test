package ma.kapisoft.gjurd.service;

import java.util.List;

import ma.kapisoft.gjurd.entities.CompteUtilisateur;
import ma.kapisoft.gjurd.entities.Notification;
import ma.kapisoft.gjurd.exception.GenericException;

public interface INotificationService {

	
	List<Notification> getNotifications(CompteUtilisateur user) throws GenericException;
	
}
