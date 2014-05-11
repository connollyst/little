package com.quane.little.data.service

import com.quane.little.data.model.RecordId
import com.quane.little.language.{SetStatement, GetStatement, PrintStatement, Expression}
import com.quane.little.language.data.Value

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

  val Primitives = List[String](
    PrimitiveGet,
    PrimitiveSet,
    PrimitivePrint
  )

}

/**
 *
 *
 * @author Sean Connolly
 */
class PrimitiveService {

  def allPrimitives: List[RecordId] =
    PrimitiveService.Primitives map {
      primitive => new RecordId(primitive)
    }

  def findPrimitive(id: RecordId): Expression =
  // TODO we are sort of abusing the RecordId here, let's abstract out an 'id'
    PrimitiveFactory.create(id.oid)

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