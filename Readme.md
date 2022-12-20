
# Board Game



## Objetivo del juego

El objetivo principal será llegar a una casilla del tablero que se asignará aleatoria al empezar la partida,
el jugador que llegue antes, o que mate al otro antes de llegar a la casilla ganará.
    

### - Características del juego 

* Empieza el juego con dos jugadores, (usuarios que se registran y hacen login) protocolos y validación JWT y Spring Security.
* Cada jugador tendrá su turno (falta por determinar atributos de jugador que influyan) y cada jugador tirará dos dados
* El primer dado determinará la dirección del jugador (número positivo el jugador avanza, negativo retrocede) y el segundo dado determinará las casillas que se mueve
* DE MOMENTO ESTO

---

### POSIBLES OPCIONES

* valorar efectos en algunas casillas, positivos o negativos para el jugador que caiga, objetos o armas.
* posibles casillas especiales :
  * el jugador vuelve a tirar los dados
  * objeto recuperar vida
  * objeto arma
  * penalización de movimiento
* FALTA

---

## Estructura del proyecto

* Entidades: Jugadores, tirada(dados), tablero
* Relaciones:
  * Un jugador puede realizar varias tiradas de dados, una tirada solo peretence a un jugador(One To Many)
  * En el tablero pueden jugar varios Jugadores, un jugador sólo puede estar en un tablero(One to many)


* Control de excepciones peronalizadas (Exception Handling)

* Seguridad:
  * Registro y login de usuarios(Jugadores)
  * Autenticación y Autorización
  * Cifrado de contraseña
  * Acceso a través de JWT

