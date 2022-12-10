package GerardGurgui.GameBoard.mapper;

import GerardGurgui.GameBoard.DTO.BoardDto;
import GerardGurgui.GameBoard.entities.Board;
import GerardGurgui.GameBoard.entities.Player;
import GerardGurgui.GameBoard.game.GameFunctions;
import GerardGurgui.GameBoard.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoToBoard implements IMapper<BoardDto, Board>{

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Board map(BoardDto boardDto) {

        Board board = Board.getSingletonBoard();

        if (boardDto.getName().isEmpty()){
            board.setName("Board Game");

        } else{
            board.setName(boardDto.getName());
        }

        if (boardDto.getHeight() == null){
            board.setHeight(GameFunctions.numRandomBoard());

        } else {
            board.setHeight(boardDto.getHeight());
        }

        if (boardDto.getWidth() == null){
            board.setWidth(GameFunctions.numRandomBoard());

        } else {
            board.setWidth(boardDto.getHeight());
        }

        if (boardDto.getWinBox() == null){
            board.setWinBox(GameFunctions.numRandomBoard());

        }else {
            board.setWinBox(boardDto.getWinBox());
        }

        board.setNumberOfBoxes(board.totalNumberOfBoxes());

        Player player1 = playerRepository.findById(1L).get();
        Player player2 = playerRepository.findById(2L).get();

        board.setFirstPlayer(player1);
        board.setSecondPlayer(player2);

        return board;

    }
}
