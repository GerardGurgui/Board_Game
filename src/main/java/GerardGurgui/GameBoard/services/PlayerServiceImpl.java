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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class PlayerServiceImpl implements Iservice <PlayerDto, Player>{

    private static final Logger log = LoggerFactory.getLogger(GameFunctions.class);

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
    public void save(PlayerDto playerDto) {

        Player playerEntity = dtoToPlayer.map(playerDto);

        //CHECK REPEAT USER NAME
        boolean existentUserName = playerRepository.existsByUserName(playerEntity.getUserName());

        if (existentUserName && !playerEntity.getUserName().equalsIgnoreCase("Anonymous")){

            throw new ExistentUserNameException(HttpStatus.FOUND,"This user name is already taken");

        }

        playerRepository.save(playerEntity);

    }

    ///---READ

    @Override
    public List<Player> getAll() {

        return playerRepository.findAll();
    }

    @Override
    public Player getOne(Long id) {

        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player","id",id));
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

        for (Dice dice : dices) {

            diceRepository.delete(dice);
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

        //NEW MOVE, PLAYER, DICE AND NEXT BOX
        Move move = new Move();
        boolean playerMoves = move.movePlayer(player, dice, box);

        //IF THE PLAYER CAN MOVE TO THE NEXT BOX, MAKE ACTUAL BOX VOID
        if(playerMoves){

            player.addMovement(move);
            actualBox.setOccupied("Not occupied");
        }

        //ADD MOVE TO PLAYER
        moveRepository.save(move);
        playerRepository.save(player);
        boxRepository.save(box);


        //6- VERIFICAR POSICIÓN DEL OTRO JUGADOR?? PAR PODER ATACAR O NO SE, QUIZÁ CON RETURN Y DESDE SERVICIOS
        checkPositionsPlayers();

    }

    public void checkPositionsPlayers(){

        Player player1 = getOne(1L);
        String actualBoxP1 = player1.getActualBox();
        Box player1ActualBox = boardService.getOneBox(actualBoxP1);


        Player player2 = getOne(2L);
        String actualBoxP2 = player2.getActualBox();
        Box player2ActualBox = boardService.getOneBox(actualBoxP2);



        if ((player1ActualBox.getBoxRow()+1) == (player2ActualBox.getBoxRow())){

            log.warn("jugadores a 1 fila de distancia 1R IF");

            if ((player1ActualBox.getBoxColumn()+1) == (player2ActualBox.getBoxColumn())
                    || (player1ActualBox.getBoxColumn()) == (player2ActualBox.getBoxColumn())){

                log.warn("jugadores a 1 columna de distancia 2n IF");

            }

            if ((player1ActualBox.getBoxColumn()-1) == (player2ActualBox.getBoxColumn())
                    || (player1ActualBox.getBoxColumn()) == (player2ActualBox.getBoxColumn())){

                log.warn("jugadores a -1 columna de distancia 3r IF");

            }



        }

        if ((player1ActualBox.getBoxRow()-1) == (player2ActualBox.getBoxRow())){

            log.warn("Jugadores a -1 fila de distancia 4t IF");

            if ((player1ActualBox.getBoxColumn()-1) == (player2ActualBox.getBoxColumn())
                    || (player1ActualBox.getBoxColumn()) == (player2ActualBox.getBoxColumn())){

                log.warn("jugadores a -1 columna de distancia 5e IF");

            }

            if ((player1ActualBox.getBoxColumn()+1) == (player2ActualBox.getBoxColumn())
                    || (player1ActualBox.getBoxColumn()) == (player2ActualBox.getBoxColumn())){

                log.warn("jugadores a 1 columna de distancia 6r IF");

            }

        }








    }
}
