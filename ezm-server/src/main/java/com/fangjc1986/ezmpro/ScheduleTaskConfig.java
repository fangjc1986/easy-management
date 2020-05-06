package com.fangjc1986.ezmpro;

import com.fangjc1986.ezmpro.sys.service.impl.AccountLogServiceImpl;
import com.fangjc1986.ezmpro.sys.service.impl.RequestLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Configuration
@EnableScheduling
public class ScheduleTaskConfig {

    @Autowired
    private AccountLogServiceImpl accountLogService;
    @Autowired
    private RequestLogServiceImpl requestLogService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteAccountLog() {
        accountLogService.delete(
            new Query().addCriteria(
                Criteria.where("create_time").lt(LocalDateTime.now().minusMonths(6L))
            )
        );
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteRequestLog() {
        requestLogService.delete(
            new Query().addCriteria(
                Criteria.where("create_time").lt(LocalDateTime.now().minusMonths(6L))
            )
        );
    }
}
