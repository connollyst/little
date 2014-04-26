package com.quane.little.data.model

/** A trait indicating a record with an id, a [[com.quane.little.data.model.RecordId]].
  *
  * @author Sean Connolly
  */
trait HasRecordID {

  def id: RecordId

  def id_=(id: RecordId)

}
