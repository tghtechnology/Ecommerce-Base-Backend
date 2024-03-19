package tghtechnology.tiendavirtual.Security.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tghtechnology.tiendavirtual.Security.Models.IpLog;

@Repository
public interface IpLogRepository extends CrudRepository<IpLog, String>{
	
	
}
