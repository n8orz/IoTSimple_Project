//Nathan Geyer
//ESP8266 or Arduino code for sending sensor data to server
 
#include <ESP8266WiFi.h>
#include <WiFiClient.h> 
#include <ESP8266WebServer.h>
#include <ESP8266HTTPClient.h>
#include <DHTesp.h>
//initialize pins for input
DHTesp dht;
int dhtPin = 4;
 
//wireless credentials
const char *ssid = "Asberry School of Music";
const char *password = "P1an4w4eva!";
 
//Web/Server address to read/write from 
const char *host = "192.168.1.10";   //server address

//set user email for firebase
const String email = "vjTzImx8IYQ2eWGjiguILa1oMZZ2";

//name sensor
const String sensorName = "water";


 
//=======================================================================
//                    Power on setup
//=======================================================================
 
void setup() {
  delay(1000);
  Serial.begin(115200);
  WiFi.mode(WIFI_OFF);        //Prevents reconnection issue (taking too long to connect)
  delay(1000);
  WiFi.mode(WIFI_STA);        //This line hides the viewing of ESP as wifi hotspot
  
  WiFi.begin(ssid, password);     //Connect to your WiFi router
  Serial.println("");
 
  Serial.print("Connecting");
  // Wait for connection
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  //If connection successful show IP address in serial monitor
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());  //IP address assigned to your ESP
  dht.setup(dhtPin, DHTesp::DHT11);
}
 
//=======================================================================
//                    Main Program Loop
//=======================================================================
void loop() {
  HTTPClient http;    //Declare object of class HTTPClient
  //create object for temp and humidity
  TempAndHumidity lastValues = dht.getTempAndHumidity();
  Serial.println("Temperature: " + String(lastValues.temperature,0));
  Serial.println("Humidity: " + String(lastValues.humidity,0));
  
 
  String ADCData, station, getData, Link;
  int adcvalue=analogRead(A0);  //Read Analog value of LDR
  ADCData = String(adcvalue);   //String to interger conversion
  station = "B";
 
  //GET Data
  //sends an http request to the server
  getData = "?status=" + ADCData + "&station=" + station ;  //Note "?" added at front
  Link = "http://192.168.1.9:3000/users/" + email + "/sensor/" +  sensorName + "/data/37/" + getData;
  
  http.begin(Link);     //Specify request destination
  
  int httpCode = http.GET();            //Send the request
  String payload = http.getString();    //Get the response payload
 
  Serial.println(httpCode);   //Print HTTP return code
  Serial.println(payload);    //Print request response payload
 
  http.end();  //Close connection
  
  delay(5000);  //GET Data at every 5 seconds
}
