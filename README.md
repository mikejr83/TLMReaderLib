# TLMReaderLib 

[![Build Status](https://travis-ci.org/mikejr83/TLMReaderLib.svg)](https://travis-ci.org/mikejr83/TLMReaderLib)

Turn Spektrum TLM telemetry files into usable data for your Java application.

## Spektrum TLM File Reader/Decoder Library

This project is designed to provide a Java library to read TLM byte data output by current Spektrum transmitters (DX8 and DX18) into a usable format. [Please see this RC Groups thread for more information](http://www.rcgroups.com/forums/showthread.php?t=1725173).

Updates coming soon!

## Documentation

As the project grows the hope is that the documentation will too. Please [see the documentation page](docs/README.md) for more information.

## Compatibility

At the moment I am without a Spektrum telemetry system to generate my own TLM files from the latest Airwave firmware. This library will rely on users providing feedback and TLM files for tests to be run. It is the hope that Spektrum will not be changing the format of the TLM file but since this project is completely unofficial there is no guarantee that the library will be compatible with future version of Airwave, future versions of Spektrum transmitters, or support any changes at all!

If you are using this library in your application and encounter an issue processing a TLM file please reach out and communicate with me on getting your issue resolved or enhancement implemented. The best way to contact us through the RCGroups thread.

## Prereqs

To begin working with the code you'll need to have at least JDK 8 installed and Maven 3.5.2

Insure that `JAVA_HOME` is set to your JDK folder and that the `bin` path for Maven is added to your `PATH` variable.

## Building

You can build this with Maven:

````mvn compile````

Package up everything with:

````mvn package````
