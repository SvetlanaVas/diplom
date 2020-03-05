package sqlUtils;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLutils {

    public static Connection getConnection() throws SQLException {
        try {
            String url = System.getProperty("spring.datasource.url");
            String username = "app";
            String password = "pass";
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url, username, password);
                {
                    System.out.println("Connection to Store DB success!");
                    return conn;
                }
            } catch (Exception ex) {
                System.out.println("Connection failed...");
                System.out.println(ex);
            }
            return null;
        } finally {

        }
    }

    public static void cleanDB() throws SQLException {
        val cleanCreditRequest = "DELETE FROM credit_request_entity;";
        val cleanPayment = "DELETE FROM payment_entity;";
        val cleanOrder = "DELETE FROM order_entity;";
        val runner = new QueryRunner();
            val cleanC = runner.execute(getConnection(), cleanCreditRequest);
            val cleanP = runner.execute(getConnection(), cleanPayment);
            val cleanO= runner.execute(getConnection(), cleanOrder);
    }

    public static String getCreditCardStatus() throws SQLException {
        val selectStatus = "SELECT * FROM credit_request_entity ORDER BY status";
        val runner = new QueryRunner();
            val creditCardStatus = runner.query(getConnection(), selectStatus, new BeanHandler<>(CreditRequestEntity.class));
            return creditCardStatus.getStatus();

    }

    public static String getDebitCardStatus() throws SQLException {
        val selectStatus = "SELECT * FROM payment_entity ORDER BY status";
        val runner = new QueryRunner();
            val debitCardStatus = runner.query(getConnection(), selectStatus, new BeanHandler<>(CreditRequestEntity.class));
            return debitCardStatus.getStatus();

    }

    public static String getPaymentEntityId(String status) throws SQLException {

        val paymentEntity = "SELECT * FROM payment_entity WHERE status='" + status + "';";
        val runner = new QueryRunner();
        val paymentBlock = runner.query(getConnection(), paymentEntity, new BeanHandler<>(PaymentEntity.class));
            return paymentBlock.getTransaction_id();

    }

    public static String getCreditRequestEntityId(String status) throws SQLException {
        val creditRequest = "SELECT * FROM credit_request_entity WHERE status='" + status + "';";
        val runner = new QueryRunner();
        val creditRequestBlock = runner.query(getConnection(), creditRequest, new BeanHandler<>(CreditRequestEntity.class));
            return creditRequestBlock.getBank_id();

    }

    public static String getOrderEntityId(String paymentEntityId) throws SQLException {
        val orderEntity = "SELECT * FROM order_entity WHERE payment_id='" + paymentEntityId + "';";
        val runner = new QueryRunner();
        val orderBlock = runner.query(getConnection(), orderEntity, new BeanHandler<>(OrderEntity.class));
            return orderBlock.getId();

    }
}

