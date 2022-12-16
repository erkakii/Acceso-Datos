import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {

    private static SessionFactory sessionFactory;
    public static void main(String[] args) {
        try {
            setUp();
            insertarPersona("Alvaro", "maceta");
            seleccionarPersona();
            borrarPersona("Maceta", "Cuadrada");
            actualizarPersona();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        sessionFactory.close();
    }

    private static void seleccionarPersona() {
    }

    private static void actualizarPersona() {
    }

    private static void borrarPersona(String nombre, String apellido) {
        PersonaEntity persona = new PersonaEntity(nombre, apellido);
        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        sesion.delete(persona);
        sesion.getTransaction().commit();
        sesion.close();



    }

    private static void insertarPersona(String nombre, String apellido) {
        PersonaEntity persona = new PersonaEntity(nombre, apellido);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int id = (int) session.save(persona);
        try {
            transaction.commit();
            System.out.println("Inserción realizada con éxito");
        }catch (Exception e){
            transaction.rollback();
            System.out.println("Ha ocurrido un problema la persona no se ha podido insertar");
        }
    }

    protected static void setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // por defecto: hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }
}
