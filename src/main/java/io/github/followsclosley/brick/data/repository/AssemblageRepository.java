package io.github.followsclosley.brick.data.repository;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import io.github.followsclosley.brick.data.Assemblage;

public interface AssemblageRepository extends DatastoreRepository<Assemblage, String> {
}
