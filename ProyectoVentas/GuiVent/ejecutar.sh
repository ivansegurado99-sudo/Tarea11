#!/bin/sh
# Compila y ejecuta GuiVent. Uso: ./ejecutar.sh [url usuario contrasena]
cd "$(dirname "$0")" || exit 1
mkdir -p bin
javac -encoding UTF-8 -cp "lib/*" -d bin src/guivent/GuiVent.java || exit 1
java -cp "bin:lib/*" guivent.GuiVent "$@"
