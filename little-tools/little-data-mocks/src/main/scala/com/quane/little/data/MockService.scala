package com.quane.little.data

import com.quane.little.data.model.{HasRecordId, RecordId}
import scala.collection.mutable.ListBuffer

/** To be extended by mock services and injected into tests.<br/>
  * The behavior emulates that of the real implementation, letting us focus on
  * higher level logic in test.
  *
  * @author Sean Connolly
  */
trait MockService[R <: Any with HasRecordId] {

  private var idSequence = 1
  protected val records = ListBuffer[R]()

  /** Initialize the mock service: clear all records.
    */
  def init(): Unit = {
    records.clear()
    idSequence = 1
  }


  protected def insert(record: R): R = {
    record.id = nextId
    records += record
    record
  }

  protected def get(id: RecordId): Option[R] = {
    records foreach {
      record => if (record.id == id) return Some(record)
    }
    None
  }

  private def nextId: RecordId = {
    idSequence += 1
    new RecordId(idSequence.toString)
  }

}
