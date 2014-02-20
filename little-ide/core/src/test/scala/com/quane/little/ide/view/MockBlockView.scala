package com.quane.little.ide.view

import com.quane.little.ide.presenter._

/** An implementation of [[com.quane.little.ide.view.BlockView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockBlockView
  extends BlockView {

  def addSetStatement() = new SetStatementPresenter(new MockSetStatementView)

  def addGetStatement() = new GetStatementPresenter(new MockGetStatementView)

  def addPrintStatement() = new PrintStatementPresenter(new MockPrintStatementView)

  def addConditional() = new ConditionalPresenter(new MockConditionalView)

  def addFunctionReference() = new FunctionReferencePresenter(new MockFunctionReferenceView)

}
