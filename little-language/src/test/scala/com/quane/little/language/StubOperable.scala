package com.quane.little.language

/** A dummy [[Operable]] for testing purposes.
  *
  * @author Sean Connolly
  */
class StubOperable(var x: Float, var y: Float) extends Operable {

  def this() = this(0.0f, 0.0f)

  var direction: Int = 0

  var speed: Int = 0

}
