package com.quane.little.ide.view

import com.quane.little.ide.presenter._

/** An implementation of [[com.quane.little.ide.view.BlockView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockBlockView
  extends BlockView {

  def addSetStatement(): SetPresenter[MockSetStatementView] =
    new SetPresenter(new MockSetStatementView)

  def addGetStatement(): GetPresenter[MockGetStatementView] =
    new GetPresenter(new MockGetStatementView)

  def addPrintStatement(): PrintPresenter[MockPrintStatementView] =
    new PrintPresenter(new MockPrintStatementView)

  def addConditionalStatement(): ConditionalPresenter[_] = ???

  def addFunctionReference(): FunctionReferencePresenter[_] = ???

}
