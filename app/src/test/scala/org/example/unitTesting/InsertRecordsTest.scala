
package org.example.unitTesting

import org.mockito.ArgumentMatchers._

import java.sql.{Connection, PreparedStatement, SQLException}

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


}