package com.quane.little.ide.view

import com.quane.little.ide.presenter._

/** An implementation of [[com.quane.little.ide.view.BlockView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockBlockView
  extends BlockView {

  def addConditional() = new ConditionalPresenter(new MockConditionalView)

  def addConditional(index: Int) = new ConditionalPresenter(new MockConditionalView)

  def addGetStatement() = new GetStatementPresenter(new MockGetStatementView)

  def addGetStatement(index: Int) = new GetStatementPresenter(new MockGetStatementView)

  def addSetStatement() = new SetStatementPresenter(new MockSetStatementView)

  def addSetStatement(index: Int) = new SetStatementPresenter(new MockSetStatementView)

  def addPrintStatement() = new PrintStatementPresenter(new MockPrintStatementView)

  def addPrintStatement(index: Int) = new PrintStatementPresenter(new MockPrintStatementView)

  def addFunctionReference() = new FunctionReferencePresenter(new MockFunctionReferenceView)

  def addFunctionReference(index: Int) = new FunctionReferencePresenter(new MockFunctionReferenceView)

}
