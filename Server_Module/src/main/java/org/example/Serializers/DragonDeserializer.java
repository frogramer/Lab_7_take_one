package org.example.Serializers;
import org.example.EnumClasses.*;
import org.example.CustomClasses.*;
import org.example.Exceptions.*;
import com.google.gson.*;

import java.lang.reflect.Type;
/** Serializer that transforms json objects to Dragon class objects
 */
public class DragonDeserializer implements JsonDeserializer<Dragon> {
    /** Method that transforms Dragon class objects to json objects
     * @param default_dragon - j-son object, that needs to be transformed
     * @param type - type of serializing object
     * @param jsonDeserializationContext - used to transform to non-primitive types of date
     * @return - Dragon class object of needed json object
     */
    @Override
    public Dragon deserialize(JsonElement default_dragon, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = default_dragon.getAsJsonObject();
        Dragon dragon = new Dragon();
        dragon.setCreationDate(jsonObject.get("Dragon creation date").getAsString());
        String test_name = jsonObject.get("Dragon name").getAsString();
        String test_id = jsonObject.get("Dragon ID").getAsString();
        String test_age = jsonObject.get("Dragon age").getAsString();
        String test_color = jsonObject.get("Dragon color").getAsString();
        String test_type = jsonObject.get("Dragon type").getAsString();
        String test_character = jsonObject.get("Dragon character").getAsString();
        String[] test_coordinates = jsonObject.get("Dragon coordinates").getAsString().split(" ");
        if (test_name.trim().length() == 0)
        {
            throw new InvalidVariableValueException();
        }
        else {
            dragon.SetDragonName(test_name);
        }
        if (test_id.contains(".") || test_id.contains(",") || Integer.parseInt(test_id) <= 0) {
            throw new InvalidVariableValueException();
        }
        else {
            dragon.Setid(jsonObject.get("Dragon ID").getAsInt());
        }
        if (test_age.contains(".") || test_age.contains(",") || Integer.parseInt(test_age) < 0) {
            throw new InvalidVariableValueException();
        }
        else
        {
            dragon.SetDragonAge(Integer.parseInt(test_age));
        }
        if (Integer.parseInt(test_coordinates[1]) > 233 || test_coordinates[1].contains(",") || test_coordinates[1].contains("."))
        {
            throw new InvalidVariableValueException();
        }
        else{
            Coordinates coordinates = new Coordinates(Float.parseFloat(test_coordinates[0]), Integer.parseInt(test_coordinates[1]));
            dragon.SetDragonCoordinates(coordinates);
        }
        if (!(test_color.equals("RED")  | test_color.equals("BLACK")  | test_color.equals("WHITE")  | test_color.equals("YELLOW")  | test_color.equals("ORANGE")))
        {
            throw new InvalidVariableValueException();
        }
        else {
            dragon.SetDragonColor(Color.GetColor(test_color.toLowerCase()));
        }
        if (!(test_type.equals("AIR") || test_type.equals("FIRE") || test_type.equals("WATER") || test_type.equals("UNDERGROUND") || test_type.equals("no type")))
        {
            throw new InvalidVariableValueException();
        }
        else{
            dragon.SetDragonType(DragonType.GetType(test_type.toLowerCase()));
        }
        if (!(test_character.equals("EVIL") || test_character.equals("GOOD") || test_character.equals("CHAOTIC_EVIL") || test_character.equals("CHAOTIC")))
        {
            throw new InvalidVariableValueException();
        }
        else{
            dragon.SetDragonCharacter(DragonCharacter.GetCharacter(test_character.toLowerCase()));
        }
        if (jsonObject.has("Dragon killer")) {
            dragon.SetDragonKiller(jsonDeserializationContext.deserialize(jsonObject.get("Dragon killer"), Person.class));
        }
        else{
            dragon.SetDragonKiller(null);
        }
        return dragon;
    }
}
