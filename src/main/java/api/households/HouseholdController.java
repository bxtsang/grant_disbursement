package api.households;

import api.households.data.models.Household;
import api.households.data.models.Person;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;

import javax.inject.Inject;

@Controller("/households")
public class HouseholdController {
    @Inject
    HouseholdService householdService;

    @Inject
    FamilyMemberService familyMemberService;

    @Post
    public Household createHousehold(@Body Household household) {
        return householdService.createHousehold(household);
    }

    @Get("/{id}")
    public Household getHousehold(Integer id) {
        return householdService.getHousehold(id);
    }

    @Get
    public Iterable<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @Post("/{householdId}/family-members")
    public Person addFamilyMember(Integer householdId, @Body Person person) {
        if (householdService.getHousehold(householdId) == null) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Household ID does not exist");
        }

        return familyMemberService.addFamilyMember(householdId, person);
    }

    @Delete("/{id}")
    public void deleteHousehold(Integer id) {
        householdService.deleteHousehold(id);
    }

    @Delete("/{householdId}/family-members/{id}")
    public void deleteFamilyMember(Integer householdId, Integer id) {
        familyMemberService.deleteFamilyMember(householdId, id);
    }
}
