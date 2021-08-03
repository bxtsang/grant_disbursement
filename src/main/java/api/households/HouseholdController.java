package api.households;

import api.households.data.models.Household;
import api.households.data.models.Person;
import api.households.data.models.PersonRequest;
import io.micronaut.core.annotation.Nullable;
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
        household.checkValid();
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
    public Person addFamilyMember(Integer householdId, @Body PersonRequest person) {
        householdService.getHousehold(householdId);

        return familyMemberService.addFamilyMember(householdId, person);
    }

    @Delete("/{id}")
    public void deleteHousehold(Integer id) {
        householdService.deleteHousehold(id);
    }

    @Delete("/{householdId}/family-members/{id}")
    public void deleteFamilyMember(Integer householdId, Integer id) {
        householdService.getHousehold(householdId);
        familyMemberService.deleteFamilyMember(householdId, id);
    }

    @Get("/search")
    public Iterable<Household> searchForHouseholds(
            @Nullable @QueryValue Integer totalIncome,
            @Nullable @QueryValue Integer childrenAge,
            @Nullable @QueryValue Integer elderAge,
            @Nullable @QueryValue Boolean hasCouple
    ) {
        return householdService.searchForHouseholds(totalIncome, childrenAge, elderAge, hasCouple);
    }
}
