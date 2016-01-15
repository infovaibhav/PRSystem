package org.iry.dao.user;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.iry.dao.AbstractDao;
import org.iry.dto.SearchCriteria;
import org.iry.dto.user.UserDto;
import org.iry.model.user.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Long, User> implements UserDao {

	public void save(User user) {
		persist(user);
	}
	
	public User findById(long id) {
		return getByKey(id);
	}

	public User findBySSO(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		return (User) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDto> findAllActiveUsers() {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("isActive", Boolean.TRUE));
		crit.add(Restrictions.eq("isRoot", Boolean.FALSE));
		crit.setProjection(Projections.projectionList()
			      .add(Projections.property("id"), "id")
			      .add(Projections.property("ssoId"), "ssoId"));
		crit.addOrder(Order.asc("ssoId"));
		crit.setResultTransformer(Transformers.aliasToBean(UserDto.class));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsers(SearchCriteria searchCriteria) {

		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("isRoot", Boolean.FALSE));
		crit.addOrder(Order.asc("ssoId"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}

}
