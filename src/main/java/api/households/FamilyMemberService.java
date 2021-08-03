package api.households;

import api.households.data.HouseholdRepository;
import api.households.data.PersonRepository;
import api.households.data.PersonRequestHandler;
import api.households.data.models.Person;
import api.households.data.models.PersonRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.exceptions.HttpStatusException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FamilyMemberService {
    @Inject
    PersonRepository personRepository;

    @Inject
    HouseholdRepository householdRepository;

    @Inject
    PersonRequestHandler personRequestHandler;

    public Person addFamilyMember(Integer householdId, PersonRequest person) {
        person.setHouseholdId(householdId);
        Person newPerson = personRepository.save(personRequestHandler.convertPersonRequest(person));
        householdRepository.updateTotalIncome(householdId, newPerson.getAnnualIncome());

        return newPerson;
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
        householdRepository.updateTotalIncome(householdId, -person.getAnnualIncome());
    }
}
