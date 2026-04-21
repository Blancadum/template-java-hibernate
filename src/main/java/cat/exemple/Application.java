/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cat.exemple;

import java.math.BigDecimal;
import cat.exemple.dao.ProducteDAO;
import cat.exemple.model.Producte;
import cat.exemple.util.HibernateUtil;

/**
 *
 * @author admin
 */
public class Application {
    public static void main(String[] args) {
        ProducteDAO dao = new ProducteDAO();

        // 1. Crear un producto
        Producte p = new Producte("MacBook Pro", new BigDecimal("2500.00"), 5);
        dao.save(p);
        System.out.println("¡Producto guardado!");

        // 2. Listar productos
        dao.getAll().forEach(System.out::println);

        HibernateUtil.shutdown();
    }
}
