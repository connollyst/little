package com.quane.little.data.model

/** A trait indicating a record with an id, a [[RecordId]].
  *
  * @author Sean Connolly
  */
trait HasRecordId {

  def id: RecordId

  def id_=(id: RecordId)

}
