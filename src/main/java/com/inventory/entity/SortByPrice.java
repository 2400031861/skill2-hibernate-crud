import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;

public class SortByPrice {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();

        Session session = factory.openSession();

        session.beginTransaction();

        Query query = session.createQuery("from Product order by price asc");

        List<Product> list = query.list();

        for(Product p : list){
            System.out.println(p.getName()+" "+p.getPrice());
        }

        session.getTransaction().commit();
        factory.close();
    }
}