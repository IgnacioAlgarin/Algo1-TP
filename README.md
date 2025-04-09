# 📊 Trabajo Práctico - Algoritmos 1 (UNSAM) | Manipulación de Datos Tabulares en Java

Este proyecto fue desarrollado como parte de un **Trabajo Práctico de la materia Algoritmos 1** en la **Licenciatura en Ciencia de Datos** (UNSAM). El objetivo es construir una pequeña librería en Java que permita representar y manipular datos tabulares (estructuras en 2 dimensiones) sin utilizar librerías externas.

---

## 📝 Enunciado del problema

Se desea construir una librería que permita manipular y analizar datos en forma tabular (2 dimensiones) para el lenguaje Java. Deberá ofrecer estructuras de datos y operaciones que soporten la funcionalidad solicitada, como así también contemple posibles extensiones futuras, minimizando el impacto ante alguna modificación.

En principio no es necesario hacer foco en la eficiencia de las operaciones, pero sería deseable disponer también de algún mecanismo que nos permita cuantificar, al menos en tiempo, el costo de su ejecución.

📌 Se permite utilizar estructuras existentes del lenguaje Java (por ejemplo: listas, arreglos, mapas), pero **no se deben utilizar librerías externas**. En caso de requerir alguna, se debe consultar previamente su viabilidad.

---

## 🙋‍♂️ Mi participación

Este proyecto fue trabajado en conjunto con [@mfrias96](https://github.com/mfrias96).  
Mis aportes principales incluyeron:

- Diseño de las clases base para la estructura tabular
- Implementación de operaciones básicas de manipulación (agregar, eliminar, buscar filas/columnas)
- Lectura de datos desde archivos `.txt`
- Colaboración en la lógica general y modularización del código
- Validación de entradas y pruebas manuales

---

## 🧠 Funcionalidades implementadas

- Representación de datos tabulares utilizando listas y arreglos
- Lectura desde archivos de texto
- Inserción y eliminación de filas/columnas
- Búsqueda por contenido
- Cálculo del tiempo de ejecución de operaciones (básico)

---

## 🔧 Tecnologías utilizadas

- Lenguaje: Java 17
- IDE sugerido: IntelliJ IDEA / Eclipse / Visual Studio Code
- Sin uso de librerías externas

---

## 🚀 Cómo ejecutar

```bash
javac src/Main.java
java src/Main

---

## 📂 Estructura del proyecto

src/
├── Main.java
├── Tabla.java
├── Fila.java
├── Columna.java
├── ...
test/
└── TablaTest.java
archivos_de_entrada/
└── datos.txt

---

## 👤 Autor

**Juan Ignacio Algarin**  
Estudiante de Ciencia de Datos – Universidad Nacional de San Martín (UNSAM)  
📧 [juanalgarin00@gmail.com](mailto:juanalgarin00@gmail.com)  
🔗 [GitHub](https://github.com/IgnacioAlgarin)
