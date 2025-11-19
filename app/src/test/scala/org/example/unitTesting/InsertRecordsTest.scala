
package org.example.unitTesting

import org.mockito.ArgumentMatchers._

import java.sql.SQLException

class InsertRecordsTest extends CreateTableTest {

  it should "insert records into Artists table successfully" in {

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPS)
    when(mockPS.execute()).thenReturn(true)
    when(mockPS.executeBatch()).thenReturn(Array(1))

    when(mockPS.executeQuery(anyString())).thenReturn(mockRS)
    when(mockRS.next()).thenReturn(true)
    when(mockRS.getString("userId")).thenReturn("8025")

    val rec1 = List("8025", "2", "vamshi", "15", "8jw83q9a")
    val rec2 = List("8027", "8", "manish", "55", "j4w83c9a")

    mockArtist.insert(null, rec1)
    mockArtist.insert(null, rec2)

    verify(mockConnection, atLeastOnce).prepareStatement(anyString())
    verify(mockPS, atLeastOnce).setString(anyInt, anyString())
    verify(mockPS, atLeastOnce).execute()

    val rs = mockPS.executeQuery("select userId from Artists where artistName = 'vamshi' ")
    rs.next() shouldBe true
    rs.getString("userId") shouldBe "8025"
  }
it should "throw SQLException when connection fails" in {

    when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("DB error"))

    val rec1 = List("8025", "2", "vamshi", "15", "8jw83q9a")

    val thrown = intercept[SQLException] {
      mockArtist.insert(null, rec1)
    }

    thrown.getMessage should include("DB error")

    verify(mockConnection, atLeastOnce).prepareStatement(anyString())
  }

  it should "throw exception when list contains nulls" in {


    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPS)
    when(mockPS.setString(anyInt, anyString())).thenThrow(new NullPointerException("Null value"))

    val recWithNull = List("8025", null, "vamshi", "15", "8jw83q9a")

    val thrown = intercept[NullPointerException] {
      mockArtist.insert(null, recWithNull)
    }

    thrown.getMessage should include("Null value")
    verify(mockPS, atLeastOnce).setString(anyInt, anyString())
  }
}