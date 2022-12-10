package GerardGurgui.GameBoard.game;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GameFunctions {

    private static final Logger log = LoggerFactory.getLogger(GameFunctions.class);


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

    public static int numRandomDice3(){

        return (int) (Math.random() * 6 +1);

    }



}
