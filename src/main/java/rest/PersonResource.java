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
import java.util.List;

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
    public String mdemo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/phone/{phone}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByPhone(@PathParam("phone") String phone) throws Exception {
        PersonDTO person = personFacade.getPersonByPhone(phone);
        person.setHobbies(hobbyFacade.getAllHobbiesByPerson(person));
        AddressDTO address = addressFacade.getAddressFromPerson(person);
        address.setCityInfo(cityInfoFacade.getCityFromPerson(person));
        person.setAddress(address);
        return Response.ok().entity(GSON.toJson(person)).build();
    }

    @GET
    @Path("/hobby/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByHobby(@PathParam("hobby") String hobby) {
        List<PersonDTO> personDTOs = personFacade.getPersonsWithHobby(hobby);
        for (PersonDTO person : personDTOs) {
            person.setHobbies(hobbyFacade.getAllHobbiesByPerson(person));
            AddressDTO address = addressFacade.getAddressFromPerson(person);
            address.setCityInfo(cityInfoFacade.getCityFromPerson(person));
            person.setAddress(address);
        }
        return Response.ok().entity(GSON.toJson(personDTOs)).build();
    }

    @GET
    @Path("/zip/{zip}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByZipCode(@PathParam("zip") String zipCode) {
        List<PersonDTO> personDTOs = personFacade.getPersonsByZipCode(Integer.parseInt(zipCode));
        for (PersonDTO person : personDTOs) {
            person.setHobbies(hobbyFacade.getAllHobbiesByPerson(person));
            AddressDTO address = addressFacade.getAddressFromPerson(person);
            address.setCityInfo(cityInfoFacade.getCityFromPerson(person));
            person.setAddress(address);
        }
        return Response.ok().entity(GSON.toJson(personDTOs)).build();
    }

    @GET
    @Path("/count/hobby/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByHobbyCount(@PathParam("hobby") String hobby) {
        List<PersonDTO> personDTOs = personFacade.getPersonsWithHobby(hobby);
        int count = personDTOs.size();
        return Response.ok().entity(GSON.toJson(count)).build();
    }

    @PUT
    @Path("/edit")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response editPerson(String person) {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        personFacade.editPerson(personDTO);
        return Response.ok().entity(GSON.toJson(personDTO)).build();
    }

    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addPerson(String person) {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        personFacade.create(personDTO);
        return Response.ok().entity(GSON.toJson(personDTO)).build();
    }
}
