package com.quane.little.ide.view

import com.quane.little.ide.presenter._

/** An implementation of [[com.quane.little.ide.view.BlockView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockBlockView
  extends BlockView {

  def addSetStatement(): SetPresenter[_] = ???

  def addGetStatement(): GetPresenter[_] = ???

  def addPrintStatement(): PrintPresenter[MockPrintStatementView] =
    new PrintPresenter(new MockPrintStatementView)

  def addConditionalStatement(): ConditionalPresenter[_] = ???

  def addFunctionReference(): FunctionReferencePresenter[_] = ???

}
