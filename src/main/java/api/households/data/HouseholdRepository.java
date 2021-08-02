package api.households.data;

import api.households.data.models.Household;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface HouseholdRepository extends CrudRepository<Household, Integer> {
    @Executable
    public Household find(Integer id);
}
