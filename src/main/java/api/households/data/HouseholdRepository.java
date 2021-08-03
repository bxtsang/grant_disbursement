package api.households.data;

import api.households.data.models.Household;
import io.micronaut.context.annotation.Executable;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface HouseholdRepository extends CrudRepository<Household, Integer> {
    @Executable
    public Household find(Integer id);

    @Query("Update Household as h set h.totalIncome = h.totalIncome + :newIncome")
    public Household updateTotalIncome(@Id Integer id, Integer newIncome);

    @Query("from Household h where (:totalIncome is null or h.totalIncome < :totalIncome)")
    public Iterable<Household> findByTotalIncome(@Nullable Integer totalIncome);
}
