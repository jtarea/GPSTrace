package com.otitan.bigdata.tracelocation.web;

import cn.hutool.core.util.StrUtil;
import com.otitan.bigdata.tracelocation.dto.GpsTrace;
import com.otitan.bigdata.tracelocation.entity.TraceData;
import com.otitan.bigdata.tracelocation.repository.TraceDataRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class TraceDataController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TraceDataRepository traceDataRepository;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;


    @PostMapping("/gpsTrace/input")
    public ResponseEntity<?> incomingTraceDate(@RequestBody GpsTrace gpsTrace) {
        String traceId = StrUtil.uuid();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, Object> map = new HashMap<>();
        map.put("traceId", traceId);
        map.put("traceData", gpsTrace);
        map.put("createTime", createTime);

        rabbitTemplate.convertAndSend("traceDataFanout", null, map);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/gpsTrace/search")
    public ResponseEntity<?> searchTraceDate(@RequestParam String deviceId, @RequestParam long startTime, @RequestParam long endTime) {

        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.rangeQuery("time").gte(startTime).lte(endTime))
                .must(QueryBuilders.termQuery("deviceId", deviceId));

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(qb)
                .build();

        List<TraceData> traceDataList = elasticsearchTemplate.queryForList(searchQuery, TraceData.class);

        return new ResponseEntity<>(traceDataList, HttpStatus.OK);
    }

    @DeleteMapping("/gpsTrace/del/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        traceDataRepository.deleteById(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping("/gpsTrace/delAll")
    public ResponseEntity<?> deleteAll() {
        traceDataRepository.deleteAll();

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/gpsTrace/delByDevice")
    public ResponseEntity<?> deleteByDevice(@RequestParam String deviceId) {

        CriteriaQuery cq = new CriteriaQuery(new Criteria("deviceId").is(deviceId));

        elasticsearchTemplate.delete(cq, TraceData.class);

        return new ResponseEntity<>(deviceId, HttpStatus.OK);
    }

}
