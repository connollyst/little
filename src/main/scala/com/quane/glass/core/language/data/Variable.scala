package com.quane.glass.core.language.data

/** A variable: the mutable association between a name and a value.
  *
  * @author Sean Connolly
  */
class Variable(val name: String, val value: Value) // can it be locked down more than Any?