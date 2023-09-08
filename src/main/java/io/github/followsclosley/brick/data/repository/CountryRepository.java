package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}
