package org.example.Serializers;
import org.example.CustomClasses.*;

import java.lang.reflect.Type;
import com.google.gson.*;
/** Serializer that transforms Person class objects to json objects
 */
public class PersonSerializer implements JsonSerializer<Person> {
    /** Method, that transforms Person class objects to json object
     * @param person - person, that needs to be transformed
     * @param type - type of serializing object
     * @param jsonSerializationContext - used to transform non-primitive types of date
     * @return - json object of needed person
     */
    @Override
    public JsonElement serialize(Person person, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Person name", person.GetPersonName());
        try {
            jsonObject.addProperty("Person birthday date", person.GetBirthdayDate().toString());
        }catch(NullPointerException e){
            jsonObject.addProperty("Person birthday date", "unknown");
        }
        try {
            jsonObject.addProperty("Person eye color", person.GetPersonEyeColor().toString());
        }catch(NullPointerException e){
            jsonObject.addProperty("Person eye color", "no color");
        }
        jsonObject.addProperty("Person nationality", person.GetNationality().toString());
        return jsonObject;
    }
}
