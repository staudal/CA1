package facades;

import dtos.HobbyDTO;
import entities.Hobby;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@NoArgsConstructor
public class HobbyFacade {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Creating a new Hobby
    public HobbyDTO create(HobbyDTO hobbyDTO) {
        Hobby hobby = new Hobby(hobbyDTO.getName(), hobbyDTO.getDescription());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HobbyDTO(hobby);
    }

}
