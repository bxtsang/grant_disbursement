package api.controllers;

import api.data.HouseholdRepository;
import api.data.models.Household;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;

@Controller("/household")
public class HouseholdController {
    @Inject
    HouseholdRepository householdRepository;

    @Post
    public Household createHousehold(@Body Household household) {
        return householdRepository.save(household);
    }

    @Get("/{id}")
    public Household getHousehold(Integer id) {
        return householdRepository.find(id);
    }
}