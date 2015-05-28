# TLM Byte Format

The following is taken from the first post of this [RCGroups thread](http://www.rcgroups.com/forums/showthread.php?t=1725173). This page currently reflects the data in the [first post](http://www.rcgroups.com/forums/showpost.php?p=22631966&postcount=1) and is just a copy and paste job at the moment. There is a large amount of data in this thread dealing with the decoding of this file. This page combine all the knowledge shared by posters in the thread into one set of rules for decoding the TLM file.

**Contributors**

Without these individuals this information would not be possible. A tremendous thanks goes to each of them.
* [KimDK](http://www.rcgroups.com/forums/member.php?u=432295) - OP of the RCGroups thread and has provided much of the legwork decoding the TLM file.
* [AndyKunz](http://www.rcgroups.com/forums/member.php?u=5584) - Spektrum Dev member. Represents a great liaison between the community and Spektrum. *Not necessarily a contributor, but provides as much help as he is allowed. *

## Basic Block Structure

All blocks start with:

| Byte Offset | Description |
| ----------- | ----------- | 
| 0x0         | Timestamp low byte |
| 0x1         | Timestamp |
| 0x2         | Timestamp |
| 0x3         | Timestamp high byte |
| 0x4         | Data type (00,16,17,7E,7F - appears to be the sensor "address") |
| 0x5         | ?? |

The first four bytes will **always** be timestamp data.

## Header Blocks

If timestamp, the first four bytes are `FF FF FF FF` then it is a header block.

The length of any header block is 36 bytes.

### Header Data vs. Header Name Info
If 0x04 and 0x05 are equal then it is header data. If 0x05 is 00 then it is name info.

The first header block has the model name in it. The name starts at offset `0x0C`. I'm not sure of the length. I also believe that the header rows contain information about the model, transmitter, and receiver. I do not know how to decode these yet.

*Note: I'm unsure of the above as I'm not really understanding what the data is telling me when compared to the original thread.*

## Data Blocks

Then it repreat a 20 byte block:

| Byte Offset | Description |
| ----------- | ----------- | 
| 0x0         | Timestamp low byte |
| 0x1         | Timestamp |
| 0x2         | Timestamp |
| 0x3         | Timestamp high byte |
| 0x4         | Data type (00,16,17,7E,7F - appears to be the sensor "address") |
| 0x5         | ?? |

### Voltage (Legacy)

Data type value: 0x00
?

### Temperature (Legacy)

Data type value: 0x01

### Altitude

Data type value: 0x12

| Byte Offset | Description |
| ----------- | ----------- | 
| 0x0         | Timestamp low byte |
| 0x1         | Timestamp |
| 0x2         | Timestamp |
| 0x3         | Timestamp high byte |
| 0x4         | Data type |
| 0x5         | ?? |
| 0x6         | Altitude (High Byte) |
| 0x7         | Altitude (Low Byte) |
| 0x8         | ?? | 
| 0x9         | ?? |
| 0x0A        | ?? |
| 0x0B        | ?? |
| 0x0C        | ?? |
| 0x0D        | ?? |
| 0x0E        | ?? |
| 0x0F        | ?? |
| 0x10        | ?? |
| 0x11        | ?? |
| 0x12        | ?? |
| 0x13        | ?? |

The format is in integer and is in 0.1m increments. 1004 = 100.4 meters.

### G-Force

Data type value: 0x14

| Byte Offset | Description |
| ----------- | ----------- | 
| 0x0         | Timestamp low byte |
| 0x1         | Timestamp |
| 0x2         | Timestamp |
| 0x3         | Timestamp high byte |
| 0x4         | Data type |
| 0x5         | ?? |
| 0x6         | X (High Byte) |
| 0x7         | X (Low Byte) |
| 0x8         | Y (High Byte) | 
| 0x9         | Y (Low Byte) |
| 0x0A        | Z (High Byte) |
| 0x0B        | Z (Low Byte) |
| 0x0C        | X-max (High Byte) |
| 0x0D        | X-max (Low Byte) |
| 0x0E        | Y-max (High Byte) |
| 0x0F        | Y-max (Low Byte) |
| 0x10        | Z-max (High Byte) |
| 0x11        | Z-max (Low Byte) |
| 0x12        | Z-min (High Byte) |
| 0x13        | Z-min (Low Byte) |

### GPS - Second Block

Data type value: 0x16.

This will be the second GPS block.

| Byte Offset | Description |
| ----------- | ----------- |
| 0x0         | serial number |
| 0x1         | serial number |
| 0x2         | serial number |
| 0x3         | always 00 |
| 0x4         | 0x16 (Data type) |
| 0x5         | always 00 |
| 0x6         | not identified is low byte of next (decimal) |
| 0x7         | not identified is high byte of previous (decimal)
| 0x8         | 1/100th of a degree second latitude (decimal) |
| 0x9         | degree seconds latitude (decimal) |
| 0x0A        | degree minutes latitude (decimal) |
| 0x0B        | degrees latitude (decimal) |
| 0x0C        | 1/100th of a degree second longitude (decimal) |
| 0x0D        | degree seconds longitude (decimal) |
| 0x0E        | degree minutes longitude (decimal) |
| 0x0F        | degrees longitude (decimal) |
| 0x10        | 1/10th of degree heading (decimal)|
| 0x11        | degrees heading first two numbers (decimal) |
| 0x12        | always 08 |
| 0x13        | always 3B |

so to get decimal degrees calculate:

latitude = {12} + {11}/60 + ( {10} + {9}/100)/3600
longitude = {16}+ {15}/60 + ({14} + {13}/100)/3600
heading ={18}*10 +{17}/10

Offset to google maps seems to be 
lat= lat-0,003655 lon= lon-0,004572 

### GPS - First Block

Data type value: 0x17

This will be the first GPS block.

| Byte Offset | Description |
| ----------- | ----------- |
| 0x0         | serial number |
| 0x1         | serial number |
| 0x2         | serial number |
| 0x3         | always 00 |
| 0x4         | 0x16 (Data type) |
| 0x5         | ?|
| 0x6         | Speed (High Byte) |
| 0x7         | Speed (Low Byte) |
| 0x8         | ? May be max speed (High Byte) |
| 0x9         | ? May be max speed (Low Byte) |
| 0x0A        | ?? |
| 0x0B        | ?? |
| 0x0C        | ?? |
| 0x0D        | ?? |
| 0x0E        | ?? |
| 0x0F        | ?? |
| 0x10        | ?? |
| 0x11        | ?? |
| 0x12        | ?? |
| 0x13        | ?? |

### Standard Telemetry

Data type value: 0x7E

| Byte Offset | Description |
| ----------- | ----------- | 
| 0x0         | Timestamp low byte |
| 0x1         | Timestamp |
| 0x2         | Timestamp |
| 0x3         | Timestamp high byte |
| 0x4         | Data type |
| 0x5         | ?? |
| 0x6         | RPM (High Byte) |
| 0x7         | RPM (Low Byte) |
| 0x8         | Volt (High Byte) | 
| 0x9         | Volt (Low Byte) |
| 0x0A        | Temp (High Byte) |
| 0x0B        | Temp (Low Byte) |
| 0x0C        | ?? |
| 0x0D        | ?? |
| 0x0E        | ?? |
| 0x0F        | ?? |
| 0x10        | ?? |
| 0x11        | ?? |
| 0x12        | ?? |
| 0x13        | ?? |

RPM = Value from sensor * count of poles.

Volts = Value from sensor / 100. 489 = 4.89V

Temperature is value measured in Celcius.

### RX Telemetry

Data type value: 0x7F

| Byte Offset | Description |
| ----------- | ----------- | 
| 0x0         | Timestamp low byte |
| 0x1         | Timestamp |
| 0x2         | Timestamp |
| 0x3         | Timestamp high byte |
| 0x4         | Data type |
| 0x5         | ?? |
| 0x6         | A (High Byte) |
| 0x7         | A (Low Byte) |
| 0x8         | B (High Byte) | 
| 0x9         | B (Low Byte) |
| 0x0A        | L (High Byte) |
| 0x0B        | L (Low Byte) |
| 0x0C        | R (High Byte) |
| 0x0D        | R (Low Byte) |
| 0x0E        | Frame loss (High Byte) |
| 0x0F        | Frame loss (Low Byte) |
| 0x10        | Holds (High Byte) |
| 0x11        | Holds (Low Byte) |
| 0x12        | Telemetry Unit's registered voltage (High Byte) |
| 0x13        | Telemetry Unit's registered voltage (Low Byte) |

Volts = Value from sensor / 100. 489 = 4.89V

# Spektrum DX8 and DX18 SD log file format - update by RevinKevin

[](http://www.rcgroups.com/forums/showpost.php?p=23133050&postcount=86) 

I thought it worth an update on the Format of the SPEKTRUM.TLM file:

All values in brackets are in Hex
Square Brackets [] are referenced to zero for Engineers like me.
I have also included the format of the data (Hex or Decimal) Hex values have to be converted to Decimal for any of the formulas to work.


There are two distinct parts of the File
The first part is information gathered from the Transmitter.
The Second part is information gathered from the Telemetry Unit

First Part TX Block
Information gathered from the Transmitter.

TX block starts with:

1 [00] 255(FF)
2 [01] 255(FF)
3 [02] 255(FF)
4 [03] 255(FF)
5 [04] Model Number (Value + 1) (hex)
6 [05] Model Type 00 = Fixed Wing, 01 = Helicopter
7 [06] BIND info 01=DSM2 6000 RX, 02=DSM2 8000 RX, 03=DSMX 8000 RX, 04=DMSX 6000 RX 
8 [07] Unknown 
9 [08] Unknown 
10[09] Unknown 
11[0A] Unknown
12[0B] Unknown
13[0C] to 23[16] Model Name terminated by 00
24[17] to 36[23] Not Used (But contains Data left in buffer)

This is followed by up to 7 Entry's depending on what options are selected in the Telemetry set-up section of the transmitter.

1[00] 255(FF)
2[01] 255(FF)
3[02] 255(FF)
4[03] 255(FF)
5[04] to 6[05] 01,01(0101) Volts Sensor Enabled
02,02(0202) Temp Sensor Enabled
03,03(0303) Amps Sensor Enabled
10,10(0A0A) PBox Enabled
17,17(1111) Speed Sensor Enabled
18,18(1212) Altimeter Sensor Enabled
20,20(1414) GForce Sensor Enabled
21,21(1515) Jetpac Enabled (option only on the DX18 and DX10t)
22,22(1616) GPS Sensor Enabled
124,124(7E7E) RPM Sensor Enable
7[06] to 23[16] Contain Telemetry set-up information i.e. if options are enabled or inhibited, alarms set vibrate and/or Tone plus Max and Min Values.
24[17] to 36[23] Not used (But contains Data left in buffer)


Last TX Entry

1[00] 255(FF)
2[01] 255(FF)
3[02] 255(FF) 
4[03] 255(FF)
5[04] 23(17)
6[05] 23(17)
7[06] to 23[16] Unknown
24[17] to 36[23] Not used (But contains Data left in buffer)

If the Transmitter detects the Telemetry unit the Sensor Blocks are shown if not we go back to the beginning and the sequence is repeated. 


The Second part is information gathered from the Telemetry Unit

Sensor Block Format

There are 6 to 8 Sensor formats 
Type 17(11),18(12),20(14),22(16)+23(17),124(7E) and 125(7F) 
There should be a Type 10 (0A) No Information is known at this time

Type 124(7E) and 125(7F) are always present but the others depend on the sensors being enabled and the Data Blocks present.

The Values for the Volts and Temperature are always present and do not have to be enabled on the Transmitter. 
I also suspect that this is true of the RPM and the current sensors.


Data type = 17(11) Speed Sensor

1[00] Time Stamp LSB
2[01] Time Stamp
3[02] Time Stamp 
4[03] Time Stamp MSB
5[04] 17(11)
6[05] 00
7[06] Speed MSB (Hex)
8[07] Speed LSB (Hex)
9[08] Unknown 
10[09] Unknown 
11[0A] Unknown
12[0B] Unknown
13[0C] Unknown
14[0D] Unknown
15[0E] Unknown
16[0F] Unknown
17[10] Unknown
18[11] Unknown
19[12] Unknown
20[13] Unknown

Data type = 18(12) Altimeter Sensor

1[00] Time Stamp LSB
2[01] Time Stamp
3[02] Time Stamp 
4[03] Time Stamp MSB
5[04] 18(12)
6[05] 00
7[06] Altitude MSB (Hex)
8[07] Altitude LSB (Hex) the format is an integer and is in 0.1m
9[08] Unknown
10[09] Unknown
11[0A] Unknown
12[0B] Unknown
13[0C] Unknown
14[0D] Unknown
15[0E] Unknown
16[0F] Unknown
17[10] Unknown
18[11] Unknown
19[12] Unknown
20[13] Unknown


Data type = 20(14) Gforce Sensor

1[00] Time Stamp LSB
2[01] Time Stamp
3[02] Time Stamp 
4[03] Time Stamp MSB
5[04] 20(14)
6[05] 00
7[06] x MSB (Hex)
8[07] x LSB (Hex)
9[08] y MSB (Hex)
10[09] y LSB (Hex)
11[0A] z MSB (Hex)
12[0B] z LSB (Hex)
13[0C] x max MSB (Hex)
14[0D] x max LSB (Hex)
15[0E] y max MSB (Hex)
16[0F] y max LSB (Hex)
17[10] z max MSB (Hex)
18[11] z max LSB (Hex)
19[12] z min MSB (Hex)
20[13] z min LSB (Hex)


Data Type = 22(16) GPS Sensor

1[00] Time Stamp LSB
2[01] Time Stamp
3[02] Time Stamp 
4[03] Time Stamp MSB
5[04] 22(16)
6[05] 00
7[06] Altitude LSB
8[07] Altitude MSB
9[08] 1/100th of a degree second latitude (Decimal)
10[09] degree seconds latitude (Decimal)
11[0A] degree minutes latitude (Decimal)
12[0B] degrees latitude (Decimal)
13[0C] 1/100th of a degree second longitude (Decimal)
14[0D] degree seconds longitude (Decimal)
15[0E] degree minutes longitude (Decimal)
16[0F] degrees longitude (Decimal)
17[10] Heading LSB (Decimal)
18[11] Heading MSB (Decimal) Divide by 10 for Degrees 
19[12] Unknown
20[13] Unknown

Data type 23(17) GPS Sensor

1[00] Time Stamp LSB
2[01] Time Stamp
3[02] Time Stamp 
4[03] Time Stamp MSB
5[04] 23(17)
6[05] 0
7[06] Speed LSB (Decimal)
8[07] Speed MSB (Decimal) Divide by 10 for Knots. Multiply by 0.185 for Kph and 0.115 for Mph
9[08] UTC Time LSB 1/10th sec.
10[09] UTC Time
11[0A] UTC Time
12[0B] UTC Time MSB
13[0C] Number of Sats (Decimal)
14[0D] 00
15[0E]-20[13] Unused (But contains Data left in buffer)


Data type = 126(7E){TM1000} or 254(FE){TM1100}

1[00] Time Stamp LSB
2[01] Time Stamp
3[02] Time Stamp 
4[03] Time Stamp MSB
5[04] 126(7E) or 254(FE)
6[05] 00
7[06] RPM MSB (Hex)
8[07] RPM LSB (Hex) RPM = Value (Decimal) * count of Poles
9[08] Volt MSB (Hex)
10[09] Volt LSB (Hex) V = Value (Decimal) / 100
11[0A] Temp MSB (Hex)
12[0B] Temp LSB (Hex) Value (Decimal) is in Fahrenheit, for Celsius ((Value (Decimal) - 32)*5) / 9
13[0c] Unknown
14[0D] Unknown
15[0E] Unknown
16[0F] Unknown
17[10] Unknown
18[11] Unknown
19[12] Unknown
20[13] Unknown

Data type = 127(7F){TM1000} or 255(FF){TM1100}
1[00] Time Stamp LSB
2[01] Time Stamp
3[02] Time Stamp 
4[03] Time Stamp MSB
5[04] 127(7F) or 255(FF)
6[05] 00 Unknown
7[06] A MSB (Hex)
8[07] A LSB (Hex)
9[08] B MSB (Hex)
10[09] B LSB (Hex)
11[0A] L MSB (Hex)
12[0B] L LSB (Hex)
13[0C] R MSB (Hex)
14[0D] R LSB (Hex)
15[0E] Frame loss MSB (Hex)
16[0F] Frame loss LSB (Hex)
17[10] Holds MSB (Hex)
18[11] Holds LSB (Hex)
19[12] Receiver Volts MSB (Hex)
20[13] Receiver Volts LSB (Hex) V = Value (Decimal) / 100

Still a few bits to sort out if anyone wants to carry on.

Andy might be able to post a TLM file of a DX10T with a 9000 Rx (DSM2 and X)

By the way Andy this habit of not Padding out the buffers, I recall Microsoft getting into trouble for showing user information in the buffers. We would not want to see anything created behind your Firewall!!!

Thanks to everyone who contributed towards my final offering

Cheers

Kevin

# Spektrum Telemetry Reverse Engineered

The following data comes from [Spektrum Telemetry reverse engineered, make DIY sensors !](http://www.rcgroups.com/forums/showthread.php?t=1726960) RCGroups thread. User **[Mukenukem](http://www.rcgroups.com/forums/member.php?u=331468)** reverse engineered what the TM1000 was doing in order for people to make custom sensors. This data seems to correspond with the earlier information.

Why did I do this ? Because noone else did and I am curious 
Spektrum unfortunatley makes a "secret" out of their telemetry stuff if you do not sign a NDA. On the other hand, signing a NDA does not help the community. And nowadays there are very able people in the community 

I am a noob at programming microcontrollers, but I am good in finding things out. Because of that I analyzed the Spektrum telemetry protocol to make it easy for talented programmers to make their own sensors. Because it is so easy !

Also, Spektrum seems to have not enough manpower to prodice sensors we all need (used capacity, for example). It's a shame to have a telemetry enabled radio and no sensors (sometimes I get jealous if I look over to the HOTT radios).

So, I took a logic analyzer for the basics and an Arduino to find out the values.

First of all: Spektrum uses an I²C bus, they call it X-Bus. It's frequency is 100kHz. The 4 pins are SDA, SCL, Ubatt, GND, SDA on the antenna side, GND on the 3-connector side. Ubatt is direct voltage from the RX, so make sure you use a 3.3V LDO regulator for your sensor. As for termination, the pullup resistors in microcontrollers are sufficient. Spektrum uses PIC 16F677 without external resistors. 
Around 70ms after the power is stable, the telemetry module enumerates the bus. It reads each address (0x01 up to 0x7D) twice, every 13ms, and if there is a telemetry module, it should answer, otherwise it will not be polled during normal operation. So if you design a sensor, make sure it listens to the I²C quickly. This enumerating takes about 3.4 seconds, after that the TM1000 transmits data and polls the sensors every 94ms. The sensors are I²C slaves. 

Every sensor must transfer 16 bytes data. First byte must be address, second byte must be 0x00, and 14 bytes of data. I will describe the data format later with the various sensors.

And now, please have a look which sensors are currently implemented in the telemetry enabled radios (if you are logging the telemetry data stream, you see ALL sensor adresses !)


Address 0x01: Voltmeter. legacy, DO NOT USE !

The voltage is transferred directly from the TM1000, if you use this sensor address the value flickers on the radio, because the "real" voltage data is transferred via address 7E (which is not polled via I²C).


Address 0x02: Temperature. legacy, DO NOT USE !

Same as above, this seems some legacy stuff and interferes with the internal data.


Address 0x03: Current. Use it ! This sensor came with DX8 2.05, DX18 1.01.

03,00,MSB,LSB,00,00,00,00,00,00,00,00,00,00,00,00
MSB and LSB are a 16bit value, 1 unit is 0.1967A, seems to be some sensor related value. You can display 4 digits, I tried up to 0x2000 (displaying 1612A). Please beware that the maximum alarm value you can set in the radio is 200A.


Address 0a: Powerbox. Very useful !

0a,00,V1MSB,V1LSB,V2MSB,V2LSB,Cap1MSB,Cap1LSB,Cap2 MSB,Cap2LSB,00,00,00,00,00,Alarm
You can display 2 voltages and 2 used capacities. Voltage scale 1 unit is 0.01V, capacity 1 unit is 1mAh. Alarm has to be triggered by the sensor, it is transmitted to the radio with the last byte, the first 4 bits. The fist bit is alarm V1, the second V2, the third Capacity 1, the 4th capacity 2. This sensor is nearly perfect for a used capacity sensor, with the drawback that the alarm value must be set in the sensor . You can enable or disable an alarm for each value, but not set the value.


Address 0x11: Airspeed

11.00.MSB,LSB,01,F9,00,00,00,00,00,00,00,00,00,00
MSB and LSB are a 16Bit value, 1 unit is 1 km/h. Displays up to 65535 km/h, maximum alarm value 563 km/h


Address 0x12: Altitude

12.00.MSB,LSB,FF,00,00,00,00,00,00,00,00,00,00,00
MSB and LSB are a 16Bit signed integer, 1 unit is 0.1m. Displays -3276.8m to 3276.7m, alarm values between -300m and 1000m. 0x7fff is 3276.7m, 0xffff=-0.1m, 0x8000 is -3276.8m

Address 0x14: G-Force

14,00,xMSB,xLSB,yMSB,zMSB,zLSB,xmaxMSB,xmaxLSB,yma xMSB,ymaxLSB,zmaxMSB,zmaxLSB,zminMSB,zminLSB
MSB/LSB are signed integers, unit is 0.01g, on the "normal" display the last digit is rounded. maximum display value is +-99.90 (actually 99.99, but when it is rounded, it overflows to 100 and displays ----)
The min/max values are retained in the sensor, because g-force cah change too quickly for the radio. Therefore you cannot clear the min/max values on the radio. Alarm can be set for Z-axis from -40g to 40g


Address 0x15: JetCat. Useful, but no alarms, display only. ONLY AVAILABLE ON THE DX9, DX10t, DX18t, DX18 (QQ) !

15,00,status,throttle,PackVoltHi,PackVoltLo,PumpVo ltHi,PumpVoltLo,RPMLo,RPMMid,RPMHi,00,TempEGTLo,Te mpEGTHi,OffCondition,00
JetCat is a display only value especially for turbines, but has some interesting fields which can be used otherwise. You can display 2 voltages, throttle, RPM and 2 status messages. There are some differences to all other sensors, JetCat uses BCD values. Only 0-9, a-f is unused. Voltages range to 99.99V, RPM to 999999, throttle up to 159% (the upper nibble is 0-f, the lower nobble 0-9), a little bit strange. EGT temperature uses only the lower nibble of the MSB, so maximum displayable value is 999°.

Possible messages for status:
0x00:OFF
0x01:WAIT FOR RPM
0x02:IGNITE
0x03;ACCELERATE
0x04:STABILIZE
0x05:LEARN HIGH
0x06:LEARN LOW
0x07:undef
0x08:SLOW DOWN
0x09:MANUAL
0x0a,0x10:AUTO OFF
0x0b,0x11:RUN
0x0c,0x12:ACCELERATION DELAY
0x0d,0x13:SPEED REG
0x0e,0x14:TWO SHAFT REGULATE
0x0f,0x15:PRE HEAT
0x16:PRE HEAT 2
0x17:MAIN F START
0x18:not used
0x19:KERO FULL ON
0x1a:MAX STATE

Messages for Off Condition:
0x00:NA
0x01:OFF BY RC
0x02:OVER TEMPERATURE
0x03:IGNITION TIMEOUT
0x04:ACCELERATION TIMEOUT
0x05:ACCELERATION TOO SLOW
0x06:OVER RPM
0x07:LOW RPM OFF
0x08:LOW BATTERY
0x09:AUTO OFF
0x0a.0x10:LOW TEMP OFF
0x0b,0x11:HIGH TEMP OFF
0x0c,0x12:GLOW PLUG DEFECTIVE
0x0d,0x13:WATCH DOG TIMER
0x0e,0x14:FAIL SAFE OFF
0x0f,0x15:MANUAL OFF
0x16:POWER BATT FAIL
0x17:TEMP SENSOR FAIL
0x18:FUEL FAIL
0x19:PROP FAIL
0x1a:2nd ENGINE FAIL
0x1b:2nd ENGINE DIFFERENTIAL TOO HIGH
0x1c:2nd ENGINE NO COMMUNICATION
0x1d:MAX OFF CONDITION

Address 0x16: GPS

Edit: To have it all in one article, I have added the GPS, thanks Vlad !

Quote:
Originally Posted by vlad_vy  View Post
Data type = 0x16 GPS Sensor (always second GPS packet)

0[00] 22(0x16)
1[01] 00
2[02] Altitude LSB (Decimal) //In 0.1m
3[03] Altitude MSB (Decimal) //Altitude = Altitude(0x17) * 10000 + Value (in 0.1m)
4[04] 1/100th of a degree second latitude (Decimal) (XX YY.SSSS)
5[05] degree seconds latitude (Decimal)
6[06] degree minutes latitude (Decimal)
7[07] degrees latitude (Decimal)
8[08] 1/100th of a degree second longitude (Decimal) (XX YY.SSSS)
9[09] degree seconds longitude (Decimal)
10[0A] degree minutes longitude (Decimal)
11[0B] degrees longitude (Decimal)
12[0C] Heading LSB (Decimal)
13[0D] Heading MSB (Decimal) Divide by 10 for Degrees
14[0E] Unknown (Decimal)
15[0F] First bit for latitude: 1=N(+), 0=S(-); 
Second bit for longitude: 1=E(+), 0=W(-); 
Third bit for longitude over 99 degrees: 1=+-100 degrees



Data type = 0x17 GPS Sensor (always first GPS packet)

0[00] 23(0x17)
1[01] 00
2[02] Speed LSB (Decimal)
3[03] Speed MSB (Decimal) Divide by 10 for Knots. Multiply by 0.185 for Kph and 0.115 for Mph
4[04] UTC Time LSB (Decimal) 1/100th sec. (HH:MM:SS.SS)
5[05] UTC Time (Decimal) = SS
6[06] UTC Time (Decimal) = MM
7[07] UTC Time MSB (Decimal) = HH
8[08] Number of Sats (Decimal)
9[09] Altitude in 1000m (Decimal)
10[0A]-15[0F] Unused (But contains Data left in buffer)
Address 0x40: Vario

40,00,altLSB,altMSB,250MSB,250LSB,500MSB,500LSB,10 00MSB,1000LSB,1500MSB,1500LSB,2000MSB,2000LSB,3000 MSB,3000LSB

alt is altitude in 0.1m
250, 500, etc is altitude change rate in 0.1m, during the last 250ms, 500ms, 1s, 1.5s, 2s, 3s. So filtering is done in the sensor. If you only have one value, either use only 250ms or transmit it for all rates. If you transmit it for the 250ms rate, you have to set the radio also to 250ms in order to get vario tones.

All altitude variables are Int16



Attached is a picture of the pinout (thanks, Schorsch). 

 

Okay, guys. Now it should be easy to build your own sensors. I just have one wish: If you build a sensor, please make the circuit and software public, so anyone can copy them. 

A guy at rc-heli.de (Schorsch) is currently working on an used capacity sensor. He told me that he will make it public when it's done.

Have fun !

