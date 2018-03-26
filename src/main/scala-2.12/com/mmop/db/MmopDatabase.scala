package com.mmop.db

import scalikejdbc.{ConnectionPool, DB, DBSession, using}

object MmopDatabase {
  Class.forName("com.mysql.cj.jdbc.Driver")
  ConnectionPool.add("mmop", "jdbc:mysql://kursova-mysql.clgpufg6ovc7.us-east-2.rds.amazonaws.com/kursova", "admin", "1qa2ws3ed")
  implicit val connection = ConnectionPool.get("mmop")

  def withSession[T](f: DBSession => T): T = {
    using(DB(connection.borrow())) { db =>
      db.localTx { implicit session =>
        f(session)
      }
    }
  }

  def withReadOnlySession[T](f: DBSession => T): T = {
    using(DB(connection.borrow())) { db =>
      db.readOnly { implicit session =>
        f(session)
      }
    }
  }
}
