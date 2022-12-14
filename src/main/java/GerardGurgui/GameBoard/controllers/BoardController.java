package GerardGurgui.GameBoard.controllers;

import GerardGurgui.GameBoard.DTO.BoardDto;
import GerardGurgui.GameBoard.controllers.utils.MessageResponseEntity;
import GerardGurgui.GameBoard.entities.Board;
import GerardGurgui.GameBoard.entities.Box;
import GerardGurgui.GameBoard.services.BoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardServiceImpl boardServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<MessageResponseEntity> createBoard(@RequestBody BoardDto boardDto){

        boardServiceImpl.save(boardDto);

        return new ResponseEntity<>(new MessageResponseEntity("Board created, the game starts"), HttpStatus.CREATED);

    }


    @GetMapping("/get/{id}")
    public Board getBoard(@PathVariable Long id){

        return boardServiceImpl.getOne(id);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponseEntity>modifyBoard(@RequestBody BoardDto boardDto,
                                                            @PathVariable Long id){

        boardServiceImpl.update(boardDto,id);

        return new ResponseEntity<>(new MessageResponseEntity("Board parameters modified"), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponseEntity> deleteBoard(@PathVariable Long id){

       boardServiceImpl.delete(id);

        return new ResponseEntity<>(new MessageResponseEntity("Board deleted"), HttpStatus.NO_CONTENT);

    }


    ////- BOXES
    //GET

    @GetMapping("/boxes/getAll")
    public List<Box> getAllBoxesFromBoard(){

        return boardServiceImpl.getAllBoxes();

    }

    @GetMapping("/boxes/getOne/{namePosition}")
    public Box getOneBox(@PathVariable String namePosition){

        return boardServiceImpl.getOneBox(namePosition);
    }

}
