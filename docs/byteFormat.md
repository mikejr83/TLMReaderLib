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
The header name block can then be followed by additional/supplemental header entries depending on what options are selected in the telmetry set-up section of the transmitter. 

*Note: currently it is thought that the number of supplemental header blocks match the number of sensors enabled within the transmitter. As this may change with future releases of AirWave it is best to parse these blocks and look for the value 0x17 in offsets 0x4 and 0x5. This denotes the last header block for the sequence.*

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

After the header blocks data repreats in a 20 byte block:

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

The following addresses for offset 0x4 are reserved: 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A

### Voltage (Legacy)

Data type value: 0x00
?

### Temperature (Legacy)

Data type value: 0x01

### Power Box

Data type value: 0x0A.

Voltages are measured in 0.01v increments. Capacity is in units of 1mAh.

| Byte Offset | Description |
| ----------- | ----------- | 
| 0x0         | Timestamp low byte |
| 0x1         | Timestamp |
| 0x2         | Timestamp |
| 0x3         | Timestamp high byte |
| 0x4         | Data type |
| 0x5         | ?? |
| 0x6         | Volt 1 (High Byte) |
| 0x7         | Volt 1 (Low Byte) |
| 0x8         | Volt 2 (High Byte) | 
| 0x9         | Volt 2 (Low Byte) |
| 0x0A        | Capacity 1 (High Byte) |
| 0x0B        | Capacity 1 (Low Byte) |
| 0x0C        | Capacity 2 (High Byte) |
| 0x0D        | Capacity 2 (Low Byte) |
| 0x0E        | Spare 16 - 1 (High Byte) |
| 0x0F        | Spare 16 - 1 (Low Byte) |
| 0x10        | Spare 16 - 2 (High Byte) |
| 0x11        | Spare 16 - 2 (Low Byte) |
| 0x12        | Spare |
| 0x13        | Alarms |

#### Power Box Alarms

| Byte Offset | Description |
| ----------- | ----------- | 
| 0x1         | Voltage 1 |
| 0x2         | Voltage 2 |
| 0x4         | Capacity 1 |
| 0x8         | Capacity 2 |
| 0x10        | RPM - Not used? |
| 0x20        | Temperature - Not Used? |
| 0x40        | Reserved |
| 0x80        | Reserved |

### Airspeed

Data type value: 0x11.

Airseepd measured in 1km/h increments.

| Byte Offset | Description |
| ----------- | ----------- | 
| 0x0         | Timestamp low byte |
| 0x1         | Timestamp |
| 0x2         | Timestamp |
| 0x3         | Timestamp high byte |
| 0x4         | Data type |
| 0x5         | ?? |
| 0x6         | Airspeed (High Byte) |
| 0x7         | Airspeed (Low Byte) |
| 0x8         | Max Airspeed (High Byte) | 
| 0x9         | Max Airspeed (Low Byte) |
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

### Altitude

Data type value: 0x12.

Altitude in 0.1 meter increments.

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
| 0x8         | Altitude Max (High Byte) | 
| 0x9         | Altitude Max (Low Byte) |
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

Data type value: 0x14.

Force is reported in 0.01G increments. The data type for these measurements are a 16-bit **signed** integer. Range is +/- 4000 (+/- 40G) in pro models. Range is +/- 800 (+/- 8G) in standard models. The max gforce for for the x-axis is the absolute value for fore/aft. The max gforce for the y-axis is the absolute value for left/right. The value for the z-axis is the wing spar load.

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

### JetCat

Data type value: 0x15.

**NOTE:** The 16-bit BCD values are assumed to be in the correct order in regards to the whole number and decimal portions (see the bolded portion). The byte-offset for the values is known to be correct. Since the writer does not have a JetCat unit for testing those positions for whole and decimal values will be assumed until confirmation is provided. 

| Byte Offset | Description |
| ----------- | ----------- |
| 0x0         | Timestamp low byte |
| 0x1         | Timestamp |
| 0x2         | Timestamp |
| 0x3         | Timestamp high byte |
| 0x4         | Data type |
| 0x5         | ?? |
| 0x6         | ECU Status - See table below. |
| 0x7         | Throttle (BCD) xx Percent |
| 0x8         | Pack Voltage (BCD) **xx**.yy | 
| 0x9         | Pack Voltage (BCD) xx.**yy** |
| 0x0A        | Pump Voltage (BCD) **xx**.yy |
| 0x0B        | Pump Voltage (BCD) xx.**yy** |
| 0x0C        | RPM (BCD) (High Byte) |
| 0x0D        | RPM (BCD) |
| 0x0E        | RPM (BCD) |
| 0x0F        | RPM (BCD) (Low Byte) |
| 0x10        | EGT in Celsius (BCD) (High Byte) |
| 0x11        | EGT in Celsius (BCD) (Low Byte) |
| 0x12        | Off Condition (BCD) - See table below. |
| 0x13        | Spare |

