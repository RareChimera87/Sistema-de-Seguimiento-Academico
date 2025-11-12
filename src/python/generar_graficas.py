import json
import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
import numpy as np
import os
from datetime import datetime
from pathlib import Path

# Configuración de estilo
plt.style.use('seaborn-v0_8-darkgrid')
sns.set_palette("husl")

def cargar_datos():
    """Carga los datos del archivo JSON"""
    try:
        base_dir = Path(__file__).resolve().parents[2]
        # Ruta relativa desde src/python/ hacia data/
        ruta_json = base_dir / "data/estudiantes.json"
        
        with open(ruta_json, 'r', encoding='utf-8') as f:
            estudiantes = json.load(f)
        
        print(f"✓ Datos cargados: {len(estudiantes)} estudiantes encontrados")
        return estudiantes
    
    except FileNotFoundError:
        print("✗ Error: No se encontró el archivo estudiantes.json")
        return []
    except json.JSONDecodeError:
        print("✗ Error: El archivo JSON está corrupto")
        return []

def procesar_datos(estudiantes):
    """Procesa los datos para las gráficas"""
    datos = []
    
    for est in estudiantes:
        # Calcular promedio general de todas las notas
        todas_notas = []
        for materia in est['materias']:
            todas_notas.extend(materia['notas'])
        
        promedio_general = round(np.mean(todas_notas), 2) if todas_notas else 0
        
        # Calcular porcentaje de asistencia general
        total_asistencias = 0
        asistencias_positivas = 0
        for materia in est['materias']:
            for asist in materia['asistencia']:
                total_asistencias += 1
                if asist['asistencia']:
                    asistencias_positivas += 1
        
        porc_asistencia = round((asistencias_positivas / total_asistencias * 100), 2) if total_asistencias > 0 else 0
        
        # Preparar datos por materia para el heatmap
        materias_dict = {}
        for materia in est['materias']:
            nombre_materia = materia['materia']
            asist_materia = sum(1 for a in materia['asistencia'] if a['asistencia'])
            total_materia = len(materia['asistencia'])
            porc_materia = round((asist_materia / total_materia * 100), 2) if total_materia > 0 else 0
            materias_dict[nombre_materia] = porc_materia
        
        datos.append({
            'nombre': est['nombre'].title(),
            'id': est['id'],
            'grupo': est['grupo'],
            'promedio': promedio_general,
            'participacion': est['participacion'],
            'asistencia': porc_asistencia,
            'materias': materias_dict,
            'num_materias': len(est['materias'])
        })
    
    print(f"✓ Datos procesados correctamente")
    return datos

def crear_carpeta_graficas():
    """Crea la carpeta para guardar las gráficas"""
    carpeta = './data/graficas'
    if not os.path.exists(carpeta):
        os.makedirs(carpeta)
        print(f"✓ Carpeta creada: {carpeta}")
    return carpeta

def grafica1_promedio_estudiantes(datos, carpeta):
    """Gráfica de Barras - Promedio de Notas por Estudiante (Matplotlib)"""
    print("\n📊 Generando Gráfica 1: Promedio por Estudiante...")
    
    df = pd.DataFrame(datos)
    df = df.sort_values('promedio', ascending=False)
    
    plt.figure(figsize=(14, 7))
    
    # Crear degradado de colores
    colores = plt.cm.viridis(np.linspace(0.3, 0.9, len(df)))
    
    barras = plt.bar(df['nombre'], df['promedio'], color=colores, edgecolor='black', linewidth=1.2)
    
    # Agregar valores encima de las barras
    for i, (barra, valor) in enumerate(zip(barras, df['promedio'])):
        plt.text(barra.get_x() + barra.get_width()/2, barra.get_height() + 0.1,
                f'{valor:.2f}', ha='center', va='bottom', fontsize=10, fontweight='bold')
    
    plt.xlabel('Estudiante', fontsize=14, fontweight='bold')
    plt.ylabel('Promedio de Notas', fontsize=14, fontweight='bold')
    plt.title('Promedio de Notas por Estudiante', fontsize=16, fontweight='bold', pad=20)
    plt.xticks(rotation=45, ha='right', fontsize=11)
    plt.ylim(0, 5.5)
    plt.grid(axis='y', alpha=0.3, linestyle='--')
    
    # Línea de referencia (nota mínima aprobatoria)
    plt.axhline(y=3.0, color='red', linestyle='--', linewidth=2, label='Nota Mínima (3.0)', alpha=0.7)
    plt.legend(fontsize=11)
    
    plt.tight_layout()
    ruta = os.path.join(carpeta, 'grafica1_promedios.png')
    plt.savefig(ruta, dpi=300, bbox_inches='tight')
    plt.close()
    
    print(f"✓ Gráfica 1 guardada: {ruta}")

