package GerardGurgui.GameBoard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor

/*SINGLETON PATTERN, ONLY ONE BOARD FOR THE GAME*/

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "board_id")
    private static Board board;

    //BOARD PARAMETERS
    private String name;
    private long height;
    private long width;
    private long numberOfBoxes;

    //SPECIAL BOX
    private String specialBox;
    private long winBox;
//    private Bow bow;
//    private MedicalKit medicalKit;

    //PLAYERS
    @ManyToOne
    @JoinColumn(name = "first_player_id")
    private Player firstPlayer;

    @ManyToOne
    @JoinColumn(name = "second_player_id")
    private Player secondPlayer;


    //BOXES
    @OneToMany(cascade = CascadeType.ALL)
            @JoinColumn(name = "boxes")
    List<Box> listOfBoxes;

    private Board(){

        listOfBoxes = new ArrayList<>();
    }


    //BOARD METHODS

    public long totalNumberOfBoxes(){

        return this.height * this.width;

    }

    public void addBoxesToBoard(){

        PlayerAction playerAction = new PlayerAction();

        char letter;

        //I rows, J columns
        for (int i = 1; i <= height; i++) {

            for (int j = 1; j <= width; j++) {

                Box box = new Box();

                if ((i%2 == 0 && j%2 !=0) || (i%2 != 0 && j%2==0)){

                    box.setColor("Black");

                } else {

                    box.setColor("White");
                }

                box.setPosition(i + " " + j);
                letter = (char)(64+i);

                box.setName(Character.toString(letter) + j);
                listOfBoxes.add(box);

            }

        }

        System.out.println(listOfBoxes.get(0).getName()+ " " + listOfBoxes.get(0).getColor());

    }

    public void getBoxes(){

        for (int i = 0; i < listOfBoxes.size(); i++) {

            System.out.println(listOfBoxes.get(i).getName()+ " " + listOfBoxes.get(i).getColor());
        }
    }


    public boolean checkMoveLimits(long moveBox){

        boolean insideBoard;

        if (moveBox > this.getWidth() || moveBox > this.getHeight()){

            insideBoard = false;
            System.out.println("Out of limits");

        } else {

            insideBoard = true;
            System.out.println("Inside of limits");
        }

        return insideBoard;

    }



    //SINGLETON
    public static Board getSingletonBoard(){

        if (board == null){
            board = new Board();
        }

        return board;
    }

}
