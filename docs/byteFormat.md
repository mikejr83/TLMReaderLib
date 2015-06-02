# TLM Byte Format

The following is taken from the first post of this [RCGroups thread](http://www.rcgroups.com/forums/showthread.php?t=1725173). This page currently reflects the data in the [first post](http://www.rcgroups.com/forums/showpost.php?p=22631966&postcount=1) and [a follow up post by RevinKevin](http://www.rcgroups.com/forums/showpost.php?p=23133050&postcount=86). There is a large amount of data in this thread dealing with the decoding of this file. This page combine all the knowledge shared by posters in the thread into one set of rules for decoding the TLM file.

**Contributors**

Without these individuals this information would not be possible. A tremendous thanks goes to each of them.
* [KimDK](http://www.rcgroups.com/forums/member.php?u=432295) - OP of the RCGroups thread and has provided much of the legwork decoding the TLM file.
* RevinKevin - Contributor on KimDK's thread. Provided a clearer picture of the byte structure and added information about the header blocks.
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

## Data Structure

The data has the following structure:

| Count       | Description |
| ----------- | ----------- | 
|One          | Main Header Block |
|One or more  | Supplemental Header Block (Last Supplemental Header Block has "Sensor type enabled" = 0x17) |
|Zero or more | Data block |

This structure is repeated each time telemetry recording is started. If the recording is just triggered a short time, it is possible that there are no data blocks.

## Header Blocks

If timestamp, the first four bytes are `FF FF FF FF` then it is a header block.

The length of any header block is 36 bytes.

### Main Header Block
This is the name and basic information header block.

| Byte Offset | Description |
| ----------- | ----------- | 
| 0x0         | 0xFF |
| 0x1         | 0xFF |
| 0x2         | 0xFF |
| 0x3         | 0xFF |
| 0x4         | Model Number (Value + 1) |
| 0x5         | Model Type |
| 0x6         | Bind Info  |
| 0x7         | Unknown    |
| 0x8         | Unknown    |
| 0x9         | Unknown    |
| 0x0A        | Unknown    |
| 0x0B        | Unknown    |
| 0x0C - 0x16 | Model Name terminated by 00 |
| 0x17 - 0x23 | Not used (But contains data left in the buffer) |

Model Type:

| Value | Description |
| ----------- | ----------- | 
| 0x0         | Fixed Wing |
| 0x1         | Helicopter |
| 0x2         | Glider |

Bind Info:

| Value | Description |
| ----------- | ----------- | 
| 0x1         | DSM2 6000 |
| 0x2         | DSM2 8000 RX |
| 0x3         | DSMX 8000 RX |
| 0x4         | DMSX 6000 RX |

### Supplemental Header Block
The header name block is then followed by up to 7 (Shure?) entries depending on what options are selected in the telmetry set-up section of the transmitter.

| Byte Offset | Description |
| ----------- | ----------- | 
| 0x0         | 0xFF |
| 0x1         | 0xFF |
| 0x2         | 0xFF |
| 0x3         | 0xFF |
| 0x4 - 0x5   | Sensor type enabled |
| 0x7 - 0x16  | Contain telmetry setup information, e.g. options are enabled/disabled, alarms, min/max |
| 0x17 - 0x23 | Not used (But contains data left in the buffer) |

Sensor Type Enabled Info:

| Value | Description |
| ----------- | ----------- | 
| 0x1, 0x01   | Volts Sensor |
| 0x2, 0x02   | Temp Sensor |
| 0x3, 0x03   | Amps Sensor |
| 0x0A, 0x0A  | PowerBox |
| 0x11, 0x11  | Speed Sensor |
| 0x12, 0x12  | Altimeter |
| 0x14, 0x14  | GForce |
| 0x15, 0x15  | JetCat (DX18 and DX10t only) |
| 0x16, 0x16  | GPS |
| 0x17, 0x17  | Identification for last Supplemental Header Block |
| 0x7E, 0x7E  | RPM |

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

*The values for the Volts and Temperature are always present and do not have to be enabled on the Transmitter. 
It is also suspected that this is true of the RPM and the current sensors.*

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
