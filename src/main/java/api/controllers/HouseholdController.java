package api.controllers;

import api.data.HouseholdRepository;
import api.data.PersonRepository;
import api.data.models.Household;
import api.data.models.Person;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

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
}
