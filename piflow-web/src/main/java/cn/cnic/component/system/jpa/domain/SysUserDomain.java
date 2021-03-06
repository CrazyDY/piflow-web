package cn.cnic.component.system.jpa.domain;

import cn.cnic.component.system.entity.SysUser;
import cn.cnic.component.system.jpa.repository.SysUserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Component
public class SysUserDomain {

    @Autowired
    private SysUserJpaRepository sysUserJpaRepository;

    private Specification<SysUser> addEnableFlagParam() {
        Specification<SysUser> specification = new Specification<SysUser>() {
			private static final long serialVersionUID = 1L;

			@Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //root.get("enableFlag") means to get the field name of enableFlag
                return criteriaBuilder.equal(root.get("enableFlag"), 1);
            }
        };
        return specification;
    }

    public SysUser saveOrUpdate(SysUser sysUser) {
        return sysUserJpaRepository.save(sysUser);
    }

    public List<SysUser> saveOrUpdate(List<SysUser> sysUserList) {
        return sysUserJpaRepository.saveAll(sysUserList);
    }

    public void delete(String id){
        sysUserJpaRepository.deleteById(id);
    }

    public String checkUsername(String username){
        return sysUserJpaRepository.checkUsername(username);
    }

}
