package GerardGurgui.GameBoard.services;

import GerardGurgui.GameBoard.DTO.PlayerDto;
import GerardGurgui.GameBoard.entities.*;
import GerardGurgui.GameBoard.exceptions.ResourceNotFoundException;
import GerardGurgui.GameBoard.exceptions.customizedExceptions.DicesPlayerException;
import GerardGurgui.GameBoard.exceptions.customizedExceptions.ExistentEmailException;
import GerardGurgui.GameBoard.exceptions.customizedExceptions.ExistentUserNameException;
import GerardGurgui.GameBoard.exceptions.customizedExceptions.PlayerNotFoundException;
import GerardGurgui.GameBoard.mapper.DtoToPlayer;
import GerardGurgui.GameBoard.repositories.DiceRepository;
import GerardGurgui.GameBoard.repositories.MoveRepository;
import GerardGurgui.GameBoard.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private DiceRepository diceRepository;

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private DtoToPlayer dtoToPlayer;

    @Autowired
    private BoardService boardService;




    //// -------------CRUD-----------

    //----CREATE----
    public Player createPlayer(PlayerDto playerDto){

        Player playerEntity = dtoToPlayer.map(playerDto);


        //CHECK REPEAT USER NAME
        boolean existentUserName = playerRepository.existsByUserName(playerEntity.getUserName());

        if (existentUserName && !playerEntity.getUserName().equalsIgnoreCase("Anonymous")){

            throw new ExistentUserNameException(HttpStatus.FOUND,"This user name is already taken");

        }

        return playerRepository.save(playerEntity);

    }


    //----READ----
    public List<Player> getAll(){

        return playerRepository.findAll();
    }


    public Player getOne(Long id) {

        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player","id",id));

        return player;
    }



    //----UPDATE----

    public void update(PlayerDto playerDTO, Long id) {

        //CHECK USER NAME
        if (playerRepository.existsByUserName(playerDTO.getUserName())){

            throw new ExistentUserNameException(HttpStatus.FOUND,"The user name is already taken");
        }

        //CHECK EMAIL
        if (playerRepository.existsByEmail(playerDTO.getEmail())){

            throw new ExistentEmailException(HttpStatus.FOUND,"The Email is already in use ");
        }

        //CHECK IF THE PLAYER TO UPDATE EXISTS
        if (!playerRepository.existsById(id)) {

            throw new PlayerNotFoundException(HttpStatus.NOT_FOUND,"The player doesn't exist");
        }

        Player playerEntity = playerRepository.findById(id).get();


        playerEntity.setUserName(playerDTO.getUserName());
        playerEntity.setEmail(playerDTO.getEmail());

        playerRepository.save(playerEntity);

    }



    //----DELETE----

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



    public void deletePlayer(Long id) {

        //CHECK PLAYER EXISTS
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player","id",id));

        playerRepository.delete(player);

    }

    public void deleteAllPLayers(){

        playerRepository.deleteAll();
    }



}
