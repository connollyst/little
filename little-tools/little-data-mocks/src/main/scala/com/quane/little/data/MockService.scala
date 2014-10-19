package com.quane.little.data

import com.quane.little.data.model.{HasId, Id}

import scala.collection.mutable.ListBuffer

/** To be extended by mock services and injected into tests.<br/>
  * The behavior emulates that of the real implementation, letting us focus on higher level logic in test.
  *
  * @author Sean Connolly
  */
abstract class MockService[I <: Id, R <: Any with HasId[I]](idFactory: IdFactory[I]) {

  protected val records = ListBuffer[R]()

  /** Initialize the mock service: clear all records.
    */
  def init(): Unit = {
    records.clear()
    idFactory.init()
  }


  protected def insert(record: R): R = {
    record.id = idFactory.next
    records += record
    record
  }

  protected def get(id: I): Option[R] = {
    records foreach {
      record => if (record.id == id) return Some(record)
    }
    None
  }

}

trait IdFactory[I <: Id] {

  private var idSequence = 1

  def init(): Unit = idSequence = 1

  def next: I

  protected def increment: String = {
    idSequence += 1
    idSequence.toString
  }

}