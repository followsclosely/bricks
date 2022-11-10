package io.github.followsclosley.brick.jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class NativeQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private NativeQueries nativeQueries;

    public List<Map<String,Object>> getSummary(String name){

        NativeQueries.QueryDetails queryDetails = nativeQueries.getQueries().get(name);
        Objects.requireNonNull(queryDetails);

        Query query = entityManager.createNativeQuery(queryDetails.getSql());

        List<Object[]> list = query.getResultList();
        return list.stream().map(a -> {
            int i=0;
            HashMap<String,Object> map = new HashMap<>();
            for (String column : queryDetails.getColumnNames() ){
                map.put(column, a[i++]);
            }
            return map;
        }
        ).collect(Collectors.toList());
    }
}