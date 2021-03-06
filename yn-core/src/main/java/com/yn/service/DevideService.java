package com.yn.service;

import com.yn.dao.DevideDao;
import com.yn.enums.DevideEnum;
import com.yn.model.Devide;
import com.yn.model.Inverter;
import com.yn.model.SolarPanel;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;
import com.yn.vo.DevideVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class DevideService {
    @Autowired
    DevideDao devideDao;
    @Autowired
    SolarPanelService solarPanelService;
    @Autowired
    InverterService inverterService;
    
    
    public Devide findOne(Long id) {
        return devideDao.findOne(id);
    }
    
    /** 拿到型号*/
    public List<Devide> selectBatch(Devide deviceType){
    	
		return devideDao.selectBatch(deviceType);
    }
    
    /** 拿到品牌*/
   public List<Devide> selectDevice(Devide deviceType){

    	return 	devideDao.selectDevice(deviceType);
    }
    
    

    public void save(Devide devide) {
        if (devide.getId() != null) {
            Devide one = devideDao.findOne(devide.getId());
            try {
                BeanCopy.beanCopy(devide, one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            devideDao.save(one);
        } else {
            devideDao.save(devide);
        }
    }

	@Transactional
    public void delete(Long id) {
        // 删除设备
        devideDao.delete(id);

        // 如果删除的是品牌，需要把该品牌下的设备也一起删除
        Devide devide = devideDao.findOne(id);
        if (devide.getZlevel().equals(DevideEnum.FIRST_LEVEL.getCode())) {
            if (!CollectionUtils.isEmpty(devide.getChildren())) {
                for (Devide children : devide.getChildren()) {
                    devideDao.delete(children.getId());
                }
            }
        }
    }

    public void deleteBatch(List<Long> id) {
        devideDao.deleteBatch(id);
    }

    public Devide findOne(Devide devide) {
        Specification<Devide> spec = RepositoryUtil.getSpecification(devide);
        Devide findOne = devideDao.findOne(spec);
        return findOne;
    }

    public List<Devide> findAll(List<Long> list) {
        return devideDao.findAll(list);
    }

    public Page<Devide> findAll(Devide devide, Pageable pageable) {
        Specification<Devide> spec = RepositoryUtil.getSpecification(devide);
        Page<Devide> findAll = devideDao.findAll(spec, pageable);
        return findAll;
    }

    public List<Devide> findAll(Devide devide) {
        Specification<Devide> spec = RepositoryUtil.getSpecification(devide);
        return devideDao.findAll(spec);
    }
    
    
    /** 保存电池板与逆变器*/
    public void saveDdeide(DevideVo deviceVo){
    	SolarPanel solarPanel = new SolarPanel();
    	solarPanel.setBrandId(deviceVo.getSolbrandId());
    	solarPanel.setBrandName(deviceVo.getSolbrandName());
    	solarPanel.setModel(deviceVo.getSolmodel());
    	solarPanel.setQualityAssurance(deviceVo.getSolqualityAssurance());
    	solarPanel.setPowerGeneration(deviceVo.getSolpowerGeneration());
    	
    	solarPanelService.save(solarPanel);
    	
    	
    	Inverter inverter = new Inverter();
    	/*private Integer invbrandId;
    	private String invbrandName;
    	private String invmodel;
    	private Double invqualityAssurance; //质保年限
    	private String invremark;
    	private Double invsupplyPrice;*/
    	inverter.setBrandId(deviceVo.getInvbrandId());
    	inverter.setBrandName(deviceVo.getInvbrandName());
    	inverter.setModel(deviceVo.getInvmodel());
    	inverter.setQualityAssurance(deviceVo.getInvqualityAssurance());
    	inverterService.save(inverter);
    }
    
    
}