def grafica2_participacion_promedio(datos, carpeta):
    """Gráfica de Dispersión - Participación vs Promedio (Matplotlib)"""
    print("\n📊 Generando Gráfica 2: Participación vs Promedio...")
    
    df = pd.DataFrame(datos)
    
    plt.figure(figsize=(12, 8))
    
    # Scatter plot con colores por grupo
    grupos = df['grupo'].unique()
    colores = plt.cm.Set2(np.linspace(0, 1, len(grupos)))
    
    for i, grupo in enumerate(sorted(grupos)):
        df_grupo = df[df['grupo'] == grupo]
        plt.scatter(df_grupo['participacion'], df_grupo['promedio'], 
                   s=200, alpha=0.7, c=[colores[i]], 
                   edgecolors='black', linewidth=1.5,
                   label=f'Grupo {grupo}')
        
        # Agregar etiquetas con nombres
        for _, row in df_grupo.iterrows():
            plt.annotate(row['nombre'], 
                        (row['participacion'], row['promedio']),
                        xytext=(5, 5), textcoords='offset points',
                        fontsize=9, alpha=0.8)
    
    plt.xlabel('Nota de Participación', fontsize=14, fontweight='bold')
    plt.ylabel('Promedio de Notas', fontsize=14, fontweight='bold')
    plt.title('Relación entre Participación y Rendimiento Académico', 
             fontsize=16, fontweight='bold', pad=20)
    
    plt.grid(True, alpha=0.3, linestyle='--')
    plt.legend(fontsize=12, loc='best')
    
    # Líneas de referencia
    plt.axhline(y=3.0, color='red', linestyle='--', linewidth=1, alpha=0.5)
    plt.axvline(x=3.0, color='red', linestyle='--', linewidth=1, alpha=0.5)
    
    plt.tight_layout()
    ruta = os.path.join(carpeta, 'grafica2_participacion.png')
    plt.savefig(ruta, dpi=300, bbox_inches='tight')
    plt.close()
    
    print(f"✓ Gráfica 2 guardada: {ruta}")

def grafica3_heatmap_asistencias(datos, carpeta):
    """Heatmap - Asistencias por Estudiante y Materia (Seaborn)"""
    print("\n📊 Generando Gráfica 3: Heatmap de Asistencias...")
    
    # Crear matriz de asistencias
    todas_materias = set()
    for est in datos:
        todas_materias.update(est['materias'].keys())
    
    todas_materias = sorted(list(todas_materias))
    
    if not todas_materias:
        print("⚠ No hay materias para generar heatmap")
        return
    
    matriz = []
    nombres = []
    
    for est in datos:
        fila = []
        nombres.append(est['nombre'])
        for materia in todas_materias:
            fila.append(est['materias'].get(materia, 0))
        matriz.append(fila)
    
    df_heatmap = pd.DataFrame(matriz, columns=todas_materias, index=nombres)
    
    plt.figure(figsize=(12, 8))
    
    sns.heatmap(df_heatmap, annot=True, fmt='.1f', cmap='RdYlGn', 
                linewidths=0.5, cbar_kws={'label': 'Asistencia (%)'}, 
                vmin=0, vmax=100, square=False)
    
    plt.title('Porcentaje de Asistencia por Estudiante y Materia', 
             fontsize=16, fontweight='bold', pad=20)
    plt.xlabel('Materia', fontsize=14, fontweight='bold')
    plt.ylabel('Estudiante', fontsize=14, fontweight='bold')
    plt.xticks(rotation=45, ha='right')
    plt.yticks(rotation=0)
    
    plt.tight_layout()
    ruta = os.path.join(carpeta, 'grafica3_heatmap_asistencias.png')
    plt.savefig(ruta, dpi=300, bbox_inches='tight')
    plt.close()
    
    print(f"✓ Gráfica 3 guardada: {ruta}")

