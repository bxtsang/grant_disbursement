package api.households;

import api.households.data.HouseholdRepository;
import api.households.data.PersonRepository;
import api.households.data.models.Household;
import api.households.data.models.Person;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.Iterator;

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

    public Iterable<Household> searchForHouseholds(Integer totalIncome, Integer childrenAge, Integer elderAge, Boolean hasCouple) {
        Iterable<Household> households = householdRepository.findByTotalIncome(totalIncome);

        Iterator<Household> householdIterator = households.iterator();
        while (householdIterator.hasNext()) {
            Household household = householdIterator.next();

            if (!hasChild(household, childrenAge)) {
                householdIterator.remove();
            }

            if (!hasElder(household, elderAge)) {
                householdIterator.remove();
            }

            if (hasCouple != null) {
                if (hasCouple && !hasCouple(household)) {
                    householdIterator.remove();
                } else if (!hasCouple && hasCouple(household)) {
                    householdIterator.remove();
                }
            }
        }

        return households;
    }

    private boolean hasChild(Household household, Integer childrenAge) {
        if (childrenAge == null) {
            return true;
        }

        for (Person member: household.getFamilyMembers()) {
            LocalDate currentDate = LocalDate.now();
            LocalDate dob = member.getDob();

            if (currentDate.getYear() - dob.getYear() < childrenAge) {
                return true;
            } else if (currentDate.getYear() - dob.getYear() == childrenAge) {
                currentDate = currentDate.minusYears(childrenAge);

                if (currentDate.isBefore(dob)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasElder(Household household, Integer elderAge) {
        if (elderAge == null) {
            return true;
        }

        for (Person member: household.getFamilyMembers()) {

            LocalDate currentDate = LocalDate.now();
            LocalDate dob = member.getDob();

            if (currentDate.getYear() - dob.getYear() > elderAge) {
                return true;
            } else if (currentDate.getYear() - dob.getYear() == elderAge) {
                currentDate = currentDate.minusYears(elderAge);

                if (currentDate.isAfter(dob) || currentDate.isEqual(dob)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasCouple(Household household) {
        for (Person member: household.getFamilyMembers()) {
            if (!member.getMaritalStatus()) {
                continue;
            }

            Person spouse = member.getSpouse();

            if (spouse == null) {
                return true;
            } else {
                if (spouse.getHouseholdId().equals(household.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
