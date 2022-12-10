package GerardGurgui.GameBoard.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BoardDto implements Serializable {

    private String name;
    private Long height;
    private Long width;
    private Long winBox;


}
