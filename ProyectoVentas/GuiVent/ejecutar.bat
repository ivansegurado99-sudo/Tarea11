@echo off
rem Compila y ejecuta GuiVent. Uso: ejecutar.bat [url usuario contrasena]
if not exist bin mkdir bin
javac -encoding UTF-8 -cp "lib\*" -d bin src\guivent\GuiVent.java || exit /b 1
java -cp "bin;lib\*" guivent.GuiVent %*
