package GerardGurgui.GameBoard.services;

import GerardGurgui.GameBoard.DTO.BoardDto;
import GerardGurgui.GameBoard.entities.Board;
import GerardGurgui.GameBoard.entities.Box;
import GerardGurgui.GameBoard.exceptions.ResourceNotFoundException;
import GerardGurgui.GameBoard.exceptions.customizedExceptions.PlayerOutOfLimitsException;
import GerardGurgui.GameBoard.game.GameFunctions;
import GerardGurgui.GameBoard.mapper.DtoToBoard;
import GerardGurgui.GameBoard.repositories.BoardRepository;
import GerardGurgui.GameBoard.repositories.BoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements Iservice<BoardDto,Board> {


    @Autowired
    private  BoardRepository boardRepository;

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private DtoToBoard dtoToBoard;

    private Board board;


    ////---- CREATE -----
    @Override
    public Board save(BoardDto boardDto){

        board = dtoToBoard.map(boardDto);

        List<Box> boxes = board.addBoxesToBoard();

        boxRepository.saveAll(boxes);

        GameFunctions.assignStartPositionBoxes(getOneBox("A1"),getOneBox("F6"));

        return boardRepository.save(board);
    }


    ////---- READ -----
    @Override
    public List<Board> getAll() {
        return null;
    }


    @Override
    public Board getOne(Long id){

        board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board","id",id));

        return board;
    }


    //GET BOXES
    public List<Box> getAllBoxes(){

        return board.getListOfBoxes();
    }

    public Box getOneBox(String namePosition){

        Box box = boxRepository.findByNameIgnoreCase(namePosition);

        if (box == null){
            throw new PlayerOutOfLimitsException(HttpStatus.BAD_REQUEST,"This box is out of the limits of the board");
        }

        return box;
    }


    ////----- UPDATE -----

    //MODIFY NAME, WIDTH AND HEIGHT
    @Override
    public void update(BoardDto boardDto, Long id){

        boolean exists = boardRepository.existsById(id);

        if (exists){

            if (boardDto.getName().isEmpty()){

                board.setName("Board Game");
            }else {
                board.setName(boardDto.getName());
            }

            board.setHeight(boardDto.getHeight());
            board.setWidth(boardDto.getWidth());
            board.setNumberOfBoxes(board.totalNumberOfBoxes());

        } else {
            throw new ResourceNotFoundException("Board","id",id);
        }

        boardRepository.save(board);

    }


    @Override
    public void delete(Long id) {

        board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board","id",id));

        boardRepository.delete(board);
    }
}
