@echo off
cd /d "%~dp0"
title Expense Tracker
echo Starting Expense Tracker...
java -cp "out;lib\sqlite-jdbc.jar" Main
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo An error occurred while launching.
    pause
)
