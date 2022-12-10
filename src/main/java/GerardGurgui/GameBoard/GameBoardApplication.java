package GerardGurgui.GameBoard;

import GerardGurgui.GameBoard.entities.Board;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GameBoardApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(GameBoardApplication.class, args);

	}


}
