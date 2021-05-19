package com.project.mqttSubscribe;


import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@RestController
public class ServiceController {
    private static final Logger logger =
            LoggerFactory.getLogger(ServiceController.class);
    private MqttService mqttService;
    public ServiceController(MqttService mqttService){
        this.mqttService = mqttService;
    }

    private ClientMqtt clientMqtt;

    private final String topic = "mqtt/#";
    private final String url = "tcp://localhost:18833";

    private final String _DATA1 = "_DATA1";
    private final String _DATA2 = "_DATA2";

    private final String DATA1 = "DATA1";
    private final String DATA2 = "DATA2";

    private final String NUMBER1 = "1";
    private final String NUMBER2 = "2";
    private final String NUMBER3 = "3";
    private final String NUMBER4 = "4";
    private final String NUMBER5 = "5";

    public void createNqtt(){
        if(clientMqtt!=null){
            logger.debug("clientMqtt already exist");
            return;
        }
        clientMqtt = new ClientMqtt();
    }
    private IMqttMessageListener listener = (topic, msg) -> {
        /* msg process */
        byte[] payload = msg.getPayload();
        String datas = bArr2String(payload);
        logger.debug("Message received: topic={}, payload={}", topic, datas);
        String[] values = new String(datas).split(",");
        float[] fValues = new float[values.length];
        if(fValues.length!=2){
            return;
        }
        for(int i=0; i<fValues.length; i++){
            fValues[i] = Float.parseFloat(values[i]);
        }

        /* topic process */
        String[] topics = new String(topic).split("/");
        String dbParam = topics[1]; //db명
        String recycleType = topics[2];
        Timestamp ts = localDateTimeToTimeStamp(LocalDateTime.now());

        /* db process */
        Values data1Values = new Values(recycleType + _DATA1, DATA1, fValues[0], ts);
        Values data2Values = new Values(recycleType + _DATA2, DATA2, fValues[1], ts);
        logger.debug(data1Values.toString());
        logger.debug(data2Values.toString());

        boolean result = InsertDB(dbParam, data1Values, data2Values);
        logger.info("input result : " + result);

    };
    public boolean connectMqttt() {
        createNqtt();
        if(clientMqtt==null){
            throw new NullPointerException("");
        }
        if(clientMqtt.isConnected()){
            logger.debug("already connection");
            return true;
        }
        boolean conn =  clientMqtt.connect(url, "sub");
        setPubParam();
        return conn;
    }
    public void setPubParam(){
        clientMqtt.setTopic(topic);
        clientMqtt.setListener(listener);
    }

    @GetMapping("/mqtt")
    public void startSubscribe() throws MqttException, InterruptedException {
        long startTime = System.currentTimeMillis();
        while(!connectMqttt()){
            Thread.sleep(1000);
            long endTime = System.currentTimeMillis();
            long waitTime = endTime-startTime;
            if(waitTime>5000) break;
        }
        try {
            clientMqtt.setSubscription(topic, listener);


        } catch (Exception e) {
            logger.error("/subscrive error : " + e.getMessage());
        }

//        try {
//            Thread.sleep(5000);
//        } catch(InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }
    }

    public boolean InsertDB(String dbParam, Values data1, Values data2){
        boolean result = false;
        if (dbParam.contains(NUMBER1)) {
            result = mqttService.insertData1(data1, data2);
        } else if (dbParam.contains(NUMBER2)) {
            result = mqttService.insertData2(data1, data2);
        } else if (dbParam.contains(NUMBER3)) {
            result = mqttService.insertData3(data1, data2);
        } else if (dbParam.contains(NUMBER4)) {
            result = mqttService.insertData4(data1, data2);
        } else if (dbParam.contains(NUMBER5)) {
            result = mqttService.insertData5(data1, data2);
        }
        return result;
    }
    public static String getCurrentTime(String timeFormat){
        return new SimpleDateFormat(timeFormat).format(System.currentTimeMillis());
    }

    public static String bArr2String(byte[] bytes){
        String result = "";
        for(int i=0; i<bytes.length; i++){
            result +=(char)bytes[i];
        }
        return result;
    }

    public static Timestamp localDateTimeToTimeStamp(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt); // 2021-02-18 01:06:55.323
    }

//    public static float[] bArr2fArr(byte[] buffer) throws IOException {
//        ByteArrayInputStream bas = new ByteArrayInputStream(buffer);
//        DataInputStream ds = new DataInputStream(bas);
//        float[] fArr = new float[buffer.length / 4];  // 4 bytes per float
//        for (int i = 0; i < fArr.length; i++)
//        {
//            fArr[i] = ds.readFloat();
//        }
//        return fArr;
//    }

}
