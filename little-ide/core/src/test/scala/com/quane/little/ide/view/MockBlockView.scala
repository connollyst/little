package com.quane.little.ide.view

import com.quane.little.ide.presenter._

/** An implementation of [[com.quane.little.ide.view.BlockView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockBlockView
  extends BlockView {

  def addSetStatement() = new SetPresenter(new MockSetStatementView)

  def addGetStatement() = new GetPresenter(new MockGetStatementView)

  def addPrintStatement() = new PrintPresenter(new MockPrintStatementView)

  def addConditionalStatement() = new ConditionalPresenter(new MockConditionalView)

  def addFunctionReference() = new FunctionReferencePresenter(new MockFunctionReferenceView)

}
