package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AddressDTO;
import dtos.PersonDTO;
import facades.AddressFacade;
import facades.CityInfoFacade;
import facades.HobbyFacade;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("persons")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PersonFacade personFacade = PersonFacade.getPersonFacade(EMF);
    private static final HobbyFacade hobbyFacade = HobbyFacade.getHobbyFacade(EMF);
    private static final AddressFacade addressFacade = AddressFacade.getAddressFacade(EMF);
    private static final CityInfoFacade cityInfoFacade = CityInfoFacade.getCityInfoFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("person/{phone}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByPhone(@PathParam("phone") String phone) {
        PersonDTO person = personFacade.getPersonByPhone(phone);
        person.setHobbies(hobbyFacade.getAllHobbiesByPerson(person));
        AddressDTO address = addressFacade.getAddressFromPerson(person);
        address.setCityInfo(cityInfoFacade.getCityFromPerson(person));
        person.setAddress(address);
        return Response.ok().entity(GSON.toJson(person)).build();
    }
}
