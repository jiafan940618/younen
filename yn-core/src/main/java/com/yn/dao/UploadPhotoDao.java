package com.yn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yn.model.Ammeter;
import com.yn.model.UploadPhoto;

public interface UploadPhotoDao extends JpaRepository<UploadPhoto, Long>, JpaSpecificationExecutor<UploadPhoto> {
	
	
	


}
