package com.yn.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yn.dao.UploadPhotoDao;
import com.yn.model.UploadPhoto;

@Mapper
public interface UploadPhotoMapper {
	
	List<UploadPhoto> findPhoto(UploadPhoto UploadPhoto);
	
	

}
