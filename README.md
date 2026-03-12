# Word Calculator OSGi

This project implements a simple desktop application using:

- Java 21
- OSGi (Equinox)
- Eclipse Plug-in Development Environment (PDE)
- Swing

The application performs arithmetic operations on numbers written in words.

Example:

iki yüz bir + on bir = iki yüz on iki

## Architecture:

The system consists of two OSGi services:

1) Number Converter Service
   Converts numbers between word and integer representation.

2) Calculator UI Service
   Provides the Swing user interface and arithmetic operations.

The application can be run directly from Eclipse using "Run As -> Eclipse Application".

## Language Configuration

The default language of the application is Turkish.

If you want to run the program in English, you must provide the following VM Arguments to the application:
```
-Duser.language=en
-Duser.country=US
```
You can run the program in English by setting these VM arguments in the Run Configuration within Eclipse.
