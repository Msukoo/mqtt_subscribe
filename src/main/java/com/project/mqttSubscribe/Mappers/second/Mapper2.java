package com.project.mqttSubscribe.Mappers.second;

import com.project.mqttSubscribe.Values;

@org.apache.ibatis.annotations.Mapper
public interface Mapper2 {

    String test();
    int insertData(Values values);
}
