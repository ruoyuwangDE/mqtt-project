package com.example.mqtt_test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    MqttHelper mqttHelper;

    EditText editText;
    TextView dataReceived, dataReceived2;

    String uploadMessage, recievedMessage, recievedMessage2;
    String Hohe;
    final String subscriptionTopic[] = {"testTopic/0","testTopic/2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.edt);
        dataReceived = (TextView) findViewById(R.id.datarecieved);
        dataReceived2 = (TextView) findViewById(R.id.datarecieved2);
        startMqtt();
    }
    private void startMqtt() {
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                if(topic.equals(subscriptionTopic[0])){
                    recievedMessage = mqttMessage.toString();
                    String dataList[] = recievedMessage.split(";");
                    Hohe = dataList[1];
                    dataReceived.setText(recievedMessage);
                    //Toast.makeText(MainActivity.this, recievedMessage, Toast.LENGTH_SHORT).show();
                    Log.w("Debug", recievedMessage);
                }
                else if(topic.equals(subscriptionTopic[1])){
                    recievedMessage2 = mqttMessage.toString();
                    dataReceived2.setText(recievedMessage2);
                    //Toast.makeText(MainActivity.this, recievedMessage, Toast.LENGTH_SHORT).show();
                    Log.w("Debug", recievedMessage2);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
    public void publish(View view) {
        //发送信息（upload）
        uploadMessage = editText.getText().toString();
        if(uploadMessage == "50"){
            Toast.makeText(this, " have reached the specified Height !!!!", Toast.LENGTH_SHORT).show();
        }
        else{
            mqttHelper.publishToTopic(uploadMessage);
            Toast.makeText(this, "publish success!", Toast.LENGTH_LONG).show();
        }
    }
}