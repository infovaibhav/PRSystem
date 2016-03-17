package org.iry.dao.pr;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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
	public List<PurchaseRequisition> findPurchaseRequests(PurchaseRequestSearchCriteria searchCriteria) throws Exception {
		Criteria crit = createEntityCriteria();
		if( searchCriteria.getPrNo() != null && !searchCriteria.getPrNo().trim().isEmpty() ) {
			if( searchCriteria.isExactMatch() ) {
				crit.add(Restrictions.eq("prNo", searchCriteria.getPrNo().trim()));
			} else {
				crit.add(Restrictions.ilike("prNo", '%'+searchCriteria.getPrNo().trim()+'%'));
			}
		}
		if( searchCriteria.getProjectCode() != null && !searchCriteria.getProjectCode().trim().isEmpty() ) {
			if( searchCriteria.isExactMatch() ) {
				crit.add(Restrictions.eq("projectCode", searchCriteria.getProjectCode().trim()));
			} else {
				crit.add(Restrictions.ilike("projectCode", '%'+searchCriteria.getProjectCode().trim()+'%'));
			}
		}
		if( searchCriteria.getProjectName() != null && !searchCriteria.getProjectName().trim().isEmpty() ) {
			if( searchCriteria.isExactMatch() ) {
				crit.add(Restrictions.eq("projectName", searchCriteria.getProjectName().trim()));
			} else {
				crit.add(Restrictions.ilike("projectName", '%'+searchCriteria.getProjectName().trim()+'%'));
			}
		}
		if( searchCriteria.getCreatedBy() != null && !searchCriteria.getCreatedBy().isEmpty() ) {
			crit.add(Restrictions.in("createdBy", searchCriteria.getCreatedBy()));
		}
		if( searchCriteria.getStatuses() != null && !searchCriteria.getStatuses().isEmpty() ) {
			crit.add(Restrictions.in("status", searchCriteria.getStatuses()));
		}
		if( searchCriteria.needFromTimeRestriction() ) {
			crit.add(Restrictions.ge("createdDate", new Timestamp(searchCriteria.getFromTime().getTime())));
		}
		if( searchCriteria.needToTimeRestriction() ) {
			crit.add(Restrictions.le("createdDate", new Timestamp(searchCriteria.getToTime().getTime() + (24 * 60 * 60 * 1000))));
		}
		if( searchCriteria.needPagination() ) {
			crit.setFirstResult(searchCriteria.getFirstResult());
			crit.setMaxResults(searchCriteria.getMaxResult());
		}
		
		crit.setProjection(Projections.count("prNo"));
		int totalRecords = ((Long)crit.uniqueResult()).intValue();
		
		crit.setProjection(null);
		
		if( searchCriteria.getSidx() != null ) {
			if( searchCriteria.getSord() != null && searchCriteria.getSidx().equalsIgnoreCase("DESC") ) {
				crit.addOrder(Order.desc("createdDate"));
			} else {
				crit.addOrder(Order.desc("createdDate"));
			}
		} else {
			crit.addOrder(Order.desc("createdDate"));
		}
		
		List<PurchaseRequisition> result = crit.list();
		
		if( result != null && !result.isEmpty() ) {
			/*crit.setProjection(Projections.count("prNo"));
			int totalRecords = (int)crit.uniqueResult();*/
			result.get(0).setTotalRecords(totalRecords);
		}
		return result;
	}

}
