package tghtechnology.chozaazul.Utils.Security.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tghtechnology.chozaazul.Utils.Security.Models.UserLog;

@Repository
public interface UserLogRepository extends CrudRepository<UserLog, String>{}
