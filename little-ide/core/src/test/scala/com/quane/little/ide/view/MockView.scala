package com.quane.little.ide.view

/** A mock view trait for implementing base view functionality in tests.
  *
  * @author Sean Connolly
  */
trait MockView {

  // mock views are never added to anything so don't need to be removed
  def remove(): Unit = Unit

}
