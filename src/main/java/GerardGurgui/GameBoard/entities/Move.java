package GerardGurgui.GameBoard.entities;

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

        boolean playerMove = false;

        char row = dice.getDice1();
        long column = dice.getDice2();


        //CHECK IF THE NEXT BOX TO MOVE IS OCCUPIED OR NOT

        if (!box.getOccupied().equalsIgnoreCase("Not occupied")) {

            throw new BoxNotValidToMove(HttpStatus.BAD_REQUEST, "Can`t move to " + box.getName() + " , this box is occupied by other player");

        }

        //MODIFY THE POSITIONS
        setBoardRow(row);
        setBoardColumn(column);
        setMove("Player " + player.getUserName() + " moves to row " + row + " column " + column);

        //CHANGE ACTUALBOX OF PLAYER
        player.setActualBox(row + "" + column);

        //CHECK IF PLAYER WIN GAME
        if (box.getName().equalsIgnoreCase(board.getWinBox())) {

            //FALTA ALGUN METODO O ALGO QUE ACABE EL JUEGO
            System.out.println("Player " + player.getUserName() + " win the game");

        }

        //SET OCCUPED BOX
        box.setOccupied(player.getUserName());
        playerMove = true;


        //PLAYER IN A WHITE BOX, RESTORE HEALTH AND BLACK GET DAMAGE

        if (box.getColor().equalsIgnoreCase("White")) {

            if (player.getHealth() <= 90) {

                long actualHealth = player.getHealth();
                player.setHealth(actualHealth + 10);

            }

        } else {

            long actualHealth = player.getHealth();
            player.setHealth(actualHealth - 20);

            if (player.getHealth() == 0) {

                //player death = PLAYER DELETE??
                System.out.println("Player " + player.getUserName() + " is dead");

            }

        }

        return playerMove;

    }

}
