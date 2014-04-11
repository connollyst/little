package com.quane.little.data.model

/** A trait indicating a record with an id, a [[RecordID]].
  *
  * @author Sean Connolly
  */
trait HasRecordID {

  def id: RecordID

  def id_=(id: RecordID)

}
