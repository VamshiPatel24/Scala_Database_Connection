package org.example.unitTesting

import org.mockito.ArgumentMatchers._

import java.sql.SQLException

class DeleteRecordsTest extends CreateTableTest {

  it should "delete a record according to rank" in {
    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeUpdate(anyString())).thenReturn(1)

    noException shouldBe thrownBy(mockArtist.delete("2"))

    verify(mockStatement).executeUpdate("delete from Artists where rank = 2")
    verify(mockConnection).createStatement()
  }

  it should "throw SQLException when executeUpdate fails" in {

    when(mockConnection.createStatement()).thenReturn(mockStatement)

    when(mockStatement.executeUpdate(anyString())).thenThrow(new SQLException("DB error"))

    an[SQLException] shouldBe thrownBy(mockArtist.delete("2"))

    verify(mockConnection, times(1)).createStatement()
    verify(mockStatement, times(1)).executeUpdate("delete from Artists where rank = 2")
  }

  it should "throw exception when rank is null" in {

    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeUpdate(anyString())).thenThrow(new NullPointerException("Rank is null"))

    an[NullPointerException] shouldBe thrownBy((mockArtist.delete(null)))
    verify(mockStatement, times(1)).executeUpdate("delete from Artists where rank = null")
  }
}
