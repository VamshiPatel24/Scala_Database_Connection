package org.example.unitTesting

import org.example.topArtist.service.Artist
import org.mockito.ArgumentMatchers.anyString

import java.sql.SQLException

class CreateTableTest extends MSSQLDBConnectionTest {
  val mockArtist = new Artist {
    override val getConnection = mockConnection
  }

  it should "create table successfully" in {
    reset(mockDBCon, mockConnection, mockStatement)

    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.execute(anyString())).thenReturn(false)

    noException should be thrownBy mockArtist.create

    verify(mockConnection).createStatement()
    verify(mockStatement).execute(anyString())
  }

  it should "fail when SQLException occurs" in {
    reset(mockDBCon, mockConnection, mockStatement)

    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.execute(anyString())).thenThrow(new SQLException("Boom"))

    an[SQLException] should be thrownBy mockArtist.create

    verify(mockConnection).createStatement()
    verify(mockStatement).execute(anyString())
  }
}
