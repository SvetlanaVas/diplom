**Программное окружение:**
1.	ОС WINDOWS 10 PRO
2.	Браузер Google Chrom Версия 80.0.3987.122 (Официальная сборка), (64 бит)
3.	Java JDK 1.8

**Действия, необходимые для запуска Приложения и его тестирования:**

**1.** Запустить Docker

**2.** В терминале IDEA из директории diplom загрузить контейнеры mysql, postgres и образ платежного шлюза nodejs командой

**docker-compose up**

**3.** Во втором терминале из директории artifacts запустить SUT командой:

•	для конфигурации с базой MySql

**java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar aqa-shop.jar**

•	для конфигурации с базой PostgreSql

**java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar aqa-shop.jar**

**4.** В браузере SUT открывается так: localhost:8080

**5.** Запуск автотестов из директории diplom командой

•	для конфигурации с MySql

**gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app**

•	для конфигурации с PostgreSql

**gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/app**

