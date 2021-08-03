package api.households;

import api.households.data.HouseholdRepository;
import api.households.data.PersonRepository;
import api.households.data.models.Household;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HouseholdService {

    @Inject
    HouseholdRepository householdRepository;

    @Inject
    PersonRepository personRepository;

    public Household createHousehold(Household household) {
        return householdRepository.save(household);
    }

    public Household getHousehold(Integer id) {
        Household household = householdRepository.find(id);
        if (household == null) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "No household of id " + id.toString() + " found");
        }

        return household;
    }

    public Iterable<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    public void deleteHousehold(Integer id) {
        if (householdRepository.find(id) == null) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "No household of id " + id.toString() + " found");
        }
        householdRepository.deleteById(id);
    }

//    public Iterable<Household> searchForHouseholds(Integer totalIncome, Integer childrenAge, Integer elderAge, Boolean hasCouple) {
//        Iterable<Household> households =
//    }
}
