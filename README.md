# Input-Sensitive-Profiling 
This file consists of instructions to build our application followed by the steps to instrument this application with a profiler.

Building LinuxDC++ :

We will first install all the dependencies, then install linuxdcpp and scons.

In order to build LinuxDC++, follow the following steps:
1. The dependencies listed here are the mandatory dependencies and have to be downloaded before the process of building LinuxDC++ is initiated. The list of dependencies is as follows:
§  Bzr
§  Scons
§  Build-essential
§  Libgtk2.0-dev
§  Libglade2-dev
§  Zlib1g-de
§  Libbz2-dev
§  Libssl-dev
§  Libboost-dev
In order to download these in a single command, write the following command in the Ubuntu terminal:

sudo apt-get install bzr scons build-essential libgtk2.0-dev libglade2-dev zlib1g-dev libbz2-dev libssl-dev libboost-dev

2. Now, to install LinuxDC++, download it through bazaar. Then run the following commands in the home directory or somewhere where the person performing the installation has the write access:

bzr branch lp:linuxdcpp (to download LinuxDC++ through bazaar)

3. The final step in installation:
cd linuxdcpp
scons release=1
sudo scons install

4. Running LinuxDC++: After running these commands, LinuxDC++ is downloaded and installed on the Ubuntu operating system. In order to run the application, one caution needs to be taken. It is recommended to turn off Assistive Technologies before running LinuxDC++. To turn the Assistive Technologies off go to System->Preferences->Assistive Technologies. For some reason, it makes LinuxDC++ run very slowly and display a bunch of errors.

Now, to run the application, type the following in the terminal:
linuxdcpp

The application executes after this command. You should see an executable named linuxdcpp inside your linuxdcpp folder.

Building aprof:

1. Get the latest version of aprof and aprof-plot from:
https://github.com/ercoppa/aprof/wiki
We downloaded aprof 0.2.1: [TGZ]
And aprof-plot 0.2.0: [TGZ]

Extract these .tgz file to say the home directory.

2. Run the following commands to get automake by going to this directory (using cd aprof-0.2.1)
   sudo apt-get update
   sudo apt-get install automake

3. You need to make the autogen.sh file by following command:
   ./autogen.sh

4. Make a new directory say ‘inst’ inside which you will install valgrind. Run the following:
   ./configure --prefix=/home/”wherever you want it to be installed”

So in my case, the command becomes, ./configure --prefix=/home/shiwangi/aprof-0.2.1/inst/

5. Now make the files by typing:
   make
   make install

6. Finally, run the valgrind with tool chosen as aprof as:

 ./the path where you have installed during ./configure/bin/valgrind --tool=aprof “the file you want    to aprof with”.

 In my case, I run ./aprof-0.2.1/inst/bin/valgrind --tool=aprof linuxdcpp linuxdcpp

Instrumenting the AUT:

Each time I want to instrument linuxdcpp with aprof, I run this very command:
./aprof-0.2.1/inst/bin/valgrind --tool=aprof linuxdcpp linuxdcpp

Running aprof-plot:

1. Go to the aprof-plot-0.2.0 directory wherever you extracted the .tgz before.
     cd aprof-plot-0.2.0 
2. Type the following command to run the aprof-plot application:
     java -jar aplot.jar

This will open the application you can choose any of the aprof output files and view their plots method by method.
File > open file> .aprof file > open


 
Additional Resources[Not necessary]
 
Some of the issues that we faced during building the application are addressed below.

Wifi Setup:

In order to start downloading and installing the application, the first thing that needs to be done is getting connected to the internet. Since the input-sensitive profiling of the application was done at University of Illinois at Chicago, the primary step is to get connected to the UIC-WiFi. The  following steps are to be taken to connect to UIC-WiFi:[9]
1) Click on the network manager applet icon and select "UIC-WiFi" from the list of available networks.
2) A window opens up similar to the one below asking for the security information. Set the security type to "WPA & WPA2 Enterprise". Now, enter the rest of the information as seen below.
3)     Now the CA certificate is to be provided. The location of the CA certificate, by default, should be in "/etc/ssl/certs". The certs should be installed by default on Ubuntu. If they are missing then search the web for steps to install them on Linux distribution as the package manager and names can change between different Linux distributions. The certificate required here is named: "AddTrust_External_Root.pem"
4)     Connection to UIC-WiFi should now be established, if any problems were experienced, it is recommended to go through the guide again to make sure nothing was missed out, and check that the correct username and password was entered. Keep in mind that the UIC ACCC does not officially support Linux in any way so they will not be able to help in the event of a problem, unless there is a problem with one of their access points itself

Java Version:

Running aprof-plot has the following java requirement. You may not face this issue if you have the appropriate one installed.
openjdk-8-headless is required to run aplot.


For any other glitch that you face while building the application or while instrumenting with the profiler, feel free to contact any of us. 


Doxygen :

doxygen is used to generate caller-calle  graphs. to install it on linux, run these commands 
sudo apt-get install doxygen
sudo apt-get install doxygen-gui

To run doxygen run this command

doxywizard

Running Java Code :

MethodsInvokedByInput.java
          this method takes input a list of files generated by profiler and gives a list of methods that were invoke. It also filters out methods which belong to third party, as their source code is not given.
to run this, just replace the filepath to the path of folder which contain the .aprof files.

    2) FindThreadsInMethods.java
        This output of above method is given as input to this method. This code takes each method in that file and checks if that method is being accessed ny multiple threads.
 to run this, just replace filepath to path of the output file of MethodsInvokedByInput.java
