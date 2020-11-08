package connectors;

import entities.PersonEntity;
import resources.PersonResource;
import utils.Database;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.util.ArrayList;

public class PersonConnector {

    public static PersonResource createOrUpdate(final PersonResource personResource) {
        PersonResource storedPersonResource = null;
        if (personResource != null) {
            EntityManager entityManager = Database.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            PersonEntity personEntity = new PersonEntity(personResource);
            PersonEntity storedPersonEntity = entityManager.merge(personEntity);
            if (storedPersonEntity != null) {
                storedPersonResource = new PersonResource(storedPersonEntity);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        return storedPersonResource;
    }

    public static PersonResource get(final Long id) {
        EntityManager entityManager = Database.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        PersonEntity personEntity = entityManager.find(PersonEntity.class, id);
        PersonResource personResource = null;
        if (personEntity != null) {
            personResource = new PersonResource(personEntity);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return personResource;
    }

    public static ArrayList<PersonResource> getAll() {
        ArrayList<PersonResource> personResources = new ArrayList<>();
        EntityManager entityManager = Database.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("Person.getAll", PersonEntity.class);
        ArrayList<PersonEntity> personEntities = (ArrayList<PersonEntity>) query.getResultList();
        for (PersonEntity personEntity : personEntities) {
            if (personEntity != null) {
                personResources.add(new PersonResource(personEntity));
            }
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return personResources;
    }

    public static boolean delete(final Long id) {
        boolean success = true;
        EntityManager entityManager = Database.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        try {
            PersonEntity personEntity = entityManager.getReference(PersonEntity.class, id);
            if (personEntity != null) {
                entityManager.remove(personEntity);
            } else {
                success = false;
            }
        } catch (EntityNotFoundException e) {
            success = false;
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        return success;
    }

}
