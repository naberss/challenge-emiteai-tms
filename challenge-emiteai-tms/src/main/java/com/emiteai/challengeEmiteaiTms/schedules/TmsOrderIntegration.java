package com.emiteai.challengeEmiteaiTms.schedules;

import com.emiteai.challengeEmiteaiTms.service.impl.TmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TmsOrderIntegration {


    @Autowired
    private TmsOrderService tmsOrderService;
    boolean teste;


    @Scheduled(cron = "0 */2 * * * *")
    public void TmsOrderScheduler(){

        if(this.teste){
         tmsOrderService.doProcess();
        }
    }

}
