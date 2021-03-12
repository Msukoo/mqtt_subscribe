package com.project.mqttSubscribe.Mappers.third;

import com.project.mqttSubscribe.Values;

@org.apache.ibatis.annotations.Mapper
public interface Mapper3 {

    String test();
    int insertData(Values values);
}
