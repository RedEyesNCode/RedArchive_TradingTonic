package com.tonictrade.tonictrade.repository;

import com.tonictrade.tonictrade.datamodel.AppVersionModel;
import com.tonictrade.tonictrade.datamodel.AppVersionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VersionRepository extends JpaRepository<AppVersionModel,Long> {




}
