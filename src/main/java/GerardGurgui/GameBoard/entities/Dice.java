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


    private long magicDice;

    //THROW DICES
    public void launchDice1(){

        //DICE 1
        long numDice1 = GameFunctions.numRandomPositiveOrNegative();

        if (numDice1 == 0){

            while (numDice1 == 0){

                numDice1 = GameFunctions.numRandomPositiveOrNegative();

                if (numDice1 != 0){
                    setDice1(numDice1);
                }
            }

        } else{

            setDice1(numDice1);
        }

    }

    public void launchDice2(){

        setDice2(GameFunctions.numRandomDice2());

    }


}
