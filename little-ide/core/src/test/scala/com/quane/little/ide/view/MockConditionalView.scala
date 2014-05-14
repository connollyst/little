package com.quane.little.ide.view

import com.escalatesoft.subcut.inject.BindingModule

/** An implementation of [[com.quane.little.ide.view.ConditionalView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockConditionalView(implicit val bindingModule: BindingModule)
  extends ConditionalView
  with MockView {

  // TODO is there a better mock condition here?
  def setConditionStatement() = new MockBlockView

  def setThenBlock() = new MockBlockView

  def setElseBlock() = new MockBlockView

}
