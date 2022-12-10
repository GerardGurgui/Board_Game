package GerardGurgui.GameBoard.entities;
import GerardGurgui.GameBoard.game.GameFunctions;
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
@Table(name = "throws")
public class Dice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //FRIST DICE DETERMINES THE DIRECTION OF PLAYER(negative move back, positive move forward)
    private long dice1;

    //SECOND DICE DETERMINES IF THE PLAYER MOVE Y AXIS(HEIGHT) OR X AXIS (WIDTH)
    private long dice2;

    //THIRD DICE DETERMINES HOW MANY BOXES THE PLAYER MOVE
    private long dice3;

    private long magicDice;

    //THROW DICES
    public void launch(){

        //DICE 1
        long noZero = GameFunctions.numRandomPositiveOrNegative();

        if (noZero == 0){

            while (noZero == 0){

                noZero = GameFunctions.numRandomPositiveOrNegative();

                if (noZero != 0){
                    setDice1(noZero);
                }
            }

        } else{

            setDice1(noZero);
        }

        //DICE 2
        noZero = GameFunctions.numRandomPositiveOrNegative();

        if (noZero == 0){

            while (noZero == 0){

                noZero = GameFunctions.numRandomPositiveOrNegative();

                if (noZero != 0){
                    setDice1(noZero);
                }
            }

        } else{

            setDice1(noZero);
        }


        //DICE 3
        this.setDice3(GameFunctions.numRandomDice3());

    }


}
