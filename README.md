# Redes Booleanas Aleatorias (Redes de Kauffman)
## Autor: Arrieta Mancera Luis Sebastian (318174116)

<img width="400px" src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExd3p3ZmdqM3M2MzFuNnQxZGtpcHRlY2hraGJ0OGxpdmJudnpmdGN6biZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/3o7TKLwSjnUxP6C3Zu/giphy.gif"/>

Este programa ejecuta una red booleana alreatoria con 20
nodos y durante 100 iteraciones. Estos valores corresponden al **alto** y al **ancho** respectivamente. Los valores de los nodos son aleatorios, en cada ejecución.

Para correr la práctica crea una carpeta `./classes`

```bash
mkdir ./classes
```

Ejecuta el siguiente comando para compilar todas las clases

```bash
javac -d ./classes -cp lib/core.jar:. kauffman/*.java
```

Ahora ejecutamos el programa

```bash
java -cp ./classes:lib/core.jar kauffman.Kauffman
```

## NOTA:

Puedes dar click en la ventana que se abre al ejecutar el programa para cambiar de patron, esto generara nuevos
valores aleatorios y volverá a ejecutar la red booleana.

<img width="200px" src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExeWlhdzVlNzAwd21yZGFicW12bGZlcnB3ZDR0MWRlaWFxemgyazRtcyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/ZExowSAY8UyRy2UnGC/giphy.gif"/>