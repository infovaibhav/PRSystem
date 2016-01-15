/**
 * 
 */
package org.iry.model.pr;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author vaibhavp
 *
 */
public class PRNoGenerator implements IdentifierGenerator {

    private static final Logger log = Logger.getLogger(PRNoGenerator.class);

    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        try {
        	String prefix = null;
        	PurchaseRequisition pr = (PurchaseRequisition) object; 
        	if( pr.getPrNoPrefix() != null ) {
        		prefix = pr.getPrNoPrefix();
        	} else {
        		prefix = "IRY_DEFAULT";
        	}
	        Connection connection = session.connection();
            PreparedStatement ps = connection.prepareStatement("SELECT nextval ('PURCHASE_REQUISITION_SEQ') as nextval");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("nextval");
                String code = prefix + '_' + id;
                log.debug("Generated PR ID: " + code);
                return code;
            }
        } catch (SQLException e) {
            log.error(e);
            throw new HibernateException("Unable to generate PR ID Sequence");
        }
        return null;
    }
}