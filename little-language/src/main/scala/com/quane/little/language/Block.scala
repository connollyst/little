package com.quane.little.language


/** A block is a piece of code with it's own scope.
  *
  * @author Sean Connolly
  */
abstract class Block(scope: Scope)
  extends Expression
  with Scope