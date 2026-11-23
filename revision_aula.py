import csv
import json
from pathlib import Path

# Carpeta donde está este script: los archivos se buscan siempre ahí,
# se ejecute el programa desde donde se ejecute
CARPETA = Path(__file__).parent

ARCHIVO_CSV = CARPETA / "incidencias.csv"
ARCHIVO_ERRORES = CARPETA / "errores.txt"
ARCHIVO_RESUMEN = CARPETA / "resumen.json"
ARCHIVO_HISTORIAL = CARPETA / "historial.txt"


def localizar_errores(ruta_csv, ruta_errores):
    """Recorre el CSV y escribe en errores.txt los equipos con estado ERROR.

    Devuelve el número de equipos con error.
    Si el CSV no existe, muestra un aviso y termina el programa.
    """
    contador = 0

    try:
        # El CSV se abre PRIMERO: si falta, errores.txt ni se llega a crear
        with open(ruta_csv, "r", encoding="utf-8", newline="") as f_csv:
            lector = csv.DictReader(f_csv)

            with open(ruta_errores, "w", encoding="utf-8") as f_errores:
                # Se procesa fila a fila, sin convertir el CSV en lista
                for fila in lector:
                    if fila["estado"] == "ERROR":
                        f_errores.write(fila["equipo"] + "\n")  # un equipo por línea
                        contador += 1
    except FileNotFoundError:
        print(f"Error: no se encuentra el archivo '{ruta_csv.name}'.")
        print("Descomprime el ZIP y deja los archivos en la misma carpeta que este script.")
        raise SystemExit(1)  # termina el programa

    return contador


def actualizar_resumen(ruta_resumen, contador):
    """Lee resumen.json, actualiza total_errores y lo guarda (conserva 'aula')."""
    with open(ruta_resumen, "r", encoding="utf-8") as f_resumen:
        resumen = json.load(f_resumen)

    resumen["total_errores"] = contador

    with open(ruta_resumen, "w", encoding="utf-8") as f_resumen:
        json.dump(resumen, f_resumen, ensure_ascii=False, indent=2)


def actualizar_historial(ruta_historial, contador):
    """Añade una línea al final de historial.txt (modo a)."""
    with open(ruta_historial, "a", encoding="utf-8") as f_historial:
        f_historial.write(f"Equipos con errores: {contador}\n")


def main():
    contador = localizar_errores(ARCHIVO_CSV, ARCHIVO_ERRORES)
    actualizar_resumen(ARCHIVO_RESUMEN, contador)
    actualizar_historial(ARCHIVO_HISTORIAL, contador)

    print(f"Proceso terminado. Equipos con errores: {contador}")


if __name__ == "__main__":
    main()
