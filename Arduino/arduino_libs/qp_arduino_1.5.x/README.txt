
***********************************************************************
NOTE: This README provides a quick overview of QP for Arduino.
Please refer to the Application Note "Event-Driven Arduino
Programming with QP" (AN_Event-Driven_Arduino.pdf), available in
the doc\ sub-directory for more details.
***********************************************************************


Event-Driven Arduino Programming with QP
========================================
Traditionally, Arduino programs are written in a sequential manner,
which means that whenever an Arduino program needs to synchronize
with some external event, such as a button press, arrival of a
character through the serial port, or a time delay, it explicitly
waits in-line for the occurrence of the event. Waiting in-line
means that the Arduino processor spends all of its cycles constantly
checking for some condition in a tight loop (called the polling
loop).

Although this approach is functional in many situations, it doesn't
work very well when there are multiple possible sources of events
whose arrival times and order you cannot predict and where it is
important to handle the events in a timely manner. The fundamental
problem is that while a sequential program is waiting for one kind
of event (e.g., a button press), it is not doing any other work and
is not responsive to other events (e.g., characters from the serial
port).

Another big problem with the sequential program structure is
wastefulness in terms of power dissipation. Regardless of how much
or how little actual work is being done, the Arduino processor is
always running at top speed, which drains the battery quickly and
prevents you from making truly long-lasting battery-powered devices.

For these and other reasons experienced programmers turn to the
long-know design strategy called event-driven programming, which
requires a distinctly different way of thinking than conventional
sequential programs. All event-driven programs are naturally
divided into the application, which actually handles the events,
and the supervisory event-driven infrastructure (framework), which
waits for events and dispatches them to the application. The control
resides in the event-driven framework, so from the application
standpoint, the control is inverted compared to a traditional
sequential program.

It turns out that the event-driven QP active object framework
beautifully complements the Arduino platform and provides everything
you need to build responsive, robust, and power-efficient Arduino
programs based on modern hierarchical state machines.

QP improves productivity, because your programs stay responsive
to new events as you keep adding them. You also no longer need
to struggle with convoluted if-else logic, because this "spaghetti"
code is replaced with elegant state machines. And you get a powerful
multitasking support, without worrying about semaphores and other
such low-level mechanisms typically found in RTOSes. Instead, you
can work at a higher level of abstraction of events, state machines,
and active objects.


Graphical Arudino Programming with State Machines
=================================================
QP is also an excellent target for automatic code generation. To this
end, QP is now supported by the the free graphical QM modeling tool,
which can automatically generate complete Arduino sketches from state
diagrams. Thus QP is your entry into graphical programming for Arduino.


Preemptive and Cooperative Kernels
==================================
The provided QP library code, to be installed in <arduino>\libraries\qp)
is configured to use the cooperative "Vanilla" kernel of QP. This
configuration works for the Blinky and PELICAN examples, but does
*not* work for the dpp_qk example, which demonstrates the *preemptive*
QK kernel.

***************************************************************************
NOTE: Please refer to the Application Note AN_Event-Driven_Arduino.pdf for
details of how to configure the QP library for the preemptive QK kernel.  
***************************************************************************


Licensing
=========
The QP frameworks may be distributed and modified under the terms of the
GNU General Public License (GPL) as published by the Free Software
Foundation and appearing in the file GPL.TXT included in the QP
package.

Alternatively, the QP frameworks may be distributed and modified under
the terms of Quantum Leaps, LLC commercial licenses, which expressly
supersede the GPL and are specifically designed for licensees interested
in retaining the proprietary status of their code.

The QM graphical modeling tool is free to download and use, but is not
open source. The QM tool is provided under the terms of a simple
End-User License Agreement (EULA).


Contact information
===================
Quantum Leaps Websites: http://www.state-machine.com
                        http://www.quantum-leaps.com
e-mail:                 info@quantum-leaps.com
toll-free:              1-866-450-LEAP (US Eastern Standard Time)
