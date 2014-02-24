package com.quane.little.ide.view

import com.quane.little.ide.presenter.BlockPresenter

/** An implementation of [[com.quane.little.ide.view.ConditionalView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockConditionalView
  extends ConditionalView
  with MockView {

  // TODO is there a better mock condition here?
  def setConditionStatement() = new BlockPresenter(new MockBlockView)

  def setThenBlock() = new BlockPresenter(new MockBlockView)

  def setElseBlock() = new BlockPresenter(new MockBlockView)

}
