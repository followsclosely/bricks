package io.github.followsclosley.brick.jpa.repository;

import io.github.followsclosley.brick.jpa.Country;
import io.github.followsclosley.brick.jpa.Franchise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, String> {
}
