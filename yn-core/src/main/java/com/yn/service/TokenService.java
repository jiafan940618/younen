package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.TokenDao;
import com.yn.model.Token;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class TokenService {
    @Autowired
    TokenDao tokenDao;

    public Token findOne(Long id) {
        return tokenDao.findOne(id);
    }

    public void save(Token token) {
        if(token.getId()!=null){
        	Token one = tokenDao.findOne(token.getId());
            try {
                BeanCopy.beanCopy(token,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tokenDao.save(one);
        }else {
            tokenDao.save(token);
        }
    }

    public void delete(Long id) {
        tokenDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		tokenDao.deleteBatch(id);
	}

    public Token findOne(Token token) {
        Specification<Token> spec = RepositoryUtil.getSpecification(token);
        Token findOne = tokenDao.findOne(spec);
        return findOne;
    }

    public List<Token> findAll(List<Long> list) {
        return tokenDao.findAll(list);
    }

    public Page<Token> findAll(Token token, Pageable pageable) {
        Specification<Token> spec = RepositoryUtil.getSpecification(token);
        Page<Token> findAll = tokenDao.findAll(spec, pageable);
        return findAll;
    }

    public List<Token> findAll(Token token) {
        Specification<Token> spec = RepositoryUtil.getSpecification(token);
        return tokenDao.findAll(spec);
    }
}
