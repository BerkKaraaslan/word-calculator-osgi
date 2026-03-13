# Word Calculator OSGi

This project implements a simple desktop application using:

- Java 21
- OSGi (Equinox)
- Eclipse Plug-in Development Environment (PDE)
- Swing

The application performs arithmetic operations on numbers written in words.

Example:

iki yüz bir + on bir = iki yüz on iki


---

# Architecture

The system consists of two OSGi services:

### 1) Number Converter Service
Converts numbers between word and integer representation.

### 2) Calculator UI Service
Provides the Swing user interface and arithmetic operations.

---

# Running the Application

The application must be run using an **OSGi Framework launch configuration** in Eclipse.

Follow the steps below to correctly configure and run the project.

---

# Step 1 — Open Run Configurations

1. Right click the project in Eclipse.
2. Select: **Run As → Run Configurations...**
3. In the left panel select: **OSGi Framework**
4. Create a new launch configuration or select the existing one.

---

# OSGi Framework Configuration

The following configuration must be applied in the **Run Configurations** window.

## Framework Settings

Framework: Equinox

Launch with: Bundles selected below

Default Start level: 4

Default Auto-Start: true


---

# Program Arguments

The **Program arguments** field should contain the following values:

**-os ${target.os} -ws ${target.ws} -arch ${target.arch} -nl ${target.nl} -consoleLog -console**

These parameters configure the runtime environment of the Equinox OSGi framework.

---

# VM Arguments

The following VM arguments are configured for the runtime:

***-Dorg.eclipse.swt.graphics.Resource.reportNonDisposed=true***

***-Declipse.ignoreApp=true***

***-Dosgi.noShutdown=true***

***-Duser.language=en (optional)***

***-Duser.country=US (optional)***

These parameters configure:

- SWT resource debugging
- Eclipse runtime behavior
- OSGi shutdown behavior
- Application locale (optional)

---

# Launch Options

The following launch options must be enabled:

***Use the -XstartOnFirstThread argument when launching with SWT***

***Use the -XX:+ShowCodeDetailsInExceptionMessages argument when launching***

The **Use @argfile when launching** option should remain disabled.

---

# Working Directory

The working directory should remain set to the default value provided by Eclipse.

---

# Bundle Configuration

In the **Bundles** tab the following configuration must be applied.

## Launch Mode

Launch with: Bundles selected below

## Selected Workspace Bundles

Only the following **three bundles must be selected**:

***calculator-ui-service (1.0.0.qualifier)***

***number-converter-service (1.0.0.qualifier)***

***org.eclipse.osgi***

These bundles appear under:

Workspace  
Target Platform

### Important

Only these **three bundles** must be selected.

All other bundles in the **Target Platform** must remain unselected.

---

# Bundle Start Level Configuration

The bundle start levels are configured as follows:

**number-converter-service**

Start Level: **2**  
Auto-Start: **true**

**calculator-ui-service**

Start Level: **4**  
Auto-Start: **true**

---

# Bundle Runtime Options

The following options must be enabled:

***Include required Bundles automatically while launching***

***Include optional dependencies when computing required Bundles***

***Add new workspace Bundles to this launch configuration automatically***

***Validate Bundles automatically prior to launching***

These options allow Eclipse PDE to resolve required runtime dependencies automatically.

---

# Language Configuration

The default language of the application is **Turkish**.

If you want to run the program in **English**, you must provide the following VM arguments:

***-Duser.language=en***

***-Duser.country=US***

These arguments can be set in:

Run Configurations  
→ OSGi Framework  
→ Arguments  
→ VM Arguments

---

# Running the Application

After the configuration is complete:

1. Open **Run Configurations**
2. Select the **OSGi Framework** configuration
3. Click **Run**

The OSGi container will start and the Swing user interface will be launched automatically.