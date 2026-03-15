package com.inventory.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.inventory.entity.Product;
import com.inventory.util.HibernateUtil;

public class ProductDAO {

    public void addProduct(Product product) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(product);

        tx.commit();
        session.close();
    }

    public Product getProduct(int id) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Product product = session.get(Product.class, id);

        session.close();

        return product;
    }

    public void updateProduct(int id, double price, int quantity) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Product product = session.get(Product.class, id);

        product.setPrice(price);
        product.setQuantity(quantity);

        session.update(product);

        tx.commit();
        session.close();
    }

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
}