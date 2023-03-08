package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;

public class Populate {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        HobbyFacade hobbyFacade = HobbyFacade.getHobbyFacade(emf);
        PersonFacade personFacade = PersonFacade.getPersonFacade(emf);

        PersonDTO personDTO = new PersonDTO("email@email.dk", "John", "Doe");
        HobbyDTO hobbyDTO = new HobbyDTO("Hobby", "Description");

        personFacade.create(personDTO);
        hobbyFacade.create(hobbyDTO);

        personFacade.addHobby(1L, 1L);
    }
    
    public static void main(String[] args) {
        populate();
    }
}
