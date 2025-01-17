package top.naccl.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.naccl.entity.LoginLog;

import java.util.List;

@Service
public interface LoginLogService {
	List<LoginLog> getLoginLogListByDate(String startDate, String endDate);

	@Async
	void saveLoginLog(LoginLog log);

	void deleteLoginLogById(Long id);
}
