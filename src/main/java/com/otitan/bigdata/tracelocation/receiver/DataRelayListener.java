package com.otitan.bigdata.tracelocation.receiver;

import cn.hutool.json.JSONUtil;
import com.otitan.bigdata.tracelocation.dto.GpsTrace;
import com.otitan.bigdata.tracelocation.service.ws.BroadcastHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Slf4j
@Component
@RabbitListener(queues = "fanout.dataRelay")
public class DataRelayListener {

    @Autowired
    private BroadcastHandler broadcastHandler;

    @RabbitHandler
    public void process(Map dataMap) {
        GpsTrace gpsTrace = (GpsTrace)dataMap.get("traceData");
        String jsonStr = JSONUtil.toJsonStr(gpsTrace);
        log.info("实时转发GPS数据：{}", jsonStr);

        broadcastHandler.broadcast(jsonStr);
    }


}
