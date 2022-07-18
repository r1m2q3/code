package com.honest.enterprise.sequence.idsequence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * id 生成服务
 * date:2021-04-01 09:49
 * author:fanjie
 */

@Service
@ConfigurationProperties(prefix = "id-service")
public class IdService {

    @Value("${id-service.dataCenterId}")
    private String dataCenterId;
    @Value("${id-service.clock}")
    private String clock;
    /**
     * 获取下一个ID
     * @return
     */
    public long nextId(){
        long dataCenterIdValue=dataCenterId==null?1L:Long.valueOf(dataCenterId);
        boolean clockValue=clock==null?false:Boolean.valueOf(clock);
        //毫秒内随机起始值开始
        IdSequenceCore sequence = new IdSequenceCore(dataCenterIdValue ,clockValue, true);
        return sequence.nextId();
    }
}
