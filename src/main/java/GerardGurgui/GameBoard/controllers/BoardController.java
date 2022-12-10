package GerardGurgui.GameBoard.controllers;

import GerardGurgui.GameBoard.DTO.BoardDto;
import GerardGurgui.GameBoard.controllers.utils.MessageResponseEntity;
import GerardGurgui.GameBoard.entities.Board;
import GerardGurgui.GameBoard.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/create")
    public Board createBoard(@RequestBody BoardDto boardDto){

        return boardService.createBoard(boardDto);

    }


    @GetMapping("/get/{id}")
    public Board getBoard(@PathVariable Long id){

        return boardService.getBoard(id);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponseEntity>modifyBoard(@RequestBody BoardDto boardDto,
                                                            @PathVariable Long id){

        boardService.updateBoard(boardDto,id);

        return new ResponseEntity<>(new MessageResponseEntity("Board parameters modified"), HttpStatus.OK);

    }

}
