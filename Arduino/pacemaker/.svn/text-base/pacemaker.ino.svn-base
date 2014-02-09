/*
  Pacemaker Model
  
  CIS 541
  Fall 2013
  Pacemaker Project
  Group 2
*/

#include <TimerOne.h>
//#include <QueueList.h>

// I/O Pins
// Pins 0 & 1 used for serial transfer (for debugging purposes)
//const int aSensePin = 2;      // the atrial sense pin
//const int vSensePin = 3;      // the ventricular sense pin
const int aPacePin = 4;       // the atrial pace pin
const int vPacePin = 5;       // the ventricular pace pin
const int ledPin = 13;
const unsigned long pulseTime = 10;    // pulse time for sense outputs
const int AVI = 150;          // AVI
const int LRI = 1000;         // LRI
const int VRP = 200;          // VRP
const int PVARP = 300;        // PVARP
const int URI = 500;          // URI
const int PAVB = 60;          // PAVB
const int VSP = 100;          // VSP
boolean DEBUG = true;

enum pacestate {
  In_PVARP,
  Wait_for_Aevent,
  In_PAVB_VSP,
  In_VSP,
  Wait_for_Vevent,
  VSP_EX,
  AVI_BRANCH,
  AVI_EX,
  In_VRP_PVARP
};

// Initial State
volatile pacestate state = In_VRP_PVARP;
pacestate lastState = Wait_for_Vevent;
volatile boolean ledOn = false;
volatile unsigned long c_lri = 0;
volatile unsigned long c_avi = 0;

void setup() {
  // attach external interrupts
  attachInterrupt(0, ASense, RISING);
  attachInterrupt(1, VSense, RISING);

  // initialize the pace pins as outputs
  pinMode(aPacePin, OUTPUT);
  pinMode(vPacePin, OUTPUT);
  
  // initialize the LED output pin
  pinMode(ledPin, OUTPUT);

  // initialize timer1, and set a 1 millisecond period
  Timer1.initialize(1000);
  // attaches timeCallback() as a timer overflow interrupt
  Timer1.attachInterrupt(timeCallback);

  // initialize serial communication (for debugging)
  if (DEBUG) {
    Serial.begin(115200);
    Serial.println("====START====");
  }
}

void loop() {
  if (DEBUG) {
    // Only print state output when it changes.
    if (state != lastState) {
      Serial.print(stateToString(state));
      Serial.print(c_lri);
      Serial.print(" ");
      Serial.println(c_avi);
      lastState = state;
    }
  }
  
  switch (state) {
    case In_PVARP:
      if (c_lri >= PVARP) {
        state = Wait_for_Aevent;
      }
      break;
    case Wait_for_Aevent:
      if (c_lri >= LRI-AVI) {
        // c_avi reset must occur before APace due to pulse delay in APace
        c_avi = 0;
        APace();
        state = In_PAVB_VSP;
      }
      break;
    case In_PAVB_VSP:
      if (c_avi >= PAVB) {
        state = In_VSP;
      }
      break;
    case In_VSP:
      if (c_avi >= VSP) {
        state = Wait_for_Vevent;
      }
      break;
    case VSP_EX:
      if (c_avi >= VSP) {
        // c_lri reset must occur before VPace due to pulse delay in VPace
        c_lri = 0;
        VPace();
        state = In_VRP_PVARP;
      }
      break;
    case Wait_for_Vevent:
      if (c_lri >= URI && c_avi >= AVI) {
        // c_lri reset must occur before VPace due to pulse delay in VPace
        c_lri = 0;
        VPace();
        state = In_VRP_PVARP;
      }
      break;
    case AVI_BRANCH:
      if(c_lri <= URI) {
        state = AVI_EX;
      }
      else {
        state = Wait_for_Vevent;
      }
      break;
    case AVI_EX:
      if (c_lri >= URI && c_avi >= AVI) {
        // c_lri reset must occur before VPace due to pulse delay in VPace
        c_lri = 0;
        VPace();
        state = In_VRP_PVARP;
      }
      break;
    case In_VRP_PVARP:
      if (c_lri >= VRP) {
        state = In_PVARP;
      }
      break;
    default:
      //Serial.println("Should not be in here!");
      break;
  }
}

void VPace() { 
  if (DEBUG) {
    Serial.print("VPace! ");
    Serial.print(c_lri);
    Serial.print(" ");
    Serial.println(c_avi);
  }
        
  digitalWrite(vPacePin, HIGH);
  delay(pulseTime);
  digitalWrite(vPacePin, LOW);
}

void APace() {
  if (DEBUG) {
    Serial.print("APace! ");
    Serial.print(c_lri);
    Serial.print(" ");
    Serial.println(c_avi);
  }

  digitalWrite(aPacePin, HIGH);
  delay(pulseTime);
  digitalWrite(aPacePin, LOW);
}

void ASense() {
  if (DEBUG) {
    Serial.print("ASense? ");
    Serial.print(c_lri);
    Serial.print(" ");
    Serial.println(c_avi);
  }

  switch (state) {
    case In_PVARP:
      break;
    case Wait_for_Aevent:
      state = AVI_BRANCH;
      c_avi = 0;
      break;
    case In_PAVB_VSP:
      break;
    case In_VSP:
      break;
    case In_VRP_PVARP:
      break;
    default:
      //Serial.println("Should not be in here!");
      break;
  }
}

void VSense() {
  if (DEBUG) {
    Serial.print("VSense? ");
    Serial.print(c_lri);
    Serial.print(" ");
    Serial.println(c_avi);
  }
  
  switch (state) {
    case In_PVARP:
      state = In_VRP_PVARP;
      c_lri = 0;
      break;
    case In_PAVB_VSP:
      break;
    case In_VSP:
      state = VSP_EX;
      break;
    case Wait_for_Aevent:
      state = In_VRP_PVARP;
      c_lri = 0;
      break;
    case Wait_for_Vevent:
      state = In_VRP_PVARP;
      c_lri = 0;
      break;
    case In_VRP_PVARP:
      break;
    default:
      //Serial.println("Should not be in here!");
      break;
  }
}

void timeCallback() {
  // TEMPORARILY DISABLED CLOCK OUTPUT
  if (false) {
    if ((c_lri % 500) == 0 || (c_avi % 500) == 0) {
      Serial.print(c_lri);
      Serial.print(" ");
      Serial.println(c_avi);
    }
  }
  
  if ((c_lri % 800) == 0) {
    if (!ledOn) {
      digitalWrite(ledPin, HIGH);
      ledOn = true;
    }
    else {
      digitalWrite(ledPin, LOW);
      ledOn = false;
    }
  }
  
  c_lri += 1;
  c_avi += 1;
}

String stateToString(int state) {
  switch (state) {
    case In_PVARP:
      return "In_PVARP ";
    case Wait_for_Aevent:
      return "Wait_for_Aevent ";
    case In_PAVB_VSP:
      return "In_PAVB_VSP ";
    case In_VSP:
      return "In_VSP ";
    case Wait_for_Vevent:
      return "Wait_for_Vevent ";
    case VSP_EX:
      return "VSP_EX ";
    case AVI_BRANCH:
      return "AVI_BRANCH ";
    case AVI_EX:
      return "AVI_EX ";
    case In_VRP_PVARP:
      return "In_VRP_PVARP ";
    default:
      return "ERROR STATE ";
  }
}
