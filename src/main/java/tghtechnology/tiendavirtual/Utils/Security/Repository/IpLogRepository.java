package tghtechnology.tiendavirtual.Utils.Security.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tghtechnology.tiendavirtual.Utils.Security.Models.IpLog;

@Repository
public interface IpLogRepository extends CrudRepository<IpLog, String>{
	
	
}
