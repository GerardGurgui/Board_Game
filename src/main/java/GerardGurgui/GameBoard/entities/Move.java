package GerardGurgui.GameBoard.entities;

import GerardGurgui.GameBoard.controllers.utils.MessageResponseEntity;
import GerardGurgui.GameBoard.exceptions.customizedExceptions.BoxNotValidToMove;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "moves")
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String move;
    private long box;

    private char boardRow;
    private long boardColumn;


    public boolean movePlayer(Player player, Dice dice, Box box) {

        Board board = Board.getSingletonBoard();

        String actualBox = player.getActualBox();

        boolean playerMove = false;

        char row = dice.getDice1();
        long column = dice.getDice2();


        //1- CASILLA ACTUAL DEL JUGADOR, MARCARLA COMO DESOCUPADA (EN CASO DE QUE SE MUEVA)


        //2- COMPROBAR SI LA SIGUIENTE POSICIÓN DE CASILLA QUE NO ESTÉ OCUPADA, SI ESTÁ OCUPADA, NO SE MUEVE

        if (!box.getOccupied().equalsIgnoreCase("Void")){

            throw new BoxNotValidToMove(HttpStatus.BAD_REQUEST,"Can`t move to "+box.getName()+" , this box is occupied by other player");

        }

            //MODIFY THE POSITIONS
            setBoardRow(row);
            setBoardColumn(column);
            setMove("Player " +player.getUserName()+ " moves to row " +row+ " column " +column);

            //CHANGE ACTUALBOX OF PLAYER
            player.setActualBox(row+""+column);

            //4- COMPROBAR SI LA SIGUIENTE POSICIÓN DE CASILLA ES LA CASILLA DE VICTORIA
            if (box.getName().equalsIgnoreCase(board.getWinBox())){

                System.out.println("Player " +player.getUserName()+ " win the game");

            }

            //SET OCCUPED BOX
            box.setOccupied(player.getUserName());
            playerMove = true;


        //6- VERIFICAR POSICIÓN DEL OTRO JUGADOR?? PAR PODER ATACAR O NO SE, QUIZÁ CON RETURN Y DESDE SERVICIOS

        //7- EXTRAS: SI JUGADOR CAE EN CASILLA BLANCA CURA VIDA, PERO SI ES NEGRA RESTA?? BUSCAR ALGO QUE LE DE MAS GRACIA AL JUEGO

        if (box.getColor().equalsIgnoreCase("White")){

            if (player.getHealth() <= 90){

                long actualHealth = player.getHealth();
                player.setHealth(actualHealth + 10);

            }

        } else {

            long actualHealth = player.getHealth();
            player.setHealth(actualHealth - 20);

            if (player.getHealth() == 0){

                //player death = PLAYER DELETE??
                System.out.println("Player "+player.getUserName()+ " is dead");

            }

        }

        //LIMITS OF THE MOVEMENT INSIDE THE BOARD

        return playerMove;

    }

}
