import json
from pathlib import Path

class Analisis:
    def __init__(self, jsonFile):
        self.BASE_DIR = Path(__file__).resolve().parents[2]
        self.jsonRoute = self.BASE_DIR / "data" / jsonFile

    def readJson(self):
        with open(self.jsonRoute, "r", encoding="utf-8") as f:
            data = json.load(f)

        print (data)



Analizar = Analisis("estudiantes.json")
Analizar.readJson()