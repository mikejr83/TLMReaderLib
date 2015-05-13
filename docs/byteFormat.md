# TLM Byte Format

The following is taken from the first post of this [RCGroups thread](http://www.rcgroups.com/forums/showthread.php?t=1725173). This page currently reflects the data in the [first post](http://www.rcgroups.com/forums/showpost.php?p=22631966&postcount=1) and is just a copy and paste job at the moment. There is a large amount of data in this thread dealing with the decoding of this file. This page combine all the knowledge shared by posters in the thread into one set of rules for decoding the TLM file.

**Contributors**
Without these individuals this information would not be possible. A tremendous thanks goes to each of them.

* [KimDK](http://www.rcgroups.com/forums/member.php?u=432295) - OP of the RCGroups thread and has provided much of the legwork decoding the TLM file.
* [AndyKunz](http://www.rcgroups.com/forums/member.php?u=5584) - Spektrum Dev member. Represents a great liaison between the community and Spektrum. *Not necessarily a contributor, but provides as much help as he is allowed.* 

*Note: The following has not yet been formatted for markdown. Please view the raw file or RCGroups thread until this can be formatted correctly.*

Data block start with:
1 Time stamp Low byte
2 Time stamp
3 Time stamp
4 Time stamp high byte
5 Data type 00,16,17,7E,7F 
6 ? 


If Time stamp = FFFFFFFF then it is a header 
Length of is 36 Byte
If 5 and 6 is equal then it is header Data
If 6 is 00 then it is name info



Then it repreat a 20 byte block:

1 Time stamp Low byte
2 Time stamp
3 Time stamp
4 Time stamp high byte
5 Data type 00,16,17,7E,7F 

Data type = 00
?

Dat type = 16 
1 serial number
2 serial number
3 serial number
4 always 00
5 16 (Data type)
6 always 00
7 not identified is low byte of next (decimal)
8 not identified is high byte of previous (decimal)
9 1/100th of a degree second latitude (decimal)
10 degree seconds latitude (decimal)
11 degree minutes latitude (decimal)
12 degrees latitude (decimal)
13 1/100th of a degree second longitude (decimal)
14 degree seconds longitude (decimal)
15 degree minutes longitude (decimal)
16 degrees longitude (decimal)
17 1/10th of degree heading (decimal)
18 degrees heading first two numbers (decimal)
19 always 08
20 always 3B

so to get decimal degrees calculate:

latitude = {12} + {11}/60 + ( {10} + {9}/100)/3600
longitude = {16}+ {15}/60 + ({14} + {13}/100)/3600
heading ={18}*10 +{17}/10

Offset to google maps seems to be 
lat= lat-0,003655 lon= lon-0,004572 



Data type = 17
6 ?
7 Speed High
8 Speed Low
9 ? May be max speed High
10 ? May be max speed Low
11 ?
12 ?
13 ?
14 ?
15 ?
16 ?
17 ?
18 ?
19 ?
20 ?

Data type = 18
6 ?
7 Altitude High
8 Altitude Low the format is in intiger and is in 0.1m
9 ? 
10 ?
11 ?
12 ?
13 ?
14 ?
15 ?
16 ?
17 ?
18 ?
19 ?
20 ?

Data type = 20
6 ?
7 x High 
8 x Low
9 y High
10 y Low
11 z High
12 z Low
13 x max High 
14 x max Low 
15 y max High 
16 y max Low 
17 z max High 
18 z max Low 
19 z min High
20 z min Low

Data type = 126
6 ?
7 RPM High
8 RPM Low RPM = (RPM low + (RPM High * 256)) * count of Poles
9 Volt High 
10 Volt Low V = (Volt low + (Volt High * 256)) / 100
11 Temp High
12 Temp Low F = Temp low + (Temp high * 256) C = ((Temp low + (Temp high * 256)) - 32) / 1,8
13 ?
14 ?
15 ?
16 ?
17 ?
18 ?
19 ?
20 ?

Data type = 127
6 ?
7 A High 
8 A Low 
9 B High
10 B Low
11 L High 
12 L Low 
13 R High
14 R Low
15 Frame loss High 
16 Frame loss Low 
17 Holds High
18 Holds Low
19 Tranciver Volt High
20 Tranciver Volt Low V = (Tranciver Volt Low + (Tranciver Volt High * 256)) / 100

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

