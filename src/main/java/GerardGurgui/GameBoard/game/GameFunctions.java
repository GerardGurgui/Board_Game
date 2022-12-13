package GerardGurgui.GameBoard.game;


import GerardGurgui.GameBoard.entities.Box;
import GerardGurgui.GameBoard.entities.Player;
import GerardGurgui.GameBoard.services.PlayerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GameFunctions {



    private static final Logger log = LoggerFactory.getLogger(GameFunctions.class);


    public static void assignStartPositionPlayers(Player player1, Player player2){

        player1.setActualBox("A1");
        player2.setActualBox("F6");

    }

    public static void assignStartPositionBoxes(Box box1, Box box2){

        box1.setOccupied("occupied");
        box2.setOccupied("occupied");

    }



    //RANDOM NUMBERS
    public static int numRandomBoard(){

        int negative = (int) (Math.random() * -6 +4);
        int positive = (int) (Math.random() * 6 +4);

        return negative+positive;

    }

    public static long numRandomPositiveOrNegative(){

        Random r = new Random();
        return r.nextLong( 3)-1;

    }

    public static char numRandomDice1(){

        int num = (int) (Math.random() * 6 +1);
        char row = ' ';

        if (num == 1){
            row = 'A';

        } else if (num == 2){
            row = 'B';

        } else if (num == 3){
            row = 'C';

        } else if (num == 4){
            row = 'D';

        } else if (num == 5){
            row = 'E';

        } else if (num == 6){
            row = 'F';
        }

        return row;

    }

    public static int numRandomDice2(){

        return (int) (Math.random() * 6 +1);

    }



}
