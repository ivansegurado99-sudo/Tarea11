import csv
import json

# Nombres de los archivos (deben estar en la misma carpeta que este script)
ARCHIVO_CSV = "incidencias.csv"
ARCHIVO_ERRORES = "errores.txt"
ARCHIVO_RESUMEN = "resumen.json"
ARCHIVO_HISTORIAL = "historial.txt"

contador = 0

# ---------------------------------------------------------------
# 1 y 2. Localizar equipos con ERROR y avisar si falta el CSV
# ---------------------------------------------------------------
try:
    # El CSV se abre PRIMERO (modo r). Si no existe, salta FileNotFoundError
    # y errores.txt ni siquiera llega a crearse.
    with open(ARCHIVO_CSV, "r", encoding="utf-8", newline="") as f_csv:
        lector = csv.DictReader(f_csv)

        with open(ARCHIVO_ERRORES, "w", encoding="utf-8") as f_errores:
            # Se procesa fila a fila, sin convertir el CSV en lista
            for fila in lector:
                if fila["estado"] == "ERROR":
                    f_errores.write(fila["equipo"] + "\n")  # un equipo por línea
                    contador += 1
except FileNotFoundError:
    print(f"Error: no se encuentra el archivo '{ARCHIVO_CSV}'.")
    print("Descomprime el ZIP y deja los archivos en la misma carpeta que este script.")
    raise SystemExit(1)  # termina el programa

# ---------------------------------------------------------------
# 3. Actualizar resumen.json y historial.txt
# ---------------------------------------------------------------
# Leer el resumen (modo r)
with open(ARCHIVO_RESUMEN, "r", encoding="utf-8") as f_resumen:
    resumen = json.load(f_resumen)

# Cambiar solo total_errores; "aula" y lo demás se conserva
resumen["total_errores"] = contador

# Guardar el diccionario (modo w)
with open(ARCHIVO_RESUMEN, "w", encoding="utf-8") as f_resumen:
    json.dump(resumen, f_resumen, ensure_ascii=False, indent=4)

# Añadir una línea al historial (modo a)
with open(ARCHIVO_HISTORIAL, "a", encoding="utf-8") as f_historial:
    f_historial.write(f"Equipos con errores: {contador}\n")

print(f"Proceso terminado. Equipos con errores: {contador}")
