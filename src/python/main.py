import json
from pathlib import Path

class Analisis:
    def __init__(self, jsonFile):
        self.BASE_DIR = Path(__file__).resolve().parents[2]
        self.jsonRoute = self.BASE_DIR / "data" / jsonFile

    def readJson(self):
        with open(self.jsonRoute, "r", encoding="utf-8") as f:
            self.data = json.load(f)


    def getMaterias(self):
        self.readJson()
        d = []
        for datos in self.data:
            nombre = datos["nombre"]
            for materias in datos["materias"]:
                # print("ssss"+materias)
                valorMateria = materias["valorMateria"]
                materia = materias["materia"]
                notas = materias["notas"]
                promedio = self.generatePromedios(notas)
                resumen = {
                    "materia": materia,
                    "notas": promedio,
                    "valorMateria": valorMateria
                }
                d.append(resumen)
            print(self.generateFinalGrades(d, nombre))
            d.clear()

    def generatePromedios(self, notas):
        suma = 0
        cantidad = 0
        for i in notas:
            suma += i
            cantidad += 1
        promedio = suma / cantidad
        return promedio

    def generateFinalGrades(self, resumen, estudiante):
        finalGrade = 0
        for i in resumen:
            print(i)
            print("aaaaaaaaaaa")

            materia = i["materia"]
            valorMateria = i["valorMateria"]
            promedio = i["notas"]
            finalGrade += (valorMateria * promedio) / 100

        return f"La nota final de {estudiante} es: {finalGrade}"



Analizar = Analisis("estudiantes.json")
Analizar.getMaterias()