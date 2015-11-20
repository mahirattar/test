package ma.kapisoft.gjurd.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.kapisoft.gjurd.entities.CompteUtilisateur;
import ma.kapisoft.gjurd.entities.Notification;
import ma.kapisoft.gjurd.exception.GenericException;
import ma.kapisoft.gjurd.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class NotificationService implements INotificationService {

	private static final Log log = LogFactory.getLog(NotificationService.class);
	
	private static final int NBR_DAYS_EXPIRE_PASS_NOTIF=7;
	private static final int NBR_DAYS_EXPIRE_PASS=90;
	
	private static final String WARNING="WARNING";
	private static final String ERROR="ERROR";
	
	private static final String NotificationPasswordExpiration="NotificationPasswordExpiration";
	private static final String NotificationPasswordExpired="NotificationPasswordExpired";
	
	
	public NotificationService() {
		// TODO Auto-generated constructor stub
	}

	public List<Notification> getNotifications(CompteUtilisateur user) throws GenericException
	{
		List<Notification> liste=new ArrayList<Notification>();
		notificationsPassword(liste, user);
		return liste;
	}
	
	private void notificationsPassword(List<Notification> liste,CompteUtilisateur user)
	{
		try{
			int nbrdays=DateUtil.nbrDays(new Date(), user.getDatepassword());
			if(nbrdays >(NBR_DAYS_EXPIRE_PASS-NBR_DAYS_EXPIRE_PASS_NOTIF))
			{
				Notification notif=new Notification();
				notif.setType(WARNING);
				if(nbrdays>NBR_DAYS_EXPIRE_PASS)
					notif.setMessage(NotificationPasswordExpired);
				else
				{
					notif.setMessage(NotificationPasswordExpiration);
					Object[] objs={nbrdays};
					notif.setParemetres(objs);
				}
				
				
				liste.add(notif);
			}
		}catch(Exception e)
		{
			log.error(e);
		}
	}

}
