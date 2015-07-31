package org.troy.biz.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.troy.biz.service.AttentionRecordService;
import org.troy.common.utils.DateUtil;

/**
 * 资金代扣业务的定时任务
 * 
 * @author troy
 * @version $Id: BizCoreTask.java, v 0.1 2014年6月26日 下午8:41:28 troy Exp $
 */
@Component
public class BizCoreTask {

    private static final Logger    logger = Logger.getLogger(BizCoreTask.class);

    @Autowired
    private AttentionRecordService attentionRecordService;

    @Scheduled(cron = "${task.CopyEchoBerthDataTask}")
    public void execute() throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("开始执行代扣的定时任务！");
        }
        Date startDate = DateUtil.getNowDate();
        attentionRecordService.execDeduct();
        Date endDate = DateUtil.getNowDate();
        if (logger.isInfoEnabled()) {
            logger.info("结束执行代扣的定时任务！耗时：[" + (endDate.getTime() - startDate.getTime()) + " ms]");
        }
    }

}
