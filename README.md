# :pencil: mqtt_subscribe

## Description
mqtt messageQ를 이용한 구독 프로세스

## Environment
+ Spring Boot
+ Maven
+ JDK 1.8
+ mariaDB
+ mybatis

## flow
<img width="600" alt="스크린샷 2021-05-20 오전 2 20 59" src="https://user-images.githubusercontent.com/75176643/118861646-b3641880-b917-11eb-96f3-cb288537bb04.png">

## programming
import org.eclipse.paho.client.mqttv3.*  
MqttCallback interface 장착  
MqttClient client : 연결되는 클라이언트 객체  

### Connection
```
public boolean connect(String url, String clientId) {
        if(client==null){
        
        .....중략
        
        client = new org.eclipse.paho.client.mqttv3.MqttClient(url, clientId);
        client.setCallback(this);
        client.connect(options);
        
```

### Subscribe
정의한 listener와 채널을 MqttClient.subscribe 함수로 구독 설정
```
public void setSubscription(String channel, IMqttMessageListener listener) throws MqttException {
        this.client.subscribe(channel, listener);
        
    }
```


