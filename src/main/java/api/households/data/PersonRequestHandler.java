package api.households.data;

import api.households.data.models.Person;
import api.households.data.models.PersonRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersonRequestHandler {
    @Inject
    PersonRepository personRepository;

    public Person convertPersonRequest(PersonRequest personRequest) {
        Person person = new Person();
        person.setName(personRequest.getName());
        person.setGender(personRequest.getGender());
        person.setMaritalStatus(personRequest.getMaritalStatus());
        person.setOccupationType(personRequest.getOccupationType());
        person.setAnnualIncome(personRequest.getAnnualIncome());
        person.setDob(personRequest.getDob());
        person.setHouseholdId(personRequest.getHouseholdId());

        if (!personRequest.getMaritalStatus()) {
            return person;
        }

        if (personRequest.getSpouse() instanceof Integer) {
            Integer spouseId = (Integer) personRequest.getSpouse();
            Person spouse = personRepository.find(spouseId);

            if (spouse == null) {
                throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Spouse with given ID does not exist");
            }
            person.setSpouse(spouse);
        } else if (personRequest.getSpouse() instanceof String) {
            String spouseName = (String) personRequest.getSpouse();
            person.setSpouseName(spouseName);
        }

        return person;
    }
}
