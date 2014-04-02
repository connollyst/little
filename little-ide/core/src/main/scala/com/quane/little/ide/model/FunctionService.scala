package com.quane.little.ide.model

import com.quane.little.language.data.Value
import com.quane.little.language.{FunctionReference, Functions, FunctionDefinition}

/** A service for interacting with [[com.quane.little.language.Functions]].
  *
  * @author Sean Connolly
  */
object FunctionService {

  val FunctionNames = List("blank", "move", "stop", "turn", "voyage", "print dir")

  def fetchReference(name: String): FunctionReference = {
    val definition = fetchDefinition(name)
    val reference = new FunctionReference(definition.name)
    definition.params foreach {
      param =>
      // TODO parameters should have default values
        reference.addArg(param.name, Value(""))
    }
    reference
  }

  def fetchDefinition(name: String): FunctionDefinition = {
    name match {
      case "blank" => Functions.blank
      case "move" => Functions.move
      case "stop" => Functions.stop
      case "turn" => Functions.turn
      case "voyage" => Functions.voyage
      case "print dir" => Functions.printDirection
      case _ => throw new IllegalArgumentException("Unknown function: '" + name + "'")
    }
  }

}
