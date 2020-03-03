package sqlUtils;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import data.AppProp;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLutils {

    /*public static void cleanDB() throws SQLException {
        val cleanCreditRequest = "DELETE FROM credit_request_entity;";
        val cleanPayment = "DELETE FROM payment_entity;";
        val cleanOrder = "DELETE FROM order_entity;";
        AppProp props = AppProp.getAppProp();
        val runner = new QueryRunner();
        //try (val conn = DriverManager.getConnection(props.getDatabaseUrl(), "app", "pass"))
        try (val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app", "app", "pass"))
        {
            val cleanCreditRequestEntity = runner.execute(conn, cleanCreditRequest, new BeanHandler<>(CreditRequestEntity.class));
            val cleanPaymentEntity = runner.execute(conn, cleanPayment, new BeanHandler<>(PaymentEntity.class));
            val cleanOrderEntity = runner.execute(conn, cleanOrder, new BeanHandler<>(OrderEntity.class));
        }
    }

    public static String getPaymentEntityId(String databaseUrl, String userName, String password, String status) throws SQLException {
        val paymentEntity = "SELECT * FROM payment_entity WHERE status='" + status + "';";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(databaseUrl, userName, password)) {
            val paymentBlock = runner.query(conn, paymentEntity, new BeanHandler<>(PaymentEntity.class));
            return paymentBlock.getTransaction_id();
        }
    }

    public static String getCreditRequestEntityId(String databaseUrl, String userName, String password, String status) throws SQLException {
        val creditRequest = "SELECT * FROM credit_request_entity WHERE status='" + status + "';";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(databaseUrl, userName, password)) {
            val creditRequestBlock = runner.query(conn, creditRequest, new BeanHandler<>(CreditRequestEntity.class));
            return creditRequestBlock.getBank_id();

    }

    public static String getOrderEntityId(String databaseUrl, String userName, String password, String paymentEntityId) throws SQLException {
        val orderEntity = "SELECT * FROM order_entity WHERE payment_id='" + paymentEntityId + "';";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(databaseUrl, userName, password)) {
            val orderBlock = runner.query(conn, orderEntity, new BeanHandler<>(OrderEntity.class));
            return orderBlock.getId();
        }
    }*/
}
