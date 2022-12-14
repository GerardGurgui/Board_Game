package GerardGurgui.GameBoard.mapper;

/*
 * Convertir de jugadorDto a JugadorEntidad
 * implementamos la interfaz Imapper, recibe un jugadorDto (el que introduce el usuario)
 * nos devuelve el jugador que guardaremos en BDD
 * @component para poder inyectar en las clases donde necesitamos
 * */

import GerardGurgui.GameBoard.DTO.PlayerDto;
import GerardGurgui.GameBoard.entities.Player;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DtoToPlayer implements IMapper<PlayerDto, Player>{


    @Override
    public Player map(PlayerDto playerDto) {

        Player playerEntity = new Player();

        //CHECK FOR EMPTY USERNAME, DEFAULT = ANONYMOUS
        if (playerDto.getUserName().isEmpty()){
            playerEntity.setUserName("Anonymous");
        }else {
            playerEntity.setUserName(playerDto.getUserName());
        }

        playerEntity.setEmail(playerDto.getEmail());
        playerEntity.setPassword(playerDto.getPassword());
        playerEntity.setSigninDay(LocalDate.now());

        playerEntity.setHealth(100);

        return playerEntity;

    }
}
