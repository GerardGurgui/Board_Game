package GerardGurgui.GameBoard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //USER
    private String userName;
    private String password;
    private String email;
    private LocalDate signinDay;

    //POSITION
    private long axysX; //WIDTH
    private long axysY; //HEIGHT

//    //ATTRIBUTES PLAYER
    private long health;
//
//    //ATTRIBUTES GAME
//    private int percentage;
//    private int wins;
//    private int level;
    private String actualBox;


    //LIST OF THROWS
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_player", referencedColumnName = "id")
    private Set<Dice> listOfThrows;

    //LIST OF ACTIONS
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_player",referencedColumnName = "id")
    private Set<Move> listOfMoves;



    public void addThrow(Dice dice){

        if (listOfThrows.isEmpty()){
            listOfThrows = new HashSet<>();
        }

        listOfThrows.add(dice);
    }

    public void addMovement(Move move){

        if (listOfMoves.isEmpty()){
            listOfMoves = new HashSet<>();
        }

        listOfMoves.add(move);
    }
}
