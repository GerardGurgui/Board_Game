package GerardGurgui.GameBoard.services;

import GerardGurgui.GameBoard.DTO.PlayerDto;
import GerardGurgui.GameBoard.entities.Box;
import GerardGurgui.GameBoard.entities.Dice;
import GerardGurgui.GameBoard.entities.Move;
import GerardGurgui.GameBoard.entities.Player;
import GerardGurgui.GameBoard.exceptions.ResourceNotFoundException;
import GerardGurgui.GameBoard.exceptions.customizedExceptions.DicesPlayerException;
import GerardGurgui.GameBoard.exceptions.customizedExceptions.ExistentEmailException;
import GerardGurgui.GameBoard.exceptions.customizedExceptions.ExistentUserNameException;
import GerardGurgui.GameBoard.exceptions.customizedExceptions.PlayerNotFoundException;
import GerardGurgui.GameBoard.game.GameFunctions;
import GerardGurgui.GameBoard.mapper.DtoToPlayer;
import GerardGurgui.GameBoard.repositories.BoxRepository;
import GerardGurgui.GameBoard.repositories.DiceRepository;
import GerardGurgui.GameBoard.repositories.MoveRepository;
import GerardGurgui.GameBoard.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class PlayerServiceImpl implements Iservice<PlayerDto, Player>{


    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private DiceRepository diceRepository;

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private BoardServiceImpl boardService;

    @Autowired
    private DtoToPlayer dtoToPlayer;


    ///---CREATE
    @Override
    public Player save(PlayerDto playerDto) {

        Player playerEntity = dtoToPlayer.map(playerDto);

        //CHECK REPEAT USER NAME
        boolean existentUserName = playerRepository.existsByUserName(playerEntity.getUserName());

        if (existentUserName && !playerEntity.getUserName().equalsIgnoreCase("Anonymous")){

            throw new ExistentUserNameException(HttpStatus.FOUND,"This user name is already taken");

        }



        return playerRepository.save(playerEntity);

    }

    ///---READ

    @Override
    public List<Player> getAll() {

        return playerRepository.findAll();
    }

    @Override
    public Player getOne(Long id) {

        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player","id",id));

        return player;
    }

    ///---UPDATE

    @Override
    public void update(PlayerDto playerDto, Long id) {

        //CHECK USERNAME
        if (playerRepository.existsByUserName(playerDto.getUserName())){

            throw new ExistentUserNameException(HttpStatus.FOUND,"The user name is already taken");
        }

        //CHECK EMAIL
        if (playerRepository.existsByEmail(playerDto.getEmail())){

            throw new ExistentEmailException(HttpStatus.FOUND,"The Email is already in use ");
        }

        //CHECK IF THE PLAYER TO UPDATE EXISTS
        if (!playerRepository.existsById(id)) {

            throw new PlayerNotFoundException(HttpStatus.NOT_FOUND,"The player doesn't exist");
        }

        Player playerEntity = playerRepository.findById(id).get();


        playerEntity.setUserName(playerDto.getUserName());
        playerEntity.setEmail(playerDto.getEmail());

        playerRepository.save(playerEntity);
    }



    //----DELETE----

    @Override
    public void delete(Long id) {

        //CHECK PLAYER EXISTS
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player","id",id));

        playerRepository.delete(player);

    }



    //DELETE THROWS PLAYER
    public void deleteThrows(Long id) {

        //CHECK IF PLAYER EXISTS
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player","id",id));


        //CHECK IF PLAYER HAVE THROWS
        if (player.getListOfThrows().isEmpty()){

            throw new DicesPlayerException(HttpStatus.NO_CONTENT,"The player with ID: " +id+ "doesn't have throws");

        }

        //DELETE ALL THROWS OF THIS PLAYER
        Set<Dice> dices = player.getListOfThrows();
        Long idLaunch;

        for (Dice dice : dices) {

            idLaunch = dice.getId();
            diceRepository.deleteById(idLaunch);

        }

        player.getListOfThrows().clear();

        //SAVE CHANGES
        playerRepository.save(player);

    }

    //PLAYER ACTIONS-- LAUNCH DICES
    public Dice launchDices(Long id) {

        //CHECK PLAYER EXISTS
        Player player = getOne(id);



        //---- VALIDAR QUE HAYA MINIMO 2 JUGADORES

        //DICES
        Dice dice = new Dice();

        //THROW DICES
        dice.launchDice1();
        dice.launchDice2();

        //ADD THROW TO PLAYER
        player.addThrow(dice);
        diceRepository.save(dice);

        movePlayer(player, dice);

        return dice;

    }

    //MOVE PLAYER
    public void movePlayer(Player player, Dice dice) {

        //ACTIONS - MOVE
        //ACTUAL BOX PLAYER
        String actualBoxPosition = player.getActualBox();
        Box actualBox = boardService.getOneBox(actualBoxPosition);

        //NEW BOX
        char letter = dice.getDice1();
        long number = dice.getDice2();

        String newBoxPosition = (letter+""+number);

        Box box = boardService.getOneBox(newBoxPosition);

        //CHECK LIMIT MOVE PLAYER INSIDE BOARD
        Move move = new Move();
        boolean playerMoves = move.movePlayer(player, dice, box);

        if(playerMoves){

            actualBox.setOccupied("Void");
        }



        //ADD MOVE TO PLAYER
        player.addMovement(move);
        moveRepository.save(move);
        playerRepository.save(player);
        boxRepository.save(box);


    }
}
