package com.yn.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.WalletDao;
import com.yn.model.User;
import com.yn.model.Wallet;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;
import com.yn.vo.UserVo;

@Service
public class WalletService {
	@Autowired
	WalletDao walletDao;

	public Wallet findOne(Long id) {
		return walletDao.findOne(id);
	}

	public void save(Wallet wallet) {
		if (wallet.getId() != null) {
			Wallet one = walletDao.findOne(wallet.getId());
			try {
				BeanCopy.beanCopy(wallet, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			walletDao.save(one);
		} else {
			walletDao.save(wallet);
		}
		System.out.println();
	}
	
	
	public void updatePrice(Wallet wallet){
		
		walletDao.updatePrice(wallet);
	}

	public void delete(Long id) {
		walletDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		walletDao.deleteBatch(id);
	}

	public Wallet findOne(Wallet wallet) {
		Specification<Wallet> spec = RepositoryUtil.getSpecification(wallet);
		Wallet findOne = walletDao.findOne(spec);
		return findOne;
	}

	public List<Wallet> findAll(List<Long> list) {
		return walletDao.findAll(list);
	}

	public Page<Wallet> findAll(Wallet wallet, Pageable pageable) {
		Specification<Wallet> spec = RepositoryUtil.getSpecification(wallet);
		Page<Wallet> findAll = walletDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Wallet> findAll(Wallet wallet) {
		Specification<Wallet> spec = RepositoryUtil.getSpecification(wallet);
		return walletDao.findAll(spec);
	} 
	

	
	/**
     * 根据用户id查找钱包，如果该用户没有钱包就新建一个钱包
     */
    public void createWallet(User user) {
    	Wallet wallet = new Wallet();
    	wallet.setUserId(user.getId());
    	Wallet findOne = findOne(wallet);
    	if (findOne == null) {
    		wallet.setMoney(BigDecimal.valueOf(0));
			save(wallet);
		}
    }


	public Wallet findWalletByUser(Long userId) {

		return walletDao.findByUserId(userId);
	}
	
	/**
	 * 更新积分
	 */
	public void updateIntegral(UserVo userVo,Double integral) {
		
			User user=new User();
			user.setId(userVo.getId());
		    createWallet(user);
		    walletDao.updateIntegral(integral, userVo.getId());

	}
	
}
