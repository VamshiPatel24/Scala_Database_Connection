package org.example.topArtist.repository

import org.example.topArtist.service.TopArtistImplementation.{msurl, password, username}
import java.sql.{Connection, DriverManager}
class DBConnection {
  def getConnection: Connection = {
     DriverManager.getConnection(msurl, username, password)
  }
}
