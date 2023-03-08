package facades;

import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Creating a new Person
    public void create(PersonDTO personDTO) {
        Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Adding a Hobby to a Person
    public void addHobby(Long personId, Long hobbyId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, personId);
            Hobby hobby = em.find(Hobby.class, hobbyId);
            person.getHobbies().add(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
