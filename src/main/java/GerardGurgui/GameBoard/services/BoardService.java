package GerardGurgui.GameBoard.services;

import GerardGurgui.GameBoard.DTO.BoardDto;
import GerardGurgui.GameBoard.entities.Board;
import GerardGurgui.GameBoard.entities.Player;
import GerardGurgui.GameBoard.exceptions.ResourceNotFoundException;
import GerardGurgui.GameBoard.game.GameFunctions;
import GerardGurgui.GameBoard.mapper.DtoToBoard;
import GerardGurgui.GameBoard.repositories.BoardRepository;
import GerardGurgui.GameBoard.repositories.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private static final Logger log = LoggerFactory.getLogger(GameFunctions.class);

    @Autowired
    private  BoardRepository boardRepository;

    @Autowired
    private DtoToBoard dtoToBoard;

    private Board board;


    ////---- CREATE -----
    public Board createBoard(BoardDto boardDto){

        board = dtoToBoard.map(boardDto);

        board.addBoxesToBoard();

        return boardRepository.save(board);
    }


    ////---- READ -----


    public Board getBoard(Long id){

        board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board","id",id));

        return board;
    }

    ////----- UPDATE -----

    //MODIFY NAME, WIDTH AND HEIGHT
    public void updateBoard(BoardDto boardDto, Long id){

        boolean exists = boardRepository.existsById(id);

        if (exists){

            if (boardDto.getName().isEmpty()){

                board.setName("Board Game");
            }else {
                board.setName(boardDto.getName());
            }

            board.setHeight(boardDto.getHeight());
            board.setWidth(boardDto.getWidth());

        } else {
            throw new ResourceNotFoundException("Board","id",id);
        }

        boardRepository.save(board);

    }









}
