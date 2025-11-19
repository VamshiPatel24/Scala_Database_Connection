package org.example.integrationTesting.configsAndConnections

import com.typesafe.config.Config

trait H2DatabaseConfiguration {
  def loader: Config = ???

  def config: Config = ???

  def url: String = ???

  def username: String = ???

  def password: String = ???
}
