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

## Running the Application

The application can be run from Eclipse using an **OSGi Framework launch configuration**.

1. In Eclipse, right click the project.
2. Select **Run As → Run Configurations...**
3. Choose **OSGi Framework** from the list on the left.
4. Create or select a launch configuration for the project.
5. Click **Run** to start the application.

## Language Configuration

The default language of the application is Turkish.

If you want to run the program in English, you must provide the following VM Arguments to the application:
```
-Duser.language=en
-Duser.country=US
```
These VM arguments can be added in the **Run Configurations → OSGi Framework → Arguments → VM Arguments** section within Eclipse.
