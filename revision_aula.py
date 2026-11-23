import csv
import json

# Nombres de los archivos (deben estar en la misma carpeta que este script)
ARCHIVO_CSV = "incidencias.csv"
ARCHIVO_ERRORES = "errores.txt"
ARCHIVO_RESUMEN = "resumen.json"
ARCHIVO_HISTORIAL = "historial.txt"


def localizar_errores(ruta_csv, ruta_errores):
    """Recorre el CSV y escribe en errores.txt los equipos con estado ERROR.

    Devuelve el número de equipos con error.
    Si el CSV no existe, lanza FileNotFoundError (se captura en main).
    """
    contador = 0

    # El CSV se abre PRIMERO: si falta, errores.txt ni se llega a crear
    with open(ruta_csv, "r", encoding="utf-8", newline="") as f_csv:
        lector = csv.DictReader(f_csv)

        with open(ruta_errores, "w", encoding="utf-8") as f_errores:
            # Se procesa fila a fila, sin convertir el CSV en lista
            for fila in lector:
                if fila["estado"] == "ERROR":
                    f_errores.write(fila["equipo"] + "\n")  # un equipo por línea
                    contador += 1

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
    try:
        contador = localizar_errores(ARCHIVO_CSV, ARCHIVO_ERRORES)
    except FileNotFoundError:
        print(f"Error: no se encuentra el archivo '{ARCHIVO_CSV}'.")
        print("Descomprime el ZIP y deja los archivos en la misma carpeta que este script.")
        return  # termina el programa

    actualizar_resumen(ARCHIVO_RESUMEN, contador)
    actualizar_historial(ARCHIVO_HISTORIAL, contador)

    print(f"Proceso terminado. Equipos con errores: {contador}")


if __name__ == "__main__":
    main()