def grafica4_distribucion_grupos(datos, carpeta):
    """Gráfica de Violín - Distribución de Notas por Grupo (Seaborn)"""
    print("\n📊 Generando Gráfica 4: Distribución por Grupos...")
    
    df = pd.DataFrame(datos)
    
    plt.figure(figsize=(12, 8))
    
    # Violin plot
    ax = sns.violinplot(data=df, x='grupo', y='promedio', palette='muted', 
                       inner='box', linewidth=1.5)
    
    # Agregar puntos individuales
    sns.swarmplot(data=df, x='grupo', y='promedio', color='black', 
                 alpha=0.6, size=8, ax=ax)
    
    plt.xlabel('Grupo', fontsize=14, fontweight='bold')
    plt.ylabel('Promedio de Notas', fontsize=14, fontweight='bold')
    plt.title('Distribución de Notas por Grupo', fontsize=16, fontweight='bold', pad=20)
    
    # Línea de referencia
    plt.axhline(y=3.0, color='red', linestyle='--', linewidth=2, 
               label='Nota Mínima (3.0)', alpha=0.7)
    
    plt.legend(fontsize=11)
    plt.grid(axis='y', alpha=0.3, linestyle='--')
    
    plt.tight_layout()
    ruta = os.path.join(carpeta, 'grafica4_distribucion_grupos.png')
    plt.savefig(ruta, dpi=300, bbox_inches='tight')
    plt.close()
    
    print(f"✓ Gráfica 4 guardada: {ruta}")

def generar_reporte_texto(datos, carpeta):
    """Genera un archivo de texto con estadísticas"""
    ruta = os.path.join(carpeta, 'reporte_estadisticas.txt')
    
    df = pd.DataFrame(datos)
    
    with open(ruta, 'w', encoding='utf-8') as f:
        f.write("=" * 60 + "\n")
        f.write("REPORTE DE ESTADÍSTICAS ACADÉMICAS\n")
        f.write("=" * 60 + "\n\n")
        f.write(f"Fecha de generación: {datetime.now().strftime('%d/%m/%Y %H:%M:%S')}\n")
        f.write(f"Total de estudiantes: {len(datos)}\n\n")
        
        f.write("-" * 60 + "\n")
        f.write("ESTADÍSTICAS GENERALES\n")
        f.write("-" * 60 + "\n")
        f.write(f"Promedio general: {df['promedio'].mean():.2f}\n")
        f.write(f"Promedio más alto: {df['promedio'].max():.2f}\n")
        f.write(f"Promedio más bajo: {df['promedio'].min():.2f}\n")
        f.write(f"Desviación estándar: {df['promedio'].std():.2f}\n\n")
        
        f.write(f"Asistencia promedio: {df['asistencia'].mean():.2f}%\n")
        f.write(f"Participación promedio: {df['participacion'].mean():.2f}\n\n")
        
        f.write("-" * 60 + "\n")
        f.write("ESTUDIANTES POR GRUPO\n")
        f.write("-" * 60 + "\n")
        for grupo in sorted(df['grupo'].unique()):
            cantidad = len(df[df['grupo'] == grupo])
            promedio = df[df['grupo'] == grupo]['promedio'].mean()
            f.write(f"Grupo {grupo}: {cantidad} estudiantes (Promedio: {promedio:.2f})\n")
    
    print(f"✓ Reporte estadístico guardado: {ruta}")

def main():
    """Función principal"""
    print("\n" + "=" * 60)
    print("GENERADOR DE GRÁFICAS ACADÉMICAS")
    print("=" * 60 + "\n")
    
    # Cargar datos
    estudiantes = cargar_datos()
    
    if not estudiantes:
        print("\n✗ No se pueden generar gráficas sin datos")
        return
    
    # Procesar datos
    datos = procesar_datos(estudiantes)
    
    # Crear carpeta para gráficas
    carpeta = crear_carpeta_graficas()
    
    # Generar las 4 gráficas
    grafica1_promedio_estudiantes(datos, carpeta)
    grafica2_participacion_promedio(datos, carpeta)
    grafica3_heatmap_asistencias(datos, carpeta)
    grafica4_distribucion_grupos(datos, carpeta)
    
    # Generar reporte
    generar_reporte_texto(datos, carpeta)
    
    print("\n" + "=" * 60)
    print("✓ PROCESO COMPLETADO EXITOSAMENTE")
    print("=" * 60)
    print(f"\nLas gráficas se han guardado en: {carpeta}/")
    print("\nGráficas generadas:")
    print("  1. grafica1_promedios.png")
    print("  2. grafica2_participacion.png")
    print("  3. grafica3_heatmap_asistencias.png")
    print("  4. grafica4_distribucion_grupos.png")
    print("  5. reporte_estadisticas.txt")
    print()

if __name__ == "__main__":
    main()