package com.mamh.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class CustomerTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void init() {
        String name = "jpa-1";
        entityManagerFactory = Persistence.createEntityManagerFactory(name);

        entityManager = entityManagerFactory.createEntityManager();

        transaction = entityManager.getTransaction();

        transaction.begin();
    }

    @After
    public void destroy() {
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testOnetoOneFind1() {
        //这里获取不维护关联关系的一方，这里 (fetch = FetchType.LAZY)设置不设置这个都是使用的左外链接。发送2条sql语句。

        Manager manager = entityManager.find(Manager.class, 3);
        System.out.println(manager.getMgrName());
        System.out.println(manager.getDeptartment().getClass().getName());
    }

    @Test
    public void testOnetoOneFind() {
        //加上    @OneToOne(fetch = FetchType.LAZY) 就不会使用左外链接了,这样只会发送一条sql语句。
        Department department = entityManager.find(Department.class, 3);
        System.out.println(department.getDeptName());
        System.out.println(department.getManager().getClass().getName());

    }

    @Test
    public void testOnetoOneSave() {
        Manager manager = new Manager();
        manager.setMgrName("bright.ma");
        Department department = new Department();
        department.setDeptName("scm");
        department.setManager(manager);
        manager.setDeptartment(department);

        //先保存不维护关联关系的那一方
        entityManager.persist(manager);
        entityManager.persist(department);
    }

    /**
     * 双向一对多关联关系
     */
    @Test
    public void testOneToMany2() {
        Customer customer = new Customer();
        customer.setAge(112);
        customer.setLastName("aa");
        customer.setEmail("aa");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Order order = new Order();
        order.setOderName("o-ff-1");
        Order order1 = new Order();
        order1.setOderName("o-ff-2");

        customer.getOrders().add(order);
        customer.getOrders().add(order1);
        order1.setCustomer(customer);
        order.setCustomer(customer);

        entityManager.persist(customer);
        entityManager.persist(order);
        entityManager.persist(order1);

    }


    @Test
    public void testOneToMany1() {
        Customer customer = entityManager.find(Customer.class, 7);
        System.out.println(customer);

    }

    @Test
    public void testOneToMany() {
        Customer customer = new Customer();
        customer.setAge(112);
        customer.setLastName("zz");
        customer.setEmail("zzzzz");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Order order = new Order();
        order.setOderName("o-ff-1");
        Order order1 = new Order();
        order1.setOderName("o-ff-2");

        customer.getOrders().add(order);
        customer.getOrders().add(order1);

        entityManager.persist(customer);
        entityManager.persist(order);
        entityManager.persist(order1);

    }

    @Test
    public void testManyToOne2() {
        Customer customer = entityManager.find(Customer.class, 6);
        entityManager.remove(customer);


    }

    @Test
    public void testManyToOne1() {
        Order order = entityManager.find(Order.class, 1);
        System.out.println(order);
    }

    @Test
    public void testManyToOne() {
        //多对一映射关联关系
        //一个customer可以有多个order。
        Customer customer = new Customer();
        customer.setAge(112);
        customer.setLastName("b");
        customer.setEmail("b");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Order order = new Order();
        order.setOderName("o-ff-1");
        Order order1 = new Order();
        order1.setOderName("o-ff-2");
        //order.setCustomer(customer);
        //order1.setCustomer(customer);
        entityManager.persist(customer);

        entityManager.persist(order);
        entityManager.persist(order1);
    }

    @Test
    public void testFind() {
        Customer customer = entityManager.find(Customer.class, 1);
        System.out.println(customer);
    }

    @Test
    public void testRef() {
        Customer customer = entityManager.getReference(Customer.class, 1);
        System.out.println("----------------------");
        System.out.println(customer);

    }

    @Test
    public void testRemove() {
        //Customer customer = new Customer();
        //customer.setId(123);
        Customer customer = entityManager.find(Customer.class, 1);
        entityManager.remove(customer);

        //System.out.println(customer);

        //entityManager.persist(customer);
    }

    @Test
    public void testMerge() {
        Customer customer = new Customer();
        customer.setAge(12);
        customer.setLastName("Tom......");
        customer.setEmail("email.....");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Customer merge = entityManager.merge(customer);

        System.out.println(customer);
        System.out.println(merge);
    }

    @Test
    public void testMerge1() {
        Customer customer = new Customer();
        customer.setAge(12);
        customer.setLastName("Tom......");
        customer.setEmail("email..1111.000..");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setId(100);

        Customer merge = entityManager.merge(customer);

        System.out.println(customer);
        System.out.println(merge);
    }

    @Test
    public void testPersist() {
        Customer customer = new Customer();
        customer.setAge(12);
        customer.setLastName("jerry1......");
        customer.setEmail("email.....");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        //customer.setId(123);

        entityManager.persist(customer);


    }
}
