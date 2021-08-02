package api.households.data;

import api.households.data.models.Person;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
    @Executable
    public Person find(Integer id);
}
