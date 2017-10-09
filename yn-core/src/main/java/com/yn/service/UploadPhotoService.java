package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.dao.UploadPhotoDao;
import com.yn.dao.mapper.UploadPhotoMapper;
import com.yn.model.ApolegamyServer;
import com.yn.model.UploadPhoto;
import com.yn.utils.BeanCopy;

@Service
public class UploadPhotoService {
	@Autowired
	private UploadPhotoDao uploadPhotodao;
	@Autowired
	private UploadPhotoMapper uploadPhotoMapper;
	
	/** 根据用户id/服务商的id查询用户上传的图片*/
	
	public 	List<UploadPhoto> findPhoto(UploadPhoto uploadPhoto){

		return uploadPhotoMapper.findPhoto(uploadPhoto);
	}
	
	 public UploadPhoto findOne(Long id) {
	        return uploadPhotodao.findOne(id);
	    }
  
	  /** 保存图片*/
	    public void save(UploadPhoto uploadPhoto) {
	        if(uploadPhoto.getId()!=null){
	        	UploadPhoto loadPhoto = uploadPhotodao.findOne(uploadPhoto.getId());
	            try {
	                BeanCopy.beanCopy(uploadPhoto,loadPhoto);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            uploadPhotodao.save(loadPhoto);
	        }else {
	        	uploadPhotodao.save(uploadPhoto);
	        }
	    }
	
	
	

}