#### ECU Status

##### JetCat

| Value | Description |
| ----------- | ----------- |
| 0x00 | Off |
| 0x01 | Wait for RPM |
| 0x02 | Ignite |
| 0x03 | Accelerate |
| 0x04 | Stabilize |
| 0x05 | Learn - Hi |
| 0x06 | Learn - Low |
| 0x07 | Undefined |
| 0x08 | Slow down |
| 0x09 | Manual |
| 0x10 | Auto Off |
| 0x11 | Run |
| 0x12 | Acceleration Delay |
| 0x13 | Speed Reg. (Speed Control) |
| 0x14 | Two Shaft Regualte (only for secondary shaft) |
| 0x15 | Pre-heat 1 |
| 0x16 | Pre-heat 2 |
| 0x17 | Main F Start |
| 0x18 | Not Used |
| 0x19 | Kero Fule On |

*Undefined states from 0x1A to 0x1F*

##### EvoJet

| Value | Description |
| ----------- | ----------- |
| 0x20 | Off |
| 0x21 | Ignite |
| 0x22 | Accelerate |
| 0x23 | Run |
| 0x24 | Cal |
| 0x25 | Cool |
| 0x26 | Fire |
| 0x27 | Glow |
| 0x28 | Heat |
| 0x29 | Idle |
| 0x2A | Lock |
| 0x2B | Rel |
| 0x2C | Spin |
| 0x2D | Stop |

*Undefined states from 0x2E to 0x2F

##### Hornet

| Value | Description |
| ----------- | ----------- |
| 0x30 | Off |
| 0x31 | Slow down |
| 0x32 | Cool down |
| 0x33 | Auto |
| 0x34 | Auto HC |
| 0x35 | Burner On |
| 0x36 | Cal Idle |
| 0x37 | Calibrate |
| 0x38 | Dev Delay |
| 0x39 | Emergency |
| 0x3A | Fuel Heat |
| 0x3B | Fuel Ignite  |
| 0x3C | Go Idle |
| 0x3D | Prop Ignite |
| 0x3E | Ramp Delay |
| 0x3F | Ramp Up |
| 0x40 | Standby |
| 0x41 | Steady |
| 0x42 | Wait Acc |
| 0x43 | Error |

*Undefined states from 0x44 to 0x4F

##### XICOY

| Value | Description |
| ----------- | ----------- |
| 0x50 | Temperature High |
| 0x51 | Trim Low |
| 0x52 | Set Idle |
| 0x53 | Ready |
| 0x54 | Ignition |
| 0x55 | Fuel Ramp |
| 0x56 | Glow Test |
| 0x57 | Running |
| 0x58 | Stop |
| 0x59 | Flameout |
| 0x5A | Speed Low |
| 0x5B | Cooling  |
| 0x5C | Igniter Bad |
| 0x5D | Starter F |
| 0x5E | Weak Fuel |
| 0x5F | Start On |
| 0x60 | Pre-heat |
| 0x61 | Battery |
| 0x62 | Timeout |
| 0x63 | Overload |
| 0x64 | Igniter Fail |
| 0x65 | Burner On |
| 0x66 | Starting |
| 0x67 | Switch Over |
| 0x68 | Cal Pump |
| 0x69 | Pump Limit |
| 0x6A | No Engine |
| 0x6B | Power Boost |
| 0x6C | Run Idle |
| 0x6D | Run Max |

*ECU Max State: 0x74*

#### Off Condition

##### JetCat

Only valid when the ECU status is 0x00 (Off). Enumeration is defined as starting 0. All other values are assumed to increment by 1.

| Value | Description |
| ----------- | ----------- |
| 0x01 | No Off Condition Defined |
| 0x02 | Shutdown via RC |
| 0x03 | Over temperature |
| 0x04 | Ignition Timeout |
| 0x05 | Acceleration Timeout |
| 0x06 | Acceleration Too Slow |
| 0x07 | Over RPM |
| 0x08 | Low RPM Off |
| 0x09 | Low Battery |
| 0x0A | Auto Off |
| 0x0B | Low Temperature Off |
| 0x0C | Hi Temp Off |
| 0x0D | Glow Plug Defective |
| 0x0E | Watch Dog Timer |
| 0x0F | Fail Safe Off |
| 0x10 | Manual Off (via GSU) |
| 0x11 | Power Fail (Battery fail) |
| 0x12 | Temp Sensor Fail (only during startup) |
| 0x13 | Fuel Fail |
| 0x14 | Prop Fail |
| 0x15 | 2nd Engine Fail |
| 0x16 | 2nd Engine Diff Too High |
| 0x17 | 2nd Engine No Comm |
| 0x18 | Max Off Condition |

### GPS - Second Block (Location Data)

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
