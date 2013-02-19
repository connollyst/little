package com.quane.little.language

/** A block is a piece of code with it's own scope. For example: a function or
  * a while loop.
  *
  * @author Sean Connolly
  */
abstract class Block(scope: Scope)
        extends Expression[Any]
        with Scope {

}