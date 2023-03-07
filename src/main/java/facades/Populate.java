package facades;

import dtos.PersonDTO;
import entities.Person;

import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

public class Populate {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade personFacade = PersonFacade.getFacadeExample(emf);

        PersonDTO personDTO1 = new PersonDTO("test@test.dk", "Test", "Testesen");
        PersonDTO personDTO2 = new PersonDTO("test2@test.dk", "Test2", "Testesen2");

        personFacade.create(personDTO1);
        personFacade.create(personDTO2);
    }
    
    public static void main(String[] args) {
        populate();
    }
}
