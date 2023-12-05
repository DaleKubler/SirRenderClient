if not "%minimized%"=="" goto :minimized
set minimized=true
start /min cmd /C "%~dpnx0"
goto :EOF
:minimized
@echo off
TITLE SirRender Client
java -jar applications\SirRender2Client.jar 4444
rem pause