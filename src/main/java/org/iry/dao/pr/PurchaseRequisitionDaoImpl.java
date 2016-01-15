package org.iry.dao.pr;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.iry.dao.AbstractDao;
import org.iry.dto.pr.PurchaseRequestSearchCriteria;
import org.iry.model.pr.PurchaseRequisition;
import org.springframework.stereotype.Repository;

@Repository("purchaseRequisitionDao")
public class PurchaseRequisitionDaoImpl extends AbstractDao<String, PurchaseRequisition> implements PurchaseRequisitionDao {

	@Override
	public void save(PurchaseRequisition purchaseRequisition) {
		persist(purchaseRequisition);
	}

	@Override
	public PurchaseRequisition findById(String prNo) {
		return getByKey(prNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PurchaseRequisition> findPurchaseRequests(PurchaseRequestSearchCriteria searchCriteria) {
		Criteria crit = createEntityCriteria();
		if( searchCriteria.getPrNo() != null ) {
			if( searchCriteria.isExactMatch() ) {
				crit.add(Restrictions.eq("prNo", searchCriteria.getPrNo()));
			} else {
				crit.add(Restrictions.like("prNo", searchCriteria.getPrNo()));
			}
		}
		if( searchCriteria.getStatuses() != null && !searchCriteria.getStatuses().isEmpty() ) {
			crit.add(Restrictions.in("status", searchCriteria.getStatuses()));
		}
		if( searchCriteria.getFromTime() != null ) {
			crit.add(Restrictions.ge("createdDate", searchCriteria.getFromTime()));
		}
		if( searchCriteria.getToTime() != null ) {
			crit.add(Restrictions.le("createdDate", searchCriteria.getToTime()));
		}
		if( searchCriteria.needPagination() ) {
			crit.setFirstResult(searchCriteria.getFirstResult());
			crit.setMaxResults(searchCriteria.getMaxResult());
		}
		if( searchCriteria.getSidx() != null ) {
			if( searchCriteria.getSord() != null && searchCriteria.getSidx().equalsIgnoreCase("DESC") ) {
				crit.addOrder(Order.desc("createdDate"));
			} else {
				crit.addOrder(Order.desc("createdDate"));
			}
		} else {
			crit.addOrder(Order.desc("createdDate"));
		}
		return crit.list();
	}

}
