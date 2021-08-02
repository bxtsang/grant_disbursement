package api.households;

import api.households.data.PersonRepository;
import api.households.data.models.Person;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.exceptions.HttpStatusException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FamilyMemberService {
    @Inject
    PersonRepository personRepository;

    public Person addFamilyMember(Integer householdId, @Body Person person) {
        person.setHouseholdId(householdId);
        return personRepository.save(person);
    }

    public void deleteFamilyMember(Integer householdId, Integer id) {
        Person person = personRepository.find(id);

        if (person == null) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Person with given ID does not exist");
        }

        if (!person.getHouseholdId().equals(householdId)) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Person does not belong to given household");
        }

        personRepository.deleteById(id);
    }
}
