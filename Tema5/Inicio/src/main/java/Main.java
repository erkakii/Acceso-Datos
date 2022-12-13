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
            guardar();
        } catch (Exception e) {
            System.err.println("Error: " + e);
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

    private static void guardar(){
        PlayerEntity player = new PlayerEntity("Kaki", "1234", "elminikaki@gmail.com");
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int id = (int) session.save(player);
        try{
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        System.out.println(id);
        sessionFactory.close();

    }
}
