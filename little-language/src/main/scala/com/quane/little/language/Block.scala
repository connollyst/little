package com.quane.little.language

import com.quane.little.language.data.Value

/** A block is a piece of code with it's own scope.
  *
  * @author Sean Connolly
  */
abstract class Block[T <: Value](scope: Scope)
  extends Expression[T]
  with Scope