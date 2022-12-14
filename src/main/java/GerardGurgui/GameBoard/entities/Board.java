package GerardGurgui.GameBoard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private static Board board;

    //BOARD PARAMETERS
    private String name;
    private long height;
    private long width;
    private long numberOfBoxes;

    //SPECIAL BOX
    private String specialBox;
    private String winBox;

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
    List<Box> listOfBoxes;

    private Board(){
        listOfBoxes = new ArrayList<>();
    }


    //BOARD METHODS

    public long totalNumberOfBoxes(){

        return this.height * this.width;

    }

    public List<Box> addBoxesToBoard(){

        Box box;
        char letter;

        //I rows, J columns
        for (int i = 1; i <= height; i++) {

            for (int j = 1; j <= width; j++) {

                box = new Box();
                box.setOccupied("Not occupied");

                if ((i%2 == 0 && j%2 != 0) || (i%2 != 0 && j%2 == 0)){

                    box.setColor("Black");

                } else {

                    box.setColor("White");
                }

                box.setBoxRow(i);
                box.setBoxColumn(j);
                letter = (char)(64+i);

                box.setName(Character.toString(letter) + j);
                listOfBoxes.add(box);

            }

        }

        return listOfBoxes;

    }

    public void getBoxes(){

        for (int i = 0; i < listOfBoxes.size(); i++) {

            System.out.println(listOfBoxes.get(i).getName()+ " " + listOfBoxes.get(i).getColor());
        }
    }




    //SINGLETON
    public static Board getSingletonBoard(){

        if (board == null){
            board = new Board();
        }

        return board;
    }

}
