package com.project.mqttSubscribe;

import com.project.mqttSubscribe.Mappers.fifth.Mapper5;
import com.project.mqttSubscribe.Mappers.first.Mapper1;
import com.project.mqttSubscribe.Mappers.fourth.Mapper4;
import com.project.mqttSubscribe.Mappers.second.Mapper2;
import com.project.mqttSubscribe.Mappers.third.Mapper3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class mqttService  {
    @Autowired
    Mapper1 mapper1;
    @Autowired
    Mapper2 mapper2;
    @Autowired
    Mapper3 mapper3;
    @Autowired
    Mapper4 mapper4;
    @Autowired
    Mapper5 mapper5;

    public boolean insertData1(Values data1Values, Values data2Values){
        int result1 = mapper1.insertData(data1Values);
        int result2 = mapper1.insertData(data2Values);
        if(result1==1 && result2==1) return true;
        else return false;
    }
    public boolean insertData2(Values data1Values, Values data2Values){
        int result1 = mapper2.insertData(data1Values);
        int result2 = mapper2.insertData(data2Values);
        if(result1==1 && result2==1) return true;
        else return false;
    }
    public boolean insertData3(Values data1Values, Values data2Values){
        int result1 = mapper3.insertData(data1Values);
        int result2 = mapper3.insertData(data2Values);
        if(result1==1 && result2==1) return true;
        else return false;
    }
    public boolean insertData4(Values data1Values, Values data2Values){
        int result1 = mapper4.insertData(data1Values);
        int result2 = mapper4.insertData(data2Values);
        if(result1==1 && result2==1) return true;
        else return false;
    }
    public boolean insertData5(Values data1Values, Values data2Values){
        int result1 = mapper5.insertData(data1Values);
        int result2 = mapper5.insertData(data2Values);
        if(result1==1 && result2==1) return true;
        else return false;
    }
}
