package com.quane.little.ide.view

/** An implementation of [[com.quane.little.ide.view.ValueView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockValueView
  extends ValueView
  with MockView {

  def setValue(value: String) = Unit

}
