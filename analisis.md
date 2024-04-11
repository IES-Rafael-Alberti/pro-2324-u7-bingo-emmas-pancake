# Análisis de bingo
# CLASES:
## Jugador() Clase realizada por emmanuel
esta es la clase donde se incluirá los jugadores y máquina

### variables:
1. nombre : String
    * es el nombre del jugador, es unico por tanto no se puede repetir
1. listaCartones: Carton:
    * es la lista de los cartones el jugador

### métodos:
* cogerCartones():
    * aquí te dara un numero x de cartones

## Carton() - Clase realizada por Julio
Generan sus números aleatoriamente cada vez que son inicializados

* Companion Object
    * una lista de constantes 3,3,2,2,2,2,2,1,1
*
## Bombo()  - Clase realizada por Julio

### Atributos
* Guarda 90 números aleatorios: MutableListOf<Int>

Atributos



Métodos
SacarBolas(cantidad: Int): Bolas: ListOfInt → Saca una lista de números aleatorios

Métodos privados
EliminarBolaDelBombo()




## Partida() Clase realizada por Nico
esta clase tiene todas las clases creadas
pregunta por el número de jugadores
y pregunta por el número de cartones
se ejecuta el programa




## GestorConsola() Clase realizada por Emmanuel
aquí imprimirá todo en consola
Logs() nico
aquí guardará todo registro de la partida




logica programa:
pide n jugadores
pide n cartones (todos)
hace la partida
finaliza
y cierra flujo
   