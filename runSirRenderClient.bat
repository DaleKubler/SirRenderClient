if not "%minimized%"=="" goto :minimized
set minimized=true
start /min cmd /C "%~dpnx0"
goto :EOF
:minimized
@echo off
TITLE SirRender GUI Client
java -jar applications\SirRenderGUIClient.jar 4444
rem pause