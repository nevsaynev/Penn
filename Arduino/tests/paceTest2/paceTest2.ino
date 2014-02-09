/*
  Pacemaker Test 2
  
  CIS 541
  Fall 2013
  Pacemaker Project
  Group 2
*/

#include <TimerOne.h>
//verify AVI & LRI
// I/O Pins
// Pins 0 & 1 used for serial transfer (for debugging purposes)
const int aSensePin = 4;     // the atrial sense pin
const int vSensePin = 5;     // the ventricular sense pin
//const int aPacePin = 2;      // the atrial pace pin
//const int vPacePin = 3;      // the ventricular pace pin
const unsigned long pulseTime = 10;    // pulse time for sense outputs

////////////////////////////////
const int AVI=150;
const int LRI=1000;
const int VPR=200;
const int PVARP=300;
const int URI=500;
const int PAVB=60;
const int VSP=100;

////////////////////////////////////

// State
volatile unsigned long time = 0;      // time counter
volatile unsigned long Aclk=0;
volatile unsigned long Vclk=0;
volatile int checkA=0;
volatile int checkV=0;
volatile int Vtime=0;

void setup() {
  // attach external interrupts
  attachInterrupt(0, APace, RISING);
  attachInterrupt(1, VPace, RISING);
  
  // initialize the sense pins as outputs:
  pinMode(aSensePin, OUTPUT);
  pinMode(vSensePin, OUTPUT);

  // initialize timer1, and set a 1 millisecond period
  Timer1.initialize(1000);
  // attaches timeCallback() as a timer overflow interrupt
  Timer1.attachInterrupt(timeCallback);
  
  randomSeed(analogRead(0)); // seed our random number generator with random analog noise.

  // initialize serial communication (for debugging)
  Serial.begin(115200);
}

void loop() {
  if(time==855 && checkA != 1)
  {
    Serial.println("Apace 1 FAIL");
  }
  if(time==1005 && checkV !=1)
  {
    Serial.println("Vpace 1 FAIL");
  }
  if(time==1550)
  {
    ASense();
  }
  if(time==1705 && checkV !=1)
 {
   Serial.println("Vpace 2 FAIL");
 }
 if(time==2555 && checkA != 1)
  {
    Serial.println("Apace 2 FAIL");
  }
 if(time == 2680)
 {
   VSense();
 }
 if(time==2705 && checkV == 1)
  {
    Serial.println("Vpace 2a FAIL");
  } 
 if(time == 3150)
 {
   VSense();
 }
 
 if(time == 4005 && checkA != 1)
  {
    Serial.println("Apace 3 FAIL");
  } 
 if(time == 4155 && checkV !=1)
 {
     Serial.println("Vpace 3 FAIL");
 }

if(time == 10 || time == 1010 || time == 1710 || time == 4160)
{
  checkV=0;
}
if(time == 860 || time == 2560 || time == 4010)
{
  checkA=0;
}

}

void ASense() {
  // DEBUG
  Serial.print("-- ASense! -- ");
  Serial.println(time);
    
  digitalWrite(aSensePin, HIGH);
  delay(pulseTime);
  digitalWrite(aSensePin, LOW);
}

void VSense() {
  // DEBUG
  Serial.print("-- VSense! -- ");
  Serial.println(time);

  digitalWrite(vSensePin, HIGH);
  delay(pulseTime);
  digitalWrite(vSensePin, LOW);
}

void APace() {
  // DEBUG
  Serial.print("-- APace? -- ");
  Serial.println(time);
   checkA=1;
   Aclk=0;
//  time = 0;
}

void VPace() {
  // DEBUG
  Serial.print("-- VPace? -- ");
  Serial.println(time);
  checkV=1;
  if(Vtime==0)
  {
  time = 0;
  Vtime=1;
  }
  if(Vtime!=0)
  {
    Vtime+=1;
  }
  if(time>=4200)
  {
    time=0;
    Vtime=0;
  }
  
}

void timeCallback() {
  // DEBUG
  if ((time % 300) == 0) {
    Serial.print("time ");
    Serial.println(time);
  }
  Aclk += 1;
  Vclk += 1;
  time += 1;
}

