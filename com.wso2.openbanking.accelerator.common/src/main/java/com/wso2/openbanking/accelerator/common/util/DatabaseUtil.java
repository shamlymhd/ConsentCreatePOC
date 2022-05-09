package com.wso2.openbanking.accelerator.common.util;



import com.wso2.openbanking.accelerator.common.exception.OpenBankingRuntimeException;
import com.wso2.openbanking.accelerator.common.persistence.JDBCPersistenceManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Utility class for database operations.
 */
public class DatabaseUtil {

    private static final Log log = LogFactory.getLog(DatabaseUtil.class);

    /**
     * Get a database connection instance from the Consent Management Persistence Manager.
     *
     * @return Database Connection
     * @throws OpenBankingRuntimeException Error when getting a database connection to Consent Management database
     */
    public static Connection getDBConnection() throws OpenBankingRuntimeException {

        return JDBCPersistenceManager.getInstance().getDBConnection();
    }

    public static void closeConnection(Connection dbConnection) {
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                log.error("Database error. Could not close statement. Continuing with others. - "
                        + e.getMessage(), e);
            }
        }
    }

    public static void rollbackTransaction(Connection dbConnection) {

        JDBCPersistenceManager.getInstance().rollbackTransaction(dbConnection);
    }

    public static void commitTransaction(Connection dbConnection) {

        JDBCPersistenceManager.getInstance().commitTransaction(dbConnection);
    }
}
