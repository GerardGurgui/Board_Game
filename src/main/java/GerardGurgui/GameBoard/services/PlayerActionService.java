package GerardGurgui.GameBoard.services;

import GerardGurgui.GameBoard.entities.Board;
import GerardGurgui.GameBoard.entities.Dice;
import GerardGurgui.GameBoard.entities.Player;
import GerardGurgui.GameBoard.entities.PlayerAction;
import GerardGurgui.GameBoard.exceptions.ResourceNotFoundException;
import GerardGurgui.GameBoard.exceptions.customizedExceptions.PlayerOutOfLimitsException;
import GerardGurgui.GameBoard.repositories.BoardRepository;
import GerardGurgui.GameBoard.repositories.DiceRepository;
import GerardGurgui.GameBoard.repositories.PlayerActionRepository;
import GerardGurgui.GameBoard.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PlayerActionService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private DiceRepository diceRepository;

    @Autowired
    private PlayerActionRepository playerActionRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;



    //PLAYER ACTIONS-- LAUNCH DICES
    public Dice launchDices(Long id){

        //CHECK PLAYER EXISTS
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player","id",id));

        //---- VALIDAR QUE HAYA MINIMO 2 JUGADORES

        //DICES
        Dice dice = new Dice();

        //THROW DICES
        dice.launch();

        //ADD THROW TO PLAYER
        player.addThrow(dice);
        diceRepository.save(dice);

        movePlayer(player,dice);

        return dice;

    }

    //MOVE PLAYER
    public void movePlayer(Player player, Dice dice){

        //ACTIONS - MOVE
        PlayerAction move = new PlayerAction();

        //CHECK LIMIT MOVE PLAYER INSIDE BOARD
        boolean insideBoard  = move.movePlayer(player,dice);

        if (insideBoard){

            //ADD MOVE TO PLAYER
            player.addMovement(move);
            playerActionRepository.save(move);
            playerRepository.save(player);

        } else {

            //ARREGLAR O EL JUGADOR PIERDE LA PARTIDA SI CAE DEL TABLERO??
            throw new PlayerOutOfLimitsException(HttpStatus.BAD_REQUEST,"The player is out of the board limits");
        }


    }
}
