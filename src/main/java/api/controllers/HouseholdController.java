package api.controllers;

import api.data.HouseholdRepository;
import api.data.PersonRepository;
import api.data.models.Household;
import api.data.models.Person;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;

import javax.inject.Inject;

@Controller("/households")
public class HouseholdController {
    @Inject
    HouseholdRepository householdRepository;

    @Inject
    PersonRepository personRepository;

    @Post
    public Household createHousehold(@Body Household household) {
        return householdRepository.save(household);
    }

    @Get("/{id}")
    public Household getHousehold(Integer id) {
        Household household = householdRepository.find(id);
        if (household == null) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "No household of id " + id.toString() + " found");
        }

        return householdRepository.find(id);
    }

    @Get
    public Iterable<Household> getHouseholds() {
        return householdRepository.findAll();
    }

    @Post("/{householdId}/family-members")
    public Person addFamilyMember(Integer householdId, @Body Person person) {
        person.setHouseholdId(householdId);
        return personRepository.save(person);
    }

    @Delete("/{id}")
    public void deleteHousehold(Integer id) {
        if (householdRepository.find(id) == null) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "No household of id " + id.toString() + " found");
        }
        householdRepository.deleteById(id);
    }
}
