package com.project.mqttSubscribe.Mappers.fourth;

import com.project.mqttSubscribe.Values;

@org.apache.ibatis.annotations.Mapper
public interface Mapper4 {

    String test();
    int insertData(Values values);
}
