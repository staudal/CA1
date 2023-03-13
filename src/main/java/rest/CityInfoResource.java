package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AddressDTO;
import dtos.PersonDTO;
import facades.AddressFacade;
import facades.CityInfoFacade;
import facades.HobbyFacade;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("cityinfo")
public class CityInfoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade personFacade = PersonFacade.getPersonFacade(EMF);
    private static final HobbyFacade hobbyFacade = HobbyFacade.getHobbyFacade(EMF);
    private static final AddressFacade addressFacade = AddressFacade.getAddressFacade(EMF);
    private static final CityInfoFacade cityInfoFacade = CityInfoFacade.getCityInfoFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllZipCodes() {
        return Response.ok().entity(GSON.toJson(cityInfoFacade.getAllZipCodes())).build();
    }
}
