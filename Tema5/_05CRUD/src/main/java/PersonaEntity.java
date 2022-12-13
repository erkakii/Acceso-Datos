import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Personas")
public class PersonaEntity implements Serializable {

    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPersona")
    private int IdPlayer;

    @Column(name = "Nombre")
    private String Nombre;
    @Column(name = "Apellido")
    private String Apellido;

    //endregion

    //region Propiedades

    public int getIdPlayer() {
        return IdPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        IdPlayer = idPlayer;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    //endregion


    //region Constructores

    public PersonaEntity(String nombre, String apellido) {
        this.Nombre = nombre;
        this.Apellido = apellido;
    }

    public PersonaEntity(){}

    //endregion


    //region Metodos
    //endregion

}
