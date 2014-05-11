package com.quane.little.data.service

import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.language.{SetStatement, GetStatement, PrintStatement, Expression}
import com.quane.little.language.data.Value
import scala.collection.immutable

object PrimitiveService {

  private var instance: Option[PrimitiveService] = None

  def apply(): PrimitiveService = {
    // Note: we provide this apply() just to be consistent with other services
    if (!instance.isDefined) {
      instance = Some(new PrimitiveService)
    }
    instance.get
  }

  val PrimitiveGet = "_little_get"
  val PrimitiveSet = "_little_set"
  val PrimitivePrint = "_little_print"
  val Primitives = immutable.List[String](
    PrimitiveGet,
    PrimitiveSet,
    PrimitivePrint
  )
  val PrimitivesNames = immutable.Map[String, String](
    PrimitiveGet -> "get",
    PrimitiveSet -> "set",
    PrimitivePrint -> "print"
  )

}

/**
 *
 *
 * @author Sean Connolly
 */
class PrimitiveService {

  // TODO we are sort of abusing the RecordId here, let's abstract out an 'id'

  def allPrimitives: Iterable[Expression] =
    PrimitiveService.Primitives map {
      id => PrimitiveFactory.create(id)
    }

  def all: Iterable[PrimitiveRecord] =
    PrimitiveService.Primitives map {
      id => createRecord(new RecordId(id))
    }

  def findPrimitive(id: RecordId): Expression = PrimitiveFactory.create(id.oid)

  def find(id: RecordId): PrimitiveRecord = createRecord(id)

  def createRecord(id: RecordId): PrimitiveRecord =
    new PrimitiveRecord(id, PrimitiveService.PrimitivesNames(id.oid), PrimitiveFactory.create(id.oid))
}

object PrimitiveFactory {

  def create(id: String): Expression = {
    id match {
      case PrimitiveService.PrimitiveGet => new GetStatement("")
      case PrimitiveService.PrimitiveSet => new SetStatement("", Value(""))
      case PrimitiveService.PrimitivePrint => new PrintStatement(Value(""))
      case _ => throw new IllegalAccessException("No primitive '" + id + "'")
    }
  }

}