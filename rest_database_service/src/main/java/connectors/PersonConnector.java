package connectors;

import entities.PersonEntity;

import javax.persistence.*;
import java.util.ArrayList;


public class PersonConnector {

    public static PersonEntity createOrUpdate(final PersonEntity personEntity) {
        PersonEntity storedPersonEntity = null;
        if (personEntity != null) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("RestDatabaseExample");
            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            storedPersonEntity = manager.merge(personEntity);
            manager.getTransaction().commit();
            manager.close();
            factory.close();
        }
        return storedPersonEntity;
    }

    public static PersonEntity get(final long id) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("RestDatabaseExample");
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        PersonEntity personEntity = manager.find(PersonEntity.class, id);
        manager.getTransaction().commit();
        manager.close();
        factory.close();
        return personEntity;
    }

    public static ArrayList<PersonEntity> getAll() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("RestDatabaseExample");
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        Query query = manager.createNamedQuery("Person.getAll", PersonEntity.class);
        ArrayList<PersonEntity> resultList = (ArrayList<PersonEntity>) query.getResultList();
        manager.getTransaction().commit();
        manager.close();
        factory.close();
        return resultList;
    }

    public static boolean delete(final long id) {
        boolean success = true;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("RestDatabaseExample");
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        try {
            PersonEntity personEntity = manager.getReference(PersonEntity.class, id);
            manager.remove(personEntity);
        } catch (EntityNotFoundException e) {
            success = false;
        } finally {
            manager.getTransaction().commit();
            manager.close();
            factory.close();
        }
        return success;
    }

}
