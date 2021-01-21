#include <ESP8266WiFi.h>
#include <Servo.h>
 
const char* ssid = "";//type your ssid
const char* password = "";//type your password
 
int ledPin = 2; // GPIO2 of ESP8266
WiFiServer server(2060);

int offTilt = 120;
int onTilt = 60;
int neutralTilt = 90;
int turnDelay = 200;

Servo servo;
 
void setup() {  
  // initialize Servo
  servo.attach(0);
  digitalWrite(ledPin, LOW);
  
  delay(10);
 
 
  pinMode(ledPin, OUTPUT);
   
  // Connect to WiFi network
  byte  ip[] = {192, 168, 0, 8};
  byte  gateway[] = {192, 168, 0, 1};
  byte  subnet[] = {255, 255, 255, 0};
  WiFi.config(ip, gateway, subnet);
  WiFi.begin(ssid, password);
   
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
  }
   
  // Start the server
  server.begin();
  digitalWrite(ledPin, HIGH);
}
 
void loop() {
  // Check if a client has connected
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
   
  // Wait until the client sends some data
  while(!client.available()){
    delay(1);
  }
   
  // Read the first line of the request
  String request = client.readStringUntil('\r');
  client.flush();
   
  // Match the request 
  if (request.indexOf("lights?turn=on") != -1) {
    servo.write(onTilt);
    delay(turnDelay);
    servo.write(neutralTilt);
    returnText(client, "ON");
  } 
  if (request.indexOf("lights?turn=off") != -1){
    servo.write(offTilt);
    delay(turnDelay);
    servo.write(neutralTilt);
    returnText(client, "OFF");
  }
 
  delay(1);
 
}

void returnText(WiFiClient client, char *text) {
  client.println("HTTP/1.1 200 OK");
  client.println("Content-Type: text");
  client.println(""); //  do not forget this one
  client.println(text);
}
