package com.example.demo.criteria;

import com.example.demo.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Consumer;

@SpringBootTest
public class CriteriaTest {
    private static SessionFactory factory = new Configuration().configure().buildSessionFactory();

    @Test
    void oldSelectRestrictions() {
        wrapWitSession(session -> {
            var list = session.createCriteria(User.class)
                    .add(Restrictions.like("firstName", "test1%"))
                    .add(Restrictions.gt("id", 1))
                    .addOrder(Order.asc("id"))
                    .list();
            System.out.println();
        });
    }

    @Test
    void oldSelectRestrictionsCombination() {
        wrapWitSession(session -> {
            var query = session.createCriteria(User.class);
            var a = Restrictions.ilike("firstName", "test11%");
            var b = Restrictions.gt("id", 3);
            var ex = Restrictions.or(a, b);
            var list = query.add(ex).list();
            System.out.println();
        });
    }

    @Test
    void oldSelectPagination() {
        wrapWitSession(session -> {
            var query = session.createCriteria(User.class);
            query.setFirstResult(1); //from 0
            query.setMaxResults(2);
            var list = query.list();
            System.out.println();
        });
    }

    @Test
    void oldSelectAggregateFunctions() {
        wrapWitSession(session -> {
            var query = session.createCriteria(User.class);
            query.setProjection(Projections.max("id"));
            var list = query.list();
            System.out.println();
        });
    }

    @Test
    void oldSelectColumn() {
        wrapWitSession(session -> {
            var query = session.createCriteria(User.class);
            query.setProjection(Projections.property("firstName"));
            var list = query.list();
            System.out.println();
        });
    }

    @Test
    void oldSelectColumnGroup() {
        wrapWitSession(session -> {
            var query = session.createCriteria(User.class);
            query.setProjection(Projections.groupProperty("firstName"));
            var list = query.list();
            System.out.println();
        });
    }

    @Test
    void oldSelectSingle() {
        wrapWitSession(session -> {
            var query = session.createCriteria(User.class);
            query.add(Restrictions.eq("id", 2));
            var user = query.uniqueResult();
            System.out.println();
        });
    }

    @Test
    void newBuilder1() {
        wrapWitSession(session -> {
            var builder = session.getCriteriaBuilder();

            var queryTemplate = builder.createQuery(User.class);
            var root = queryTemplate.from(User.class);
            var firstNameParam = builder.parameter(String.class);
            queryTemplate.where(builder.equal(root.get("firstName"), firstNameParam));

            var query = session.createQuery(queryTemplate);
            query.setParameter(firstNameParam, "test13");
            var list = query.getResultList();
            System.out.println();
        });
    }

    @Test
    void newBuilder2() {
        wrapWitSession(session -> {
            var cb = session.getCriteriaBuilder();
            var cr = cb.createQuery(User.class);
            var r = cr.from(User.class);
            cr.select(r).where(cb.equal(r.get("id"), 1));

            var user = session.createQuery(cr).uniqueResult();
            System.out.println();
        });
    }

    @Test
    void newBuilderUpdate() {
        wrapWitSession(session -> {
            var cb = session.getCriteriaBuilder();
            var criteriaUpdate = cb.createCriteriaUpdate(User.class);
            var root = criteriaUpdate.from(User.class);
            criteriaUpdate.set("firstName", "updatedFirstName1");
            criteriaUpdate.where(cb.equal(root.get("firstName"), "test13"));

            session.createQuery(criteriaUpdate).executeUpdate();
        });
    }

    @Test
    void newBuilderDelete() {
        wrapWitSession(session -> {
            var cb = session.getCriteriaBuilder();
            var criteriaDelete = cb.createCriteriaDelete(User.class);
            var root = criteriaDelete.from(User.class);
            criteriaDelete.where(cb.greaterThan(root.get("id"), 3));

            session.createQuery(criteriaDelete).executeUpdate();
        });
    }



    private static void wrapWitSession(Consumer<Session> lambda) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            lambda.accept(session);
        }
    }

    @BeforeAll
    static void fillData() {
        wrapWitSession(session -> {
            session.createNativeQuery("" +
                    "delete from users;\n" +
                    "delete from profile;\n" +
                    "insert into profile values (1, '12333');\n" +
                    "insert into profile values (2, '12333');\n" +
                    "insert into profile values (3, '12333');\n" +
                    "insert into profile values (5, '12333');\n" +
                    "insert into profile values (4, '12333');\n" +
                    "insert into users values\n" +
                    "(1, 'test11', 'test11', 'test11@t.d', 'p1', 1),\n" +
                    "(2, 'test12', 'test12', 'test12@t.d', 'p2', 2),\n" +
                    "(3, 'test13', 'test13', 'test13@t.d', 'p3', 3),\n" +
                    "(5, 'test13', 'test13', 'test13@t.d', 'p3', 5),\n" +
                    "(4, 'test14', 'test14', 'test14@t.d', 'p4', 4);").executeUpdate();
        });
    }
}
