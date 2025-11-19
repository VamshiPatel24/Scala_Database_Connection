package org.example.integrationTesting.configsAndConnections

import java.sql.{Connection, DriverManager}

class H2DBConnection {
  val args = H2DatabaseConfImplementation

  def getConnection: Connection = {
    Class.forName("org.h2.Driver")
    DriverManager.getConnection(args.url, args.username, args.password)
  }
}
