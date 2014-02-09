//////////////////////////////////////////////////////////////////////////////
// Product: TCL script for building Arduino sketches
// Last updated for: Arduino 1.5.2
// Date of the Last Update: Jul 30, 2013
//
//                    Q u a n t u m     L e a P s
//                    ---------------------------
//                    innovating embedded systems
//
// Copyright (C) 2002-2013 Quantum Leaps, LLC. All rights reserved.
//
// This program is open source software: you can redistribute it and/or
// modify it under the terms of the GNU General Public License as published
// by the Free Software Foundation, either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
//
// Contact information:
// Quantum Leaps Web sites: http://www.quantum-leaps.com
//                          http://www.state-machine.com
// e-mail:                  info@quantum-leaps.com
//////////////////////////////////////////////////////////////////////////////
//
// usage: arduinodude <BOARD> <COM-port>
//
// for example: arduinodude UNO COM5
//
// NOTE: the environment variable ARDUINO_HOME must be defined and point to
// the installation directory of the standard Arduino software.
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <ctype.h>

static char buffer[512];

static char ARDUINO_HOME[256];
static char BOARD  [40];
static char MCU    [40];
static char SPEED  [40];
static char PROJECT[40];

//............................................................................
static size_t min(size_t x, size_t y) {
    if (x < y) {
        return x;
    }
    else {
        return y;
    }
}
//............................................................................
int main(int argc, char *argv[]) {
    if (argc != 3) {
        printf("usage: "
            "arduinodude <BOARD> <COM-port>\n"
            "for example: arduinodude UNO COM5\n");
        return 1;
    }

    char const *p = argv[1];  // board
    char *d = BOARD;
    while (*p != '\0') {
        *d++ = tolower(*p++); // convert to lower case
    }
    *d = '\0';

    char const *COM = argv[2];
    //printf("BOARD=%s, COM=%s\n", BOARD, COM);

    // ARDUINO_HOME is the directory of the standard Arduino software.
    // The following code attempts to set this directory first from the
    // environment variable ARDIONO_HOME. However, if this environment variable
    // is not defined, it is set from the location of this TCL script, which
    // is assumed to be nested two levels deep in the ARDUONO_HOME directory.
    p = getenv("ARDUINO_HOME");
    if (p == NULL) {  // ARDUIONO_HOME not defined?
        printf("argv[0]=%s\n", argv[0]);
        p = argv[0] + strlen(argv[0]); // the path to this file
        int n = 3;
        while ((p > argv[0]) && (n != 0)) {
            --p;
            if (*p == '\\') {
                --n;
            }
        }
        if (p == argv[0]) {
            perror("arduinodude not installed in the standard location.");
            return 1;
        }
        strncpy(ARDUINO_HOME, argv[0], p - argv[0]);
    }
    else {
        if (*p == '"') {
            strncpy(ARDUINO_HOME, p+1, min(strlen(p)-2, sizeof(ARDUINO_HOME)));
        }
        else {
            strncpy(ARDUINO_HOME, p,   min(strlen(p),   sizeof(ARDUINO_HOME)));
        }
    }
    //printf("ARDUINO_HOME=%s\n", ARDUINO_HOME);

    if (getcwd(buffer, sizeof(buffer)) != NULL) {
        p = buffer + strlen(buffer); // the path to this file
        while ((p > buffer) && (*(p - 1) != '\\')) {
            --p;
        }
        if (strlen(p) < sizeof(PROJECT)) {
            strcpy(PROJECT, p);
            printf("Current working dir: %s\n", PROJECT);
        }
        else {
            perror("current directory name too long for an Arduino project.");
            return 1;
        }
    }

    snprintf(buffer, sizeof(buffer),
             "%s\\hardware\\arduino\\avr\\boards.txt",
             ARDUINO_HOME);

    FILE *boards = fopen(buffer, "r");
    if (boards == NULL) {
        perror("boards.txt file not found");
        return 1;
    }

    snprintf(MCU,   sizeof(MCU),   "%s.build.mcu",    BOARD);
    snprintf(SPEED, sizeof(SPEED), "%s.upload.speed", BOARD);

    int found = 0;
    while (fgets(buffer, sizeof(buffer), boards) != NULL) {
        if ((p = strstr(buffer, MCU)) != NULL) {
            p = &buffer[strlen(buffer)];
            while ((p >= buffer) && (*(p - 1) != '=')) {
                --p;
            }
            strncpy(MCU, p, min(sizeof(MCU), strlen(p)));
            MCU[strlen(p)-1] = '\0';  // get rid of '\n'
            //printf("MCU=%s\n", MCU);
            found |= (1 << 0);
        }
        if ((p = strstr(buffer, SPEED)) != NULL) {
            p = &buffer[strlen(buffer)];
            while ((p >= buffer) && (*(p - 1) != '=')) {
                --p;
            }
            strncpy(SPEED, p, min(sizeof(SPEED), strlen(p)));
            SPEED[strlen(p)-1] = '\0';  // get rid of '\n'
            //printf("SPEED=%s\n", SPEED);
            found |= (1 << 1);
        }
        if (found == 0x3) {
            break;
        }
    }
    fclose(boards);

    snprintf(buffer, sizeof(buffer),
             "%s\\hardware\\tools\\avr\\bin\\avrdude "
             "-p %s "
             "-c arduino -P %s "
             "-b %s -D -v "
             "-C %s\\hardware\\tools\\avr\\etc\\avrdude.conf "
             "-U flash:w:bin\\%s.hex:i",
             ARDUINO_HOME,
             MCU,
             COM,
             SPEED,
             ARDUINO_HOME,
             PROJECT);
    //printf(buffer);

    FILE *pipe = _popen(buffer, "rt" );
    if (pipe == NULL) {
        perror("Failed to launch avrdude");
        return 1;
    }

    while (fgets(buffer, sizeof(buffer), pipe)) {
        printf(buffer);
    }
    if (feof(pipe)) {
        printf( "\nProcess returned %d\n", _pclose(pipe));
    }
    else {
        perror("Failed to read the pipe to the end");
        return 1;
    }

    return 0;
}