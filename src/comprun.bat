@echo off
cls
echo ==========================
echo Cleaning and compiling...
echo ==========================
del /s *.class
javac *.java
if errorlevel 1 goto exit
if errorlevel 0 goto successfulCompile
goto exit

:successfulCompile
cls
echo ==========================
echo Running...
echo ==========================
java Driver
goto exit

:exit

