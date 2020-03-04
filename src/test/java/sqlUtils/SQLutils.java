package sqlUtils;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
//import data.AppProp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLutils {

    public static void cleanDB(String databaseUrl, String userName, String password) throws SQLException {
        val cleanCreditRequest = "DELETE FROM credit_request_entity;";
        val cleanPayment = "DELETE FROM payment_entity;";
        val cleanOrder = "DELETE FROM order_entity;";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(databaseUrl, userName, password)) {
            val cleanC = runner.execute(conn, cleanCreditRequest);
            val cleanP = runner.execute(conn, cleanPayment);
            val cleanO= runner.execute(conn, cleanOrder);
        }
    }

    public static String getCreditCardStatus(String databaseUrl, String userName, String password) throws SQLException {
         val selectStatus = "SELECT * FROM credit_request_entity ORDER BY status created DESC LIMIT 1";
         val runner = new QueryRunner();
         try ( val  conn = DriverManager.getConnection(databaseUrl, userName, password)) {
             val creditCardStatus = runner.query(conn, selectStatus, new BeanHandler<>(CreditRequestEntity.class));
             return creditCardStatus.getStatus();
         }
    }

    public static String getDebitCardStatus(String databaseUrl, String userName, String password) throws SQLException {
        val selectStatus = "SELECT * FROM payment_entity ORDER BY status created DESC LIMIT 1";
        val runner = new QueryRunner();
        try ( val  conn = DriverManager.getConnection(databaseUrl, userName, password)) {
            val debitCardStatus = runner.query(conn, selectStatus, new BeanHandler<>(CreditRequestEntity.class));
            return debitCardStatus.getStatus();
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
    }

        public static String getOrderEntityId(String databaseUrl, String userName, String password, String
        paymentEntityId) throws SQLException {
            val orderEntity = "SELECT * FROM order_entity WHERE payment_id='" + paymentEntityId + "';";
            val runner = new QueryRunner();
            try (val conn = DriverManager.getConnection(databaseUrl, userName, password)) {
                val orderBlock = runner.query(conn, orderEntity, new BeanHandler<>(OrderEntity.class));
                return orderBlock.getId();
            }
        }
    }

