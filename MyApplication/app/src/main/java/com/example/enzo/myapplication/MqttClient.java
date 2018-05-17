package com.example.enzo.myapplication;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.Gpio;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Enzo on 17/05/2018.
 */

class MqttClientTest implements MqttCallback {

    private MqttClient client;
    private Gpio mLedGpio;



    public void initializeMqttClient(Gpio gpioId) throws MqttException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {


        this.mLedGpio=gpioId;

        try {
            client = new MqttClient("tcp://test.mosquitto.org:1883", "AndroidThingSub", new MemoryPersistence());
            client.setCallback(this);
            client.connect();

            String topic = "crescenzomugione/topic/led";
            client.subscribe(topic);

        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {


        this.mLedGpio.setValue(!mLedGpio.getValue());


    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
