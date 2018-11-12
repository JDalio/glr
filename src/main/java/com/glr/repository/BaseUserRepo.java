package com.glr.repository;

import com.glr.model.BaseUser;
import org.springframework.data.repository.CrudRepository;


public interface BaseUserRepo extends CrudRepository<BaseUser, String>
{
}
