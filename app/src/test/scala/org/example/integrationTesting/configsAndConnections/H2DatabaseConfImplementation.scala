package org.example.integrationTesting.configsAndConnections

import com.typesafe.config.{Config, ConfigFactory}

object H2DatabaseConfImplementation extends H2DatabaseConfiguration {

  override def url: String = config.getString("url")

  override def config: Config = loader.getConfig("h2")

  override def loader: Config = ConfigFactory.load("h2conf.conf")

  override def username: String = config.getString("username")

  override def password: String = config.getString("password")

}
