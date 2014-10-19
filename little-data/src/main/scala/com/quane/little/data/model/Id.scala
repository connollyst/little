package com.quane.little.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.bson.types.ObjectId

/** An id.<br/>
  * Ids are stored as `String` representations, the meaning of this is determined by the implementing class.
  *
  * @author Sean Connolly
  */
sealed trait Id {

  val id: String

  override def equals(other: Any): Boolean = other match {
    case that: Id => id == that.id
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String = id

}

class PrimitiveId(val id: String) extends Id

/** An object id which maps to a MongoDB [[org.bson.types.ObjectId]].
  *
  * @see [[http://docs.mongodb.org/manual/reference/object-id/ MongoDB ObjectId Reference]]
  *
  * @param id the hexadecimal string representation of the id
  *
  * @author Sean Connolly
  */
sealed abstract class MongoId(val id: String) extends Id {

  def toObjectId = new ObjectId(id)

}

class FunctionId(@JsonProperty("$oid") id: String) extends MongoId(id) {

  def this(id: ObjectId) = this(id.toStringMongod)

  def this() = this(new ObjectId())

}

class UserId(@JsonProperty("$oid") id: String) extends MongoId(id) {

  def this(id: ObjectId) = this(id.toStringMongod)

  def this() = this(new ObjectId())

}

class ListenerId(@JsonProperty("$oid") id: String) extends MongoId(id) {

  def this(id: ObjectId) = this(id.toStringMongod)

  def this() = this(new ObjectId())

}

/** A trait indicating an object has an [[com.quane.little.data.model.Id]].
  *
  * @author Sean Connolly
  */
trait HasId[I <: Id] {

  def id: I

  def id_=(id: I)

}