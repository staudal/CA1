package facades;

import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public PersonDTO create(PersonDTO personDTO) {
        Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
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

    // Find all persons
    public List<PersonDTO> getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            return PersonDTO.getDTOs(query.getResultList());
        } finally {
            em.close();
        }
    }

    // Get information about a person given a phone number
    public PersonDTO getPersonByPhone(String phone) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.phones ph WHERE ph.number = :phone", Person.class);
            query.setParameter("phone", phone);
            return new PersonDTO(query.getSingleResult());
        } finally {
            em.close();
        }
    }

    // Gets the number of persons in the database
    public int getPersonCount() {
        Set<Person> persons = new HashSet<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            persons.addAll(query.getResultList());
        } finally {
            em.close();
        }
        return persons.size();
    }

}
