package facades;

import dtos.CityInfoDTO;
import dtos.PersonDTO;
import entities.CityInfo;
import entities.Person;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@NoArgsConstructor
public class CityInfoFacade {

    private static CityInfoFacade instance;
    private static EntityManagerFactory emf;

    public static CityInfoFacade getCityInfoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityInfoFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Creating a new city
    public CityInfoDTO create (CityInfoDTO cityInfoDTO) {
        CityInfo cityInfo = new CityInfo(cityInfoDTO.getZipCode(), cityInfoDTO.getCity());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cityInfo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CityInfoDTO(cityInfo);
    }

    // Get city from personDTO
    public CityInfoDTO getCityFromPerson (PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, personDTO.getId());
        try {
            return new CityInfoDTO(em.find(CityInfo.class, person.getAddress().getCityInfo().getId()));
        } finally {
            em.close();
        }
    }

}
