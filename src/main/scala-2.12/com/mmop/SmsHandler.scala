package com.mmop

import scalikejdbc._

object SmsHandler {

  // initialize JDBC driver & connection pool
  Class.forName("com.mysql.cj.jdbc.Driver")
  ConnectionPool.add("turbosms", "jdbc:mysql://94.249.146.189/users", "dimasymonenko", "31121997")
  val connection = ConnectionPool.get("turbosms")

  def sendSms(phoneNumber: String, message: String) : Unit = {
    using(DB(connection.borrow())) { db =>
      db.localTx { implicit session =>
        sql"INSERT INTO dimasymonenko (number, sign, message, wappush) VALUES (${phoneNumber}, 'Msg', ${message},'')".execute().apply()
      }
    }
  }

  def sendLoginCode(phoneNumber: String, loginCode: String) : Unit = {
    sendSms(phoneNumber, s"Your login code is $loginCode.")
  }
}
