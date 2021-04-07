package com.zhongcai.common.utils;

import android.content.Context;

import com.zhongcai.common.ui.application.CommonApp;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class MQTTHelper {

    private static final String SERVER_URI = "服务地址";
    private static  String CLIENT_ID = "2df8aabfb8b6085638953664f413a446bbc";//"ID唯一标识";

    private static final String USER_NAME = "用户名";
    private static final String PASSWORD = "密码";

    private boolean isConnected;
    private MqttAndroidClient mClient;


    private static MQTTHelper inst;

    public MQTTHelper instance(){
        if(null == inst)
            inst = new MQTTHelper();
        return inst;
    }

    public MQTTHelper() {
        CLIENT_ID = MqttClient.generateClientId();
        this.mClient = new MqttAndroidClient(CommonApp.app, SERVER_URI, CLIENT_ID);
        setCallback();
    }

    int count = 0;

    private void setCallback(){
        if(null != mClient){
            mClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    if (count < 5) {
                        count++;//5次重连
                        mClient.close();
                        doConnect();
                    }
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
//                    Log.d(TAG,"接收消息=="+new String(message.getPayload()));
                    if (callBack!=null) {
                        callBack.OnMQTTMessage(topic,new String(message.getPayload()));
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
        }
    }


    private OnMQTTMessageListener callBack;

    public void setOnMQTTMessageListener(OnMQTTMessageListener callBack){
        this.callBack = callBack;
    }


    public interface OnMQTTMessageListener{
        void OnMQTTMessage(String topic,String message);
    }

    /**
     * 触发连接
     */
    public void doConnect() {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(USER_NAME);
        connectOptions.setPassword(PASSWORD.toCharArray());
        connectOptions.setAutomaticReconnect(true);
        //设置是否清除session，清除后服务器不会保留记忆
        connectOptions.setCleanSession(false);
        //设置超时时间，默认30s
        connectOptions.setConnectionTimeout(45);
        //设置心跳时间，默认60s
        connectOptions.setKeepAliveInterval(30);
        try {
            mClient.connect(connectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    isConnected = true;
                    DisconnectedBufferOptions disconnectedBufferOptions =
                            new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mClient.setBufferOpts(disconnectedBufferOptions);
//                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    isConnected = false;
                }
            });

        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param topic
     * @param qos  0 1,2
     */
    public void subscribeToTopic(String topic,int qos) {
        if(!isConnected || null == mClient)
            return;
        try {
            mClient.subscribe(topic, qos, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    int msgId = asyncActionToken.getMessageId();
                    int test = msgId;
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });

        } catch (MqttException ex) {
            ex.printStackTrace();
        }


    }

    public void publish(String topic, MqttMessage mqttMessage) {
        if(mClient!= null && isConnected) {
            try {
                mClient.publish(topic,mqttMessage);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发布消息
     * @param topic 发布消息主题
     * @param msg 消息体
     *            https://www.jianshu.com/p/51c8c9b05a9c?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation
     */
    public void publish(String topic,String msg,int qos) {

        try {
            if (mClient != null && isConnected) {
                MqttMessage message = new MqttMessage();
                message.setQos(qos);
                message.setRetained(false);//是否为保留消息
                message.setPayload(msg.getBytes());
                mClient.publish(topic, message);
            }
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void disconnect() {
        try {
            if (mClient != null & isConnected) {
                mClient.disconnect();
                this.isConnected = false;
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}