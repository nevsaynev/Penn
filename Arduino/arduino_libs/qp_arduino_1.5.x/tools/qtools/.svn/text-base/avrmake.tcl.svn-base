##############################################################################
# Product: TCL script for building Arduino sketches
# Last updated for: Arduino 1.5.2
# Date of the Last Update: Jul 30, 2013
#
#                    Q u a n t u m     L e a P s
#                    ---------------------------
#                    innovating embedded systems
#
# Copyright (C) 2002-2013 Quantum Leaps, LLC. All rights reserved.
#
# This program is open source software: you can redistribute it and/or
# modify it under the terms of the GNU General Public License as published
# by the Free Software Foundation, either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program. If not, see <http://www.gnu.org/licenses/>.
#
# Contact information:
# Quantum Leaps Web sites: http://www.quantum-leaps.com
#                          http://www.state-machine.com
# e-mail:                  info@quantum-leaps.com
##############################################################################
#
# usage
# $tclsh arduinomake.tcl <BOARD> "<LIBS>" ["<DEFINES>"]
#
# NOTE: Typically, you don't need to modify this script to build your Arduino
# sketches. All information is provided in the parameters.
#

if { $argc < 2 } {
    puts "usage: arduinomakemake.tcl <BOARD> <PROJECT> \"<LIBS>\"" \[\"<DEFINES>\"\]
    puts "for example: tclsh arduinomake.tcl UNO \"qp\"" \"QK_PREEMPTIVE\"
    puts
    puts "The supported boards are (from boards.txt):"
    puts "uno"
    puts "atmega328   (Duemilanove w/ ATmega328)"
    puts "diecimila"
    puts "nano328     (Nano w/ ATmega328)"
    puts "nano        (Nano w/ ATmega168)"
    puts "mega2560    (Mega 2560 or Mega ADK)"
    puts "mega        (Mega ATmega1280)"
    puts "leonardo"
    puts "esplora"
    puts "micro"
    puts "mini328     (Mini w/ ATmega328)"
    puts "mini        (Mini w/ ATmega168)"
    puts "ethernet"
    puts "fio"
    puts "bt328       (BT w/ ATmega328)"
    puts "bt          (BT w/ ATmega168)"
    puts "LilyPadUSB"
    puts "lilypad328  (LilyPad w/ ATmega328)"
    puts "lilypad     (LilyPad w/ ATmega168)"
    puts "pro5v328    (Pro or Pro Mini (5V, 16 MHz) w/ ATmega328)"
    puts "pro5v       (Pro or Pro Mini (5V, 16 MHz) w/ ATmega168)"
    puts "pro328      (Pro or Pro Mini (3.3V, 8 MHz) w/ ATmega328)"
    puts "pro         (Pro or Pro Mini (3.3V, 8 MHz) w/ ATmega168)"
    puts "atmega168   (NG or older w/ ATmega168)"
    puts "atmega8     (NG or older w/ ATmega8)"

    exit 1
}
set ARDUINO_BOARD [lindex $argv 0]
set ARDUINO_LIBS  [lindex $argv 1]
set ARDUINO_DEFS  [lindex $argv 2]

#=============================================================================

# ARDUINO_HOME is the directory of the standard Arduino software.
# The following code attempts to set this directory first from the
# environment variable ARDIONO_HOME. However, if this environment variable
# is not defined, it is set from the location of this TCL script, which
# is assumed to be nested two levels deep in the ARDUONO_HOME directory.
#
if { [catch {set ARDUINO_HOME $env(ARDUINO_HOME)}] } {
    set ARDUINO_HOME [file dirname $argv0]/../..
} else {
    set ARDUINO_HOME [string trim $ARDUINO_HOME \" ]
}
set ARDUINO_HOME [file normalize [string trim $ARDUINO_HOME \" ]]

set AVR_TOOLS "$ARDUINO_HOME/hardware/tools/avr/bin"
set CC   "$AVR_TOOLS/avr-gcc"
set CXX  "$AVR_TOOLS/avr-g++"
set AR   "$AVR_TOOLS/avr-ar"
set LINK "$AVR_TOOLS/avr-gcc"

set OBJCOPY "$AVR_TOOLS/avr-objcopy"
set OBJDUMP "$AVR_TOOLS/avr-objdump"

#.............................................................................
# directories
#

# project name is the same as the current directory
set PROJECT "[file tail [pwd]]"

set BIN_DIR bin
set LIB_DIR lib

#.............................................................................
# project source files
#
set CXX_FILES [glob -nocomplain *.cpp]
set CXX_FILES [concat $CXX_FILES [glob -nocomplain *.ino]]
set C_FILES   [glob -nocomplain *.c]

#.............................................................................
# extract the board-dependent settings (from boards.txt)
#
if { [catch {open "$ARDUINO_HOME/hardware/arduino/avr/boards.txt" "r"} fid] } {
    puts stderr $fid
    exit 1
}
set contentList [split $[read -nonewline $fid] "\n "]
catch { close $fid }

set ARDUINO_BOARD [string tolower $ARDUINO_BOARD]

set match [lsearch -inline $contentList "$ARDUINO_BOARD.build.mcu=*"]
set BUILD_MCU [string range $match [expr [string first "=" $match] + 1] end]

set match [lsearch -inline $contentList "$ARDUINO_BOARD.build.core=*"]
set BUILD_CORE [string range $match [expr [string first "=" $match] + 1] end]

set match [lsearch -inline $contentList "$ARDUINO_BOARD.build.variant=*"]
set BUILD_VARIANT [string range $match [expr [string first "=" $match] + 1] end]

set match [lsearch -inline $contentList "$ARDUINO_BOARD.build.f_cpu=*"]
set BUILD_F_CPU [string range $match [expr [string first "=" $match] + 1] end]

if { $BUILD_MCU == "" || $BUILD_CORE == "" \
     || $BUILD_VARIANT == "" || $BUILD_F_CPU == "" } {
    puts stderr "Arduino board \"$ARDUINO_BOARD\" not found"
    exit 1
}

#.............................................................................
# compile and link options
#
set CORE_DIR    "$ARDUINO_HOME/hardware/arduino/avr/cores"
set LIBRARY_DIR "$ARDUINO_HOME/hardware/arduino/avr/libraries"

# Place -I options here
set INCLUDES "-I. -I$CORE_DIR/$BUILD_CORE -I$CORE_DIR/../variants/$BUILD_VARIANT"
set DEFINES  "-DF_CPU=$BUILD_F_CPU"
foreach def $ARDUINO_DEFS {
    append DEFINES " -D" $def
}

# Compiler flag to set the C Standard level.
set CXXFLAGS  "-x c++ -mmcu=$BUILD_MCU -Os -Wall \
 -fno-exceptions -ffunction-sections -fdata-sections"
set CFLAGS    "-mmcu=$BUILD_MCU -Os -Wall \
 -ffunction-sections -fdata-sections -std=gnu99"
set LDFLAGS   "-Wl,-Map,$BIN_DIR/$PROJECT.map,--cref"

#=============================================================================
# execute a build command
#
proc make.exec args {
    set cmd [join $args]
    puts $cmd
    catch {eval exec -- $cmd} msg
    if {[string length $msg]} {
        puts $msg
    }
    global EXEC
    incr EXEC
}
#.............................................................................
# return 1 if any file in the dependency list is newer than a given file
#
proc make.filesAreNewer {depList f} {
    foreach dep $depList {
        if {![file exists $dep] || ![file exists $f]} {
            return 1
        }
        if {[file mtime $dep] > [file mtime $f]} {
            return 1
        }
    }
    return 0
}
#.............................................................................
proc make.fileChangeExt { file ext } {
    return [file tail [file rootname $file]]$ext
}
#.............................................................................
# generate TCL list of dependencies from the GNU-type dependency (.d) file
#
proc make.genDepList {depFile} {
    set fileList {}
    if { [catch {open $depFile "r"} fid] } {
        puts stderr $fid
        exit 1
    }
    set contentList [split $[read -nonewline $fid] "\n "]
    catch { close $fid }

    foreach str $contentList {
        if { $str != "\\" && $str != " "} {
            set $str [file normalize $str]
            if { [file exists $str] } {
                lappend fileList [file normalize $str]
            }
        }
    }
    return $fileList
}
#.............................................................................
proc make.delete { dir pattern } {
    set fileList [glob -nocomplain -directory $dir $pattern]
    puts -nonewline "deleting $dir/$pattern"
    set n 0
    foreach f $fileList {
        file delete $f
        incr n
    }
    puts "($n)"
}

#=============================================================================
proc makeCxxDeps { srcList depDir } {
    foreach f $srcList {
        set depFile $depDir/[make.fileChangeExt $f ".d"]
        if { [make.filesAreNewer $f $depFile] } {
            global CXX INCLUDES DEFINES
            make.exec $CXX -x c++ -MM -MT [make.fileChangeExt $f ".o"] \
                      $INCLUDES $DEFINES $f > $depFile
        }
    }
}
#.............................................................................
proc makeCDeps { srcList depDir } {
    foreach f $srcList {
        set depFile $depDir/[make.fileChangeExt $f ".d"]
        if { [make.filesAreNewer $f $depFile] } {
            global CC INCLUDES DEFINES
            make.exec $CC -MM -MT [make.fileChangeExt $f ".o"] \
                      $INCLUDES $DEFINES $f > $depFile
        }
    }
}
#.............................................................................
proc makeCxxObjs { srcList depDir binDir } {
    foreach f $srcList {
        set objFile $binDir/[make.fileChangeExt $f ".o"]
        set depList [make.genDepList $depDir/[make.fileChangeExt $f ".d"]]
        if { [make.filesAreNewer $depList $objFile] } {
            global CXX CXXFLAGS INCLUDES DEFINES
            make.exec $CXX -c $CXXFLAGS $INCLUDES $DEFINES $f -o $objFile
        }
    }
}
#.............................................................................
proc makeCObjs { srcList depDir binDir } {
    foreach f $srcList {
        set objFile $binDir/[make.fileChangeExt $f ".o"]
        set depList [make.genDepList $depDir/[make.fileChangeExt $f ".d"]]
        if { [make.filesAreNewer $depList $objFile] } {
            global CC CFLAGS INCLUDES DEFINES
            make.exec $CC -c $CFLAGS $INCLUDES $DEFINES $f -o $objFile
        }
    }
}
#.............................................................................
proc makeLib { libName libDir outDir } {
    set lib $libDir/$libName
    set libFile $outDir/$libName.a
    if { ![file exists $lib] } {
        puts "library not found $lib"
        exit 1
    }
    if { [file exists $libFile] } {
        return $libFile
    }
    set cxxFiles [glob -nocomplain -directory $lib "*.cpp"]
    set cxxFiles [concat $cxxFiles [glob -nocomplain -directory $lib "*.ino"]]
    set cFiles   [glob -nocomplain -directory $lib "*.c"]
    set utility  ""
    if { [file exists $lib/utility] } {
        set cxxFiles [concat $cxxFiles [glob -nocomplain \
                      -directory $lib/utility "*.cpp"]]
        set cxxFiles [concat $cxxFiles [glob -nocomplain \
                      -directory $lib/utility "*.ino"]]
        set cFiles   [concat $cFiles [glob -nocomplain \
                      -directory $lib/utility "*.c"]]
        set utility "-I$lib/utility"
    }
    foreach f $cxxFiles {
        set objFile $outDir/[make.fileChangeExt $f ".o"]
        global AR CXX CXXFLAGS LIB_INCLUDES DEFINES
        make.exec $CXX -c $CXXFLAGS $LIB_INCLUDES $utility $DEFINES $f -o $objFile
        make.exec $AR rcs $libFile $objFile
        file delete $objFile
    }
    foreach f $cFiles {
        set objFile $outDir/[make.fileChangeExt $f ".o"]
        global AR CC CFLAGS LIB_INCLUDES DEFINES
        make.exec $CC -c $CFLAGS $LIB_INCLUDES $utility $DEFINES $f -o $objFile
        make.exec $AR rcs $libFile $objFile
        file delete $objFile
    }
    return $libFile
}
#.............................................................................
proc makeElf { projName srcList libList binDir } {
    set objList {}
    foreach f $srcList {
        set objFile $binDir/[make.fileChangeExt $f ".o"]
        lappend objList $objFile
    }
    set elfFile $binDir/$projName.elf
    if { [make.filesAreNewer $objList $elfFile] } {
        global LINK CFLAGS LIB_DIR LDFLAGS
        make.exec $LINK $CFLAGS [join $objList] [join $libList] \
                  -lm --output $elfFile $LDFLAGS
    }
}
#.............................................................................
proc makeHex { projName binDir } {
    set elfFile $binDir/$projName.elf
    set hexFile $binDir/$projName.hex
    if { [make.filesAreNewer $elfFile $hexFile ] } {
        global OBJCOPY
        make.exec $OBJCOPY -O ihex -R .eeprom $elfFile $hexFile
    }
}
#.............................................................................
proc makeEep { projName binDir } {
    set elfFile $binDir/$projName.elf
    set eepFile $binDir/$projName.eep
    if { [make.filesAreNewer $elfFile $eepFile ] } {
        global OBJCOPY
        make.exec $OBJCOPY -O ihex -j .eeprom \
                  --set-section-flags=.eeprom=alloc,load \
                  --no-change-warnings --change-section-lma .eeprom=0 \
                  $elfFile $eepFile
    }
}

#-----------------------------------------------------------------------------
set EXEC 0
if { ![file exists $BIN_DIR] } {
    file mkdir $BIN_DIR
}
if { ![file exists $LIB_DIR] } {
    file mkdir $LIB_DIR
}
set LIB_INCLUDES $INCLUDES
set buildinLibs [glob -nocomplain $LIBRARY_DIR/*]
foreach l $buildinLibs {
    append LIB_INCLUDES " -I$l"
}

set libList [makeLib $BUILD_CORE "$CORE_DIR" $LIB_DIR]
foreach l $ARDUINO_LIBS {
    set lib [makeLib $l "$LIBRARY_DIR" $LIB_DIR]
    append INCLUDES " -I$LIBRARY_DIR/$l"
    lappend libList $lib
}

makeCxxDeps $CXX_FILES $BIN_DIR
makeCDeps   $C_FILES   $BIN_DIR
makeCxxObjs $CXX_FILES $BIN_DIR $BIN_DIR
makeCObjs   $C_FILES   $BIN_DIR $BIN_DIR
makeElf     $PROJECT   [concat $CXX_FILES $C_FILES] $libList $BIN_DIR
makeHex     $PROJECT   $BIN_DIR
makeEep     $PROJECT   $BIN_DIR

if { $EXEC == 0 } {
    puts "UP TO DATE"
}


