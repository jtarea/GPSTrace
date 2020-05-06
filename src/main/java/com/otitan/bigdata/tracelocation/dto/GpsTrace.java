package com.otitan.bigdata.tracelocation.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class GpsTrace implements Serializable {
    private String deviceId;

    private double latitude;

    private double longitude;

    private float accuracy;

    private float bearing;

    private float speed;

    private double altitude;

    private long time;

}
