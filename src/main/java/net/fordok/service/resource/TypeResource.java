package net.fordok.service.resource;

import com.codahale.metrics.annotation.Timed;
import net.fordok.service.dto.Type;
import net.fordok.service.service.TypeRequest;
import net.fordok.service.service.TypeResponse;
import net.fordok.service.storage.Storage;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by fordok on 10/26/2015.
 */
@Path("/types")
@Produces(value = MediaType.APPLICATION_JSON)
@Timed
public class TypeResource {

    private final Storage storage;

    public TypeResource(Storage storage){
        this.storage = storage;
    }

    @GET
    public List<Type> getTypes(){
        return storage.getTypes();
    }

    @POST
    public TypeResponse createType(TypeRequest typeRequest){
        Type type = new Type();
        type.setName(typeRequest.getName());
        type.setInputParams(typeRequest.getInputParams());
        type.setOutputParams(typeRequest.getOutputParams());
        storage.saveType(type);
        return new TypeResponse("Success");
    }
}
