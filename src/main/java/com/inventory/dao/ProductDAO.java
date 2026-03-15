package com.inventory.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.inventory.entity.Product;
import com.inventory.util.HibernateUtil;

public class ProductDAO {

    // INSERT PRODUCT
    public void addProduct(Product product) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(product);

        tx.commit();
        session.close();
    }

    // GET PRODUCT BY ID
    public Product getProduct(int id) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Product product = session.get(Product.class, id);

        session.close();

        return product;
    }

    // UPDATE PRODUCT
    public void updateProduct(int id, double price, int quantity) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Product product = session.get(Product.class, id);

        if(product != null)
        {
            product.setPrice(price);
            product.setQuantity(quantity);

            session.update(product);
        }

        tx.commit();
        session.close();
    }

    // DELETE PRODUCT
    public void deleteProduct(int id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Product p = session.get(Product.class, id);

        if(p != null)
        {
            session.delete(p);
            System.out.println("Product Deleted");
        }
        else
        {
            System.out.println("Product not found");
        }

        tx.commit();
        session.close();
    }

    // =============================
    // HQL QUERIES (SKILL 3)
    // =============================

    // SORT BY PRICE ASC
    public void sortPriceAsc() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Product> list = session.createQuery(
                "from Product order by price asc", Product.class).list();

        list.forEach(p -> System.out.println(p.getName()+" "+p.getPrice()));

        session.close();
    }

    // SORT BY PRICE DESC
    public void sortPriceDesc() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Product> list = session.createQuery(
                "from Product order by price desc", Product.class).list();

        list.forEach(p -> System.out.println(p.getName()+" "+p.getPrice()));

        session.close();
    }

    // SORT BY QUANTITY (HIGHEST FIRST)
    public void sortQuantity() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Product> list = session.createQuery(
                "from Product order by quantity desc", Product.class).list();

        list.forEach(p -> System.out.println(p.getName()+" "+p.getQuantity()));

        session.close();
    }

    // =============================
    // PAGINATION
    // =============================

    // FIRST 3 PRODUCTS
    public void firstThree() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Product> list = session.createQuery("from Product", Product.class)
                .setFirstResult(0)
                .setMaxResults(3)
                .list();

        list.forEach(p -> System.out.println(p.getName()));

        session.close();
    }

    // NEXT 3 PRODUCTS
    public void nextThree() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Product> list = session.createQuery("from Product", Product.class)
                .setFirstResult(3)
                .setMaxResults(3)
                .list();

        list.forEach(p -> System.out.println(p.getName()));

        session.close();
    }

    // =============================
    // AGGREGATE FUNCTIONS
    // =============================

    // TOTAL PRODUCTS
    public void totalProducts() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Long count = (Long) session.createQuery(
                "select count(*) from Product").uniqueResult();

        System.out.println("Total Products: "+count);

        session.close();
    }

    // COUNT PRODUCTS WHERE QUANTITY > 0
    public void availableProducts() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Long count = (Long) session.createQuery(
                "select count(*) from Product where quantity > 0").uniqueResult();

        System.out.println("Available Products: "+count);

        session.close();
    }

    // GROUP BY DESCRIPTION
    public void groupByDescription() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Object[]> list = session.createQuery(
                "select description, count(*) from Product group by description")
                .list();

        for(Object[] row : list)
        {
            System.out.println(row[0]+" : "+row[1]);
        }

        session.close();
    }

    // MIN AND MAX PRICE
    public void minMaxPrice() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Object[] result = (Object[]) session.createQuery(
                "select min(price), max(price) from Product").uniqueResult();

        System.out.println("Min Price: "+result[0]);
        System.out.println("Max Price: "+result[1]);

        session.close();
    }

    // =============================
    // FILTER USING WHERE
    // =============================

    public void priceRange(double min, double max) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Product> list = session.createQuery(
                "from Product where price between :min and :max", Product.class)
                .setParameter("min", min)
                .setParameter("max", max)
                .list();

        list.forEach(p -> System.out.println(p.getName()+" "+p.getPrice()));

        session.close();
    }

    // =============================
    // LIKE QUERIES
    // =============================

    // NAME STARTS WITH
    public void nameStartsWith(String letter)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Product> list = session.createQuery(
                "from Product where name like :letter", Product.class)
                .setParameter("letter", letter+"%")
                .list();

        list.forEach(p -> System.out.println(p.getName()));

        session.close();
    }

    // NAME ENDS WITH
    public void nameEndsWith(String letter)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Product> list = session.createQuery(
                "from Product where name like :letter", Product.class)
                .setParameter("letter", "%"+letter)
                .list();

        list.forEach(p -> System.out.println(p.getName()));

        session.close();
    }

    // NAME CONTAINS
    public void nameContains(String word)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Product> list = session.createQuery(
                "from Product where name like :word", Product.class)
                .setParameter("word", "%"+word+"%")
                .list();

        list.forEach(p -> System.out.println(p.getName()));

        session.close();
    }

    // NAME EXACT LENGTH
    public void nameLength(int length)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Product> list = session.createQuery(
                "from Product where length(name)=:len", Product.class)
                .setParameter("len", length)
                .list();

        list.forEach(p -> System.out.println(p.getName()));

        session.close();
    }
}