package com.otitan.bigdata.tracelocation.receiver;

import com.otitan.bigdata.tracelocation.dto.GpsTrace;
import com.otitan.bigdata.tracelocation.entity.TraceData;
import com.otitan.bigdata.tracelocation.repository.TraceDataRepository;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RabbitListener(queues = "fanout.dataStore")
public class DataStoreListener {
    @Autowired
    private TraceDataRepository traceDataRepository;

    @RabbitHandler
    public void process(Map dataMap) {
        GpsTrace gpsTrace = (GpsTrace)dataMap.get("traceData");
        String traceId = (String)dataMap.get("traceId");
        String createTime = (String)dataMap.get("createTime");

        TraceData traceData = new TraceData();
        traceData.setId(traceId);
        traceData.setDeviceId(gpsTrace.getDeviceId());
        traceData.setLatitude(gpsTrace.getLatitude());
        traceData.setLongitude(gpsTrace.getLongitude());
        traceData.setAccuracy(gpsTrace.getAccuracy());
        traceData.setAltitude(gpsTrace.getAltitude());
        traceData.setBearing(gpsTrace.getBearing());
        traceData.setSpeed(gpsTrace.getSpeed());
        traceData.setTime(gpsTrace.getTime());
        traceData.setCreateTime(createTime);

        traceDataRepository.save(traceData);
    }

}
