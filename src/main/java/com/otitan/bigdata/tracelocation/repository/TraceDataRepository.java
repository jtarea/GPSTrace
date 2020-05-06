package com.otitan.bigdata.tracelocation.repository;

import com.otitan.bigdata.tracelocation.entity.TraceData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface TraceDataRepository extends ElasticsearchRepository<TraceData, String> {

    List<TraceData> findByDeviceId(String deviceId);

}

