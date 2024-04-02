package com.api.expo.repository;
import org.springframework.data.repository.CrudRepository;
import com.api.expo.models.Validation;

public interface ValidationRepository extends CrudRepository <Validation, Integer> {
    
    Validation findByCode(String code);
}
