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
@Table(name = "actions")
public class PlayerAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String move;
    private long box;

    //MOVE PLAYER CORRECT
    private long boardRow;
    private long boardColumn;

    //
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;


    public boolean movePlayer(Player player, Dice dice) {

        //LIMITS OF THE MOVEMENT INSIDE THE BOARD
        Board board = Board.getSingletonBoard();
        long actualBox = 0;
        boolean insideLimits = false;

        if (dice.getDice1() < 0) {

            actualBox = player.getActualBox();
            long moveBox = actualBox - dice.getDice3();

            if (dice.getDice2() < 0){




            }

            insideLimits = board.checkMoveLimits(actualBox);

            if (insideLimits){

                setMove("Player moves back -" + dice.getDice3() + " boxes");
                player.setActualBox(moveBox);
                setBox(moveBox);

            }

        }


        if (dice.getDice1() > 0) {

            actualBox = player.getActualBox();
            long moveBox = actualBox + dice.getDice3();

            insideLimits = board.checkMoveLimits(actualBox);

            if (insideLimits){

                setMove("Player moves forward " + dice.getDice3() + " boxes");
                player.setActualBox(moveBox);
                setBox(moveBox);

            }

        }

        return insideLimits;

    }

}
