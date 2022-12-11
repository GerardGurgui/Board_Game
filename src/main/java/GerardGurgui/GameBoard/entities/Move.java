package GerardGurgui.GameBoard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    //MOVE PLAYER CORRECT
    private long boardRow;
    private long boardColumn;


    public void movePlayer(Player player, Dice dice) {

        //LIMITS OF THE MOVEMENT INSIDE THE BOARD
        Board board = Board.getSingletonBoard();
        long actualBox = 0;
        boolean insideLimits = false;

        if (dice.getDice1() < 0) {

            actualBox = player.getActualBox();
            long moveBox = actualBox - dice.getDice2();

            setMove("Player moves back -" + dice.getDice2() + " boxes");
            player.setActualBox(moveBox);
            setBox(moveBox);

        }

        if (dice.getDice1() > 0) {

            actualBox = player.getActualBox();
            long moveBox = actualBox + dice.getDice2();


            setMove("Player moves forward " + dice.getDice2() + " boxes");
            player.setActualBox(moveBox);
            setBox(moveBox);


        }


    }

}
