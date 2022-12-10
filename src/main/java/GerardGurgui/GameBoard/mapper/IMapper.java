package GerardGurgui.GameBoard.mapper;

/*
 Interfaz generica para mapear las entidades a DTO y viceversa
 * Input Output
 */

public interface IMapper <I,O>{

    O map(I input);

}
