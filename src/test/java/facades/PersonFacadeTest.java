package facades;

import dtos.AddressDTO;
import dtos.PersonDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static rest.PersonResource.*;

public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade personFacade;
    private static HobbyFacade hobbyFacade;
    private static AddressFacade addressFacade;
    private static CityInfoFacade cityInfoFacade;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       personFacade = PersonFacade.getPersonFacade(emf);
       hobbyFacade = HobbyFacade.getHobbyFacade(emf);
       addressFacade = AddressFacade.getAddressFacade(emf);
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

    // Test that checks if a person is created
    @Test
    public void testGetPersonByPhone() throws Exception {
        PersonDTO personDTO = personFacade.getPersonByPhone("12345678");
        System.out.println(personDTO.getAddress().getStreet());
    }

    // Counts number of person with given hobby
    @Test
    public void testGetCountByHobby() {
        int count = personFacade.getPersonCountByHobby("testHobby");
        assertEquals(1, count);
    }

    // Counts number of person with given city
    @Test
    public void testGetCountByCity() {
        List<PersonDTO> persons = personFacade.getPersonsByZipCode(1234);
        assertEquals(1, persons.size());
    }

    // Updates a person's info
    @Test
    public void testUpdatePersonInfo() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setFirstName("Jane");
        assertEquals("Jane", personFacade.editPerson(personDTO).getFirstName());
    }

}
