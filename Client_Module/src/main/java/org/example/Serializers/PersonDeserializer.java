package org.example.Serializers;

import com.google.gson.*;
import org.example.CustomClasses.Person;
import org.example.EnumClasses.Color;
import org.example.EnumClasses.Country;
import org.example.Exceptions.InvalidVariableValueException;

import java.lang.reflect.Type;
/** Serializer, that transforms json object to Person class object
 */
public class PersonDeserializer implements JsonDeserializer<Person> {
    /** Method, that transforms json object to Person class objects
     * @param default_person - json object, that needs to be transformed
     * @param type - type of serializing object
     * @param jsonDeserializationContext - used to transform to non-primitive types of date
     * @return - Person clas object of needed json object
     */
    @Override
    public Person deserialize(JsonElement default_person, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = default_person.getAsJsonObject();
        Person person = new Person();
        String test_name = jsonObject.get("Person name").getAsString();
        String test_nationality = jsonObject.get("Person nationality").getAsString();
        String test_eye_color = jsonObject.get("Person eye color").getAsString();
        String test_birthday = jsonObject.get("Person birthday date").getAsString();
        if (test_name.trim().length() == 0)
        {
            throw new InvalidVariableValueException();
        }
        else {
            person.SetPersonName(test_name);
        }
        if (!test_birthday.equals("unknown"))
        {
            person.SetBirthdayDate(java.time.LocalDate.parse(test_birthday));
        }
        if (!(test_nationality.equals("USA") || test_nationality.equals("JAPAN") || test_nationality.equals("VATICAN") || test_nationality.equals("CHINA")))
        {
            throw new InvalidVariableValueException();
        }
        else {
            person.SetNationality(Country.GetNationality(test_nationality.toLowerCase()));
        }
        if (!(test_eye_color.equals("RED") || test_eye_color.equals("YELLOW") || test_eye_color.equals("ORANGE") || test_eye_color.equals("WHITE") || test_eye_color.equals("BLACK") || test_eye_color.equals("no color")))
        {
            System.out.println(test_eye_color);
            throw new InvalidVariableValueException();
        }
        else {
            person.SetPersonEyeColor(Color.GetColor(test_eye_color.toLowerCase()));
        }
        return person;
    }
}
