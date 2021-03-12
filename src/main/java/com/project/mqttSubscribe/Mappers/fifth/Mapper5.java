package com.project.mqttSubscribe.Mappers.fifth;

import com.project.mqttSubscribe.Values;

@org.apache.ibatis.annotations.Mapper
public interface Mapper5 {

    String test();
    int insertData(Values values);
}
