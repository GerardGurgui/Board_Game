//package GerardGurgui.GameBoard.services;
//
//import GerardGurgui.GameBoard.entities.Dice;
//import GerardGurgui.GameBoard.entities.Player;
//import GerardGurgui.GameBoard.entities.Move;
//import GerardGurgui.GameBoard.exceptions.ResourceNotFoundException;
//import GerardGurgui.GameBoard.exceptions.customizedExceptions.PlayerOutOfLimitsException;
//import GerardGurgui.GameBoard.repositories.DiceRepository;
//import GerardGurgui.GameBoard.repositories.MoveRepository;
//import GerardGurgui.GameBoard.repositories.PlayerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MoveService {
//
//    @Autowired
//    private PlayerServiceImpl playerService;
//
//    @Autowired
//    private DiceRepository diceRepository;
//
//    @Autowired
//    private MoveRepository moveRepository;
//
//
//    //PLAYER ACTIONS-- LAUNCH DICES
//    public Dice launchDices(Long id) {
//
//        //CHECK PLAYER EXISTS
//        Player player = playerService.getOne(id);
//
//        //---- VALIDAR QUE HAYA MINIMO 2 JUGADORES
//
//        //DICES
//        Dice dice = new Dice();
//
//        //THROW DICES
//        dice.launchDice1();
//        dice.launchDice2();
//
//        //ADD THROW TO PLAYER
//        player.addThrow(dice);
//        diceRepository.save(dice);
//
//        movePlayer(player, dice);
//
//        return dice;
//
//    }
//
//    //MOVE PLAYER
//    public void movePlayer(Player player, Dice dice) {
//
//        //ACTIONS - MOVE
//        Move move = new Move();
//
//        //CHECK LIMIT MOVE PLAYER INSIDE BOARD
//        move.movePlayer(player, dice);
//
//
//        //ADD MOVE TO PLAYER
//        player.addMovement(move);
//        moveRepository.save(move);
//        playerRepository.save(player);
//
//
//    }
//}
