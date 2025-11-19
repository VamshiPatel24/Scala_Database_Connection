package org.example.topArtist.repository

import org.example.App.getClass
import org.example.topArtist.service.TopArtistImplementation.{msurl, password, username}
import org.slf4j.LoggerFactory

import java.sql.{Connection, DriverManager}
class DBConnection {
  private val logger = LoggerFactory.getLogger(getClass)
  def getConnection: Connection = {
    logger.info("......................")
     DriverManager.getConnection(msurl, username, password)
  }
}
