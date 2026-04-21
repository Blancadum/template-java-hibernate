package cat.exemple.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import cat.exemple.model.Producte;
import cat.exemple.util.HibernateUtil;
import java.util.List;

public class ProducteDAO {
    public void save(Producte producte) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(producte);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Producte> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Producte", Producte.class).list();
        }
    }
}