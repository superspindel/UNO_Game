@echo off
set JDK_HOME=c:\PROGRA~1\Java\jdk1.8.0_144
@echo "Compiling code ..."
@mkdir bin
%JDK_HOME%/bin/javac.exe -d bin -cp src src/Client/Client.java src/Server/Server.java
@echo "Compiling tests ..."
set JUNIT=lib\org.junit4-4.3.1.jar
%JDK_HOME%/bin/javac.exe -d bin -cp %JUNIT%;src src/Tests/TestRequirements.java