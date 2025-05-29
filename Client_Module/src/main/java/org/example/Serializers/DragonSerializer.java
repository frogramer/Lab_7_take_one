package org.example.Serializers;

import com.google.gson.*;
import org.example.CustomClasses.Dragon;

import java.lang.reflect.Type;

/** Serializer that transforms Dragon class objects to json objects
 */
public class DragonSerializer implements JsonSerializer<Dragon> {
    /** Method that transforms Dragon class objects to json objects
     * @param dragon - dragon, that needs to be transformed
     * @param type - type of serializing object
     * @param jsonSerializationContext - used to transform non-primitive types of date
     * @return - json object of needed dragon
     */
    @Override
    public JsonElement serialize(Dragon dragon, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Dragon ID", dragon.GetDragonid());
        jsonObject.addProperty("Dragon name", dragon.GetName());
        jsonObject.addProperty("Dragon age", dragon.GetDragonAge());
        jsonObject.addProperty("Dragon coordinates", dragon.GetCoordinates().toString());
        jsonObject.addProperty("Dragon creation date", dragon.GetDragonCreationDate().toString());
        jsonObject.addProperty("Dragon color", dragon.GetDragonColor().toString());
        try {
            jsonObject.addProperty("Dragon type", dragon.GetDragonType().toString());
        }catch (NullPointerException e){
            jsonObject.addProperty("Dragon type", "no type");
        }
        jsonObject.addProperty("Dragon character", dragon.GetDragonCharacter().toString());
        try {
            jsonObject.add("Dragon killer", jsonSerializationContext.serialize(dragon.GetDragonKiller()));}
        catch (NullPointerException e){
            jsonObject.addProperty("Dragon killer", "no killer");
        }
        return jsonObject;
    }
}
