package ma.kapisoft.gjurd.service;

import ma.kapisoft.gjurd.entities.CompteUtilisateur;
import ma.kapisoft.gjurd.entities.Utilisateur;
import ma.kapisoft.gjurd.exception.GenericException;
/**
interface service de CompteUtilisateur
*/
public interface ICompteUtilisateurService extends IService<CompteUtilisateur> {

	CompteUtilisateur getCompteUtilisateur(String login);

	void editPassword(CompteUtilisateur selected)  throws GenericException;

	void editPasswordCurrentUser(CompteUtilisateur selected,String ancienpassword, String newpassword) throws GenericException;

}
