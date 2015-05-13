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