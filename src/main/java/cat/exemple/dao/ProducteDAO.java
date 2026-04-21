package cat.exemple.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import cat.exemple.model.Producte;
import cat.exemple.util.HibernateUtil;
import java.util.List;

// ================================================================
// PLANTILLA DAO — tots els mètodes CRUD amb Hibernate
// Per adaptar a un altre projecte:
//   1. Canvia "Producte" pel nom de la teva entitat
//   2. Canvia "producte" per la variable corresponent
//   3. Afegeix els findBy que necessitis seguint el mateix patró
// ================================================================
public class ProducteDAO {

    // INSERT — guardar objecte nou
    public Long save(Producte producte) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(producte);
            tx.commit();
            return producte.getId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException(e);
        }
    }

    // SELECT WHERE id = ? — buscar per ID (retorna null si no existeix)
    public Producte getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Producte.class, id);
        }
    }

    // SELECT * — obtenir tots
    public List<Producte> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Producte", Producte.class).list();
        }
    }

    // UPDATE — actualitzar objecte existent
    public void update(Producte producte) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(producte);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException(e);
        }
    }

    // DELETE — esborrar objecte
    public void delete(Producte producte) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Producte managed = session.merge(producte);
            session.remove(managed);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException(e);
        }
    }

    // SELECT WHERE nom LIKE %text% — cerca parcial (case-insensitive)
    public List<Producte> findByNom(String nom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Producte p WHERE LOWER(p.nom) LIKE LOWER(:nom)", Producte.class)
                .setParameter("nom", "%" + nom + "%")
                .list();
        }
    }
}
