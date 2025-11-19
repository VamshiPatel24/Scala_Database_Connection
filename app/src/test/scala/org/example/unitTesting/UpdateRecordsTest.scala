package org.example.unitTesting

import org.mockito.ArgumentMatchers.anyString

import java.sql.SQLException

class UpdateRecordsTest extends CreateTableTest {
  it should "successfully update playCount" in {

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPS)
    when(mockPS.executeUpdate()).thenReturn(1) // simulate 1 row updated

    noException should be thrownBy {
      mockArtist.update("500")
    }

    verify(mockConnection, times(1)).prepareStatement(anyString())
    verify(mockPS, times(1)).executeUpdate()
  }

  it should "throw SQLException if executeUpdate fails" in {

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPS)
    when(mockPS.executeUpdate()).thenThrow(new SQLException("DB error"))


    val thrown = intercept[SQLException] {
      mockArtist.update("500")
    }

    thrown.getMessage should include ("DB error")

    verify(mockConnection, times(1)).prepareStatement(anyString())
    verify(mockPS, times(1)).executeUpdate()
  }
}