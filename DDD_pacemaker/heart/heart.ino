/*
  Heart Model
  
  CIS 541
  Fall 2013
  Pacemaker Project
  Group 2
*/

#include <TimerOne.h>
#include <Button.h>

// I/O Pins
// Pins 0 & 1 used for serial transfer (for debugging purposes)
//const int aPacePin = 2;    // the atrial pace pin (external interrupt)
//const int vPacePin = 3;    // the ventricular pace pin (external interrupt)
const int aSensePin = 4;     // the atrial sense pin
const int vSensePin = 5;     // the ventricular sense pin
const int aSenseButtonPin = 6;  // the atrial sense button
const int vSenseButtonPin = 7;  // the ventricular sense button
const int ledPin = 13;

const int minwaitAPin = 0;   // the analog minwaitA initialization pin
const int minwaitVPin = 1;   // the analog minwaitV initialization pin
const unsigned long pulseTime = 10;  // pulse time for sense outputs
boolean DEBUG = true;

// initialize buttons
Button aSenseButton = Button(aSenseButtonPin, PULLUP);
Button vSenseButton = Button(vSenseButtonPin, PULLUP);

volatile boolean ledState = false;
volatile unsigned long time = 0;   // time counter, volatile due to changes in ISRs
unsigned int minwait_A = 1000;     // default minimum atrial separation time
unsigned int minwait_V = 1000;     // default minimum ventricular separation time

void setup() {
  // attach external interrupts
  attachInterrupt(0, APace, RISING);  // DIGITAL Pin 2
  attachInterrupt(1, VPace, RISING);  // DIGITAL Pin 3
  
  // initialize the sense pins as outputs:
  pinMode(aSensePin, OUTPUT);
  pinMode(vSensePin, OUTPUT);
  
  // initialize minwait_A and minwait_V from potentiometers
  minwait_A = analogRead(minwaitAPin);
  minwait_V = analogRead(minwaitVPin);

  // initialize timer1, and set a 1 millisecond period
  Timer1.initialize(1000);
  // attaches timeCallback() as a timer overflow interrupt
  Timer1.attachInterrupt(timeCallback);
  
  randomSeed(analogRead(2)); // seed our random number generator with random analog noise.

  // initialize serial communication (for debugging)
  if (DEBUG) {
    Serial.begin(115200);
    Serial.println("====STARTUP====");
    Serial.print("minwait_A: ");
    Serial.print(minwait_A);
    Serial.print(" minwait_V: ");
    Serial.println(minwait_V);
  }
}

void loop() {
  if (aSenseButton.uniquePress()) {
    Serial.println("aSenseButton pressed");
    ASense();
  }
  
  if (vSenseButton.uniquePress()) {
    Serial.println("vSenseButton pressed");
    VSense();
  }
  
  switch (random(2)) {
    case 0:
      // send atrial sense
      if (time >= minwait_A) {
        ASense();
      }
      break;
    case 1:
      // send ventricular sense
      if (time >= minwait_V) {
        VSense();
      }
      break;
  }
}

void ASense() {
  // DEBUG
  if (DEBUG) {
    Serial.print("ASense! ");
    Serial.println(time);
  }
  
  time = 0;
  digitalWrite(aSensePin, HIGH);
  delay(pulseTime);
  digitalWrite(aSensePin, LOW);
}

void VSense() {
  // DEBUG
  if (DEBUG) {
    Serial.print("VSense! ");
    Serial.println(time);
  }
  
  time = 0;
  digitalWrite(vSensePin, HIGH);
  delay(pulseTime);
  digitalWrite(vSensePin, LOW);
}

void APace() {
  // DEBUG
  if (DEBUG) {
    Serial.print("APace? ");
    Serial.println(time);
  }
  
  time = 0;
}

void VPace() {
  // DEBUG
  if (DEBUG) {
    Serial.print("VPace? ");
    Serial.println(time);
  }
  
  time = 0;
}

void timeCallback() {
  // DEBUG
  if (DEBUG) {
    if ((time % 250) == 0) {
      Serial.println(time);
    }
  }
  
  if ((time % 500) == 0) {
    if (!ledState) {
      digitalWrite(ledPin, HIGH);
      ledState = true;
    }
    else {
      digitalWrite(ledPin, LOW);
      ledState = false;
    }
  }
  
  time += 1;
}

