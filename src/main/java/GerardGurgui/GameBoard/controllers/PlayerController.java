package GerardGurgui.GameBoard.controllers;


import GerardGurgui.GameBoard.DTO.PlayerDto;
import GerardGurgui.GameBoard.controllers.utils.MessageResponseEntity;
import GerardGurgui.GameBoard.entities.Dice;
import GerardGurgui.GameBoard.entities.Player;
import GerardGurgui.GameBoard.services.PlayerActionService;
import GerardGurgui.GameBoard.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerActionService playerActionService;


    ////------------CRUD-----------

    //------CREATE------

    @PostMapping("/add")
    public ResponseEntity<MessageResponseEntity> addPlayer(@RequestBody PlayerDto playerDto){

        playerService.createPlayer(playerDto);

        return new ResponseEntity<>(new MessageResponseEntity("Player created succesfully"),HttpStatus.CREATED);

    }


    //------READ------

    @GetMapping("/findAll")
    public List<Player> getAllPlayers(){

        return playerService.getAll();
    }

    @GetMapping("/findOne/{id}")
    public Player getOnePlayer(@PathVariable Long id){

        return playerService.getOne(id);

    }


    //------UPDATE------

    @PutMapping("/updatePlayer/{id}")
    public ResponseEntity<MessageResponseEntity> updatePlayer(@RequestBody PlayerDto playerDTO,
                                               @PathVariable Long id){

        playerService.update(playerDTO,id);

        return new ResponseEntity<>(new MessageResponseEntity("Player updated succesfully"), HttpStatus.OK);

    }


    //------DELETE------

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<MessageResponseEntity> deleteOnePlayer(@PathVariable Long id){

       playerService.deletePlayer(id);

       return new ResponseEntity<>(new MessageResponseEntity("Player deleted successfully"),HttpStatus.OK);

    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<MessageResponseEntity> deleteAllPlayers(){

        playerService.deleteAllPLayers();

        return new ResponseEntity<>(new MessageResponseEntity("All players have been deleted"),HttpStatus.OK);

    }

    @DeleteMapping("/deleteThrows/{id}")
    public ResponseEntity<MessageResponseEntity> deleteThrowsPlayer(@PathVariable Long id){

        playerService.deleteThrows(id);

        return new ResponseEntity<>(new MessageResponseEntity("All throws deleted from player with ID " +id), HttpStatus.OK);

    }




    //GAME METHODS - ACTIONS

    //DICES
    @PostMapping("/throwDices/{id}")
    public ResponseEntity<Dice> launchDicesPlayer(@PathVariable Long id) {

        return ResponseEntity.ok(playerActionService.launchDices(id));

    }
}
