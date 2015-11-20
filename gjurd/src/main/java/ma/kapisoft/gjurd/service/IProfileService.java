package ma.kapisoft.gjurd.service;


import java.util.List;

import ma.kapisoft.gjurd.entities.Previlege;
import ma.kapisoft.gjurd.entities.Profile;
/**
interface service de Profile
*/
public interface IProfileService extends IService<Profile> {

	List<Previlege> listPrevileges();

	Previlege findPrevilege(Integer key);

}
