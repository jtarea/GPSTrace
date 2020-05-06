package com.otitan.bigdata.tracelocation.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "tracedata", type = "gpsTrace", shards = 3, replicas = 1)
public class TraceData {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String deviceId;

    @Field(type = FieldType.Double)
    private double latitude;

    @Field(type = FieldType.Double)
    private double longitude;

    @Field(type = FieldType.Float)
    private float accuracy;

    @Field(type = FieldType.Float)
    private float bearing;

    @Field(type = FieldType.Float)
    private float speed;

    @Field(type = FieldType.Double)
    private double altitude;

    @Field(type = FieldType.Long)
    private long time;

    @Field(index = false, type = FieldType.Keyword)
    private String createTime;

}
