package org.example.unitTesting

import org.example.topArtist.repository.DBConnection
import org.mockito.scalatest.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.sql._

class MSSQLDBConnectionTest extends AnyFlatSpec with MockitoSugar with Matchers with BeforeAndAfterEach{
  val mockDBCon: DBConnection       = mock[DBConnection]
  val mockConnection: Connection = mock[Connection]
  val mockStatement: Statement   = mock[Statement]
  val mockPS: PreparedStatement  = mock[PreparedStatement]
  val mockRS: ResultSet = mock[ResultSet]
  val artist = new DBConnection {
    override def getConnection = mockDBCon.getConnection
  }
  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockDBCon, mockConnection, mockStatement, mockPS)
  }

  it should "establish the connection to MS SQL Server" in{
    beforeEach()
      when(mockDBCon.getConnection).thenReturn(mockConnection)
      mockConnection should not be null
      mockDBCon.getConnection.isClosed should be (false)
    verify(mockDBCon).getConnection
  }

  it should "fail when DB connection throws SQLException" in {
    beforeEach()
    when(mockDBCon.getConnection).thenThrow(new SQLException("Connection failed"))

    an[SQLException] should be thrownBy {
      mockDBCon.getConnection
    }

    verify(mockDBCon).getConnection
  }
}
