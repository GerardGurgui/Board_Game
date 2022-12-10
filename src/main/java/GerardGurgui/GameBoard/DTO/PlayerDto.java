package GerardGurgui.GameBoard.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PlayerDto implements Serializable {

    private String userName;
    private String password;
    private String email;

}
