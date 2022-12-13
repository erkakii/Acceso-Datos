import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Player")
public class PlayerEntity implements Serializable {

    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Se puede poner sin los parentesis pero es recomendable ponerselos
    @Column(name = "idPlayer")
    private int idPlayer;
    @Column(name = "Nick")
    private String Nick;
    @Column(name = "password")
    private String password;
    @Column(name = "email") //Esto indica a que columna hace referencia el atributo en caso de cambiarle el nombre al atributo
    private String correoElectronico;

    //endregion

    //region Constructores
    public PlayerEntity(String Nombre, String password, String email){
        this.Nick = Nombre;
        this.password = password;
        this.correoElectronico = email;
    }

    public PlayerEntity() {

    }


    //endregion

    //region Propiedades
    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getNick() {
        return Nick;
    }

    public void setNick(String nick) {
        Nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    //endregion

    //region Metodos
    //endregion
}
