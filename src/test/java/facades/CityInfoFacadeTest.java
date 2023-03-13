package facades;

import dtos.CityInfoDTO;
import dtos.PersonDTO;
import entities.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CityInfoFacadeTest {

    private static EntityManagerFactory emf;
    private static CityInfoFacade cityInfoFacade;

    public CityInfoFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       cityInfoFacade = CityInfoFacade.getCityInfoFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Creates a cityInfo object
        CityInfo cityInfo = new CityInfo(1234, "testCity");
        em.persist(cityInfo);

        // Creates an address object
        Address address = new Address("testStreet", "testAdditionalInfo");
        address.setCityInfo(cityInfo);
        em.persist(address);

        // Creates a hobby object
        Hobby hobby = new Hobby("testHobby", "testDescription");

        // Creates a person object
        Person person = new Person("john@doe.dk", "John", "Doe");
        person.setAddress(address);
        person.setHobbies(List.of(hobby));
        em.persist(person);

        // Persists the hobby object
        hobby.setPersons(List.of(person));
        em.persist(hobby);

        // Creates a phone object
        Phone phone = new Phone("12345678", "testDescription");
        phone.setPerson(person);
        em.persist(phone);

        em.getTransaction().commit();
    }

    // Counts the number of zip codes in DB
    @Test
    public void testGetAllZipCodes() {
        List<CityInfoDTO> cityInfos = cityInfoFacade.getAllZipCodes();
        assertEquals(1, cityInfos.size());
    }

}
