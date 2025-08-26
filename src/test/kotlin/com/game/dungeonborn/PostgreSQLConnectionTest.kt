package com.game.dungeonborn

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.sql.DataSource

@SpringBootTest
class PostgreSQLConnectionTest {

    @Autowired
    private lateinit var dataSource: DataSource

    @Test
    fun testPostgreSQLConnection() {
        val connection = dataSource.connection;

        assertNotNull(connection);
        assertEquals("PostgreSQL", connection.metaData.databaseProductName)

        connection.close();
    }
}