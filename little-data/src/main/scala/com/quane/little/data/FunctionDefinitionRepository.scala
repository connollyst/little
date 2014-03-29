package com.quane.little.data

import com.quane.little.language.FunctionDefinition
import scala.collection.mutable

/** Provides storage and retrieval access to the repository of
  * [[com.quane.little.language.FunctionDefinition]] objects.
  *
  * @author Sean Connolly
  */
class FunctionDefinitionRepository {

  private val funs: mutable.Map[String, FunctionDefinition] = mutable.Map()

  def fetch(id: String): FunctionDefinition = funs(id)

  def save(id: String, fun: FunctionDefinition): Unit = funs += (id -> fun)

}
