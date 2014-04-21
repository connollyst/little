package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import com.quane.little.language._
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language.data.Value

@RunWith(classOf[JUnitRunner])
class TestBlockPresenter extends FunSuite with MockitoSugar {

  test("test listener registered") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    verify(view).registerViewPresenter(presenter)
  }

  /* Test adding steps to the block */

  test("should error when adding unknown expression") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    intercept[IllegalArgumentException] {
      presenter.add(mock[Expression])
    }
  }

  test("test set statement added to view") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val setPresenter = new SetStatementPresenter(new MockSetStatementView)
    when[SetStatementPresenter[_]](view.addSetStatement()).thenReturn(setPresenter)
    presenter.add(new SetStatement("x", Value("y")))
    verify(view).addSetStatement()
  }

  test("test set statement added to presenter") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val setPresenter = new SetStatementPresenter(new MockSetStatementView)
    when[SetStatementPresenter[_]](view.addSetStatement()).thenReturn(setPresenter)
    presenter.add(new SetStatement("x", Value("y")))
    assert(presenter.steps.contains(setPresenter))
  }

  test("test set statement initialized") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val setPresenter = mock[SetStatementPresenter[SetStatementView]]
    when[SetStatementPresenter[_]](view.addSetStatement()).thenReturn(setPresenter)
    val statement = new SetStatement("x", Value("y"))
    presenter.add(statement)
    verify(setPresenter).initialize(statement)
  }

  test("test get statement added to view") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val getPresenter = new GetStatementPresenter(new MockGetStatementView)
    when[GetStatementPresenter[_]](view.addGetStatement()).thenReturn(getPresenter)
    presenter.add(new GetStatement("x"))
    verify(view).addGetStatement()
  }

  test("test get statement added to presenter") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val getPresenter = new GetStatementPresenter(new MockGetStatementView)
    when[GetStatementPresenter[_]](view.addGetStatement()).thenReturn(getPresenter)
    presenter.add(new GetStatement("x"))
    assert(presenter.steps.contains(getPresenter))
  }

  test("test get statement initialized") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val getPresenter = mock[GetStatementPresenter[GetStatementView]]
    when[GetStatementPresenter[_]](view.addGetStatement()).thenReturn(getPresenter)
    val statement = new GetStatement("x")
    presenter.add(statement)
    verify(getPresenter).initialize(statement)
  }

  test("test print statement added to view") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val printPresenter = new PrintStatementPresenter(new MockPrintStatementView)
    when[PrintStatementPresenter[_]](view.addPrintStatement()).thenReturn(printPresenter)
    presenter.add(new PrintStatement(Value("x")))
    verify(view).addPrintStatement()
  }

  test("test print statement added to presenter") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val printPresenter = new PrintStatementPresenter(new MockPrintStatementView)
    when[PrintStatementPresenter[_]](view.addPrintStatement()).thenReturn(printPresenter)
    presenter.add(new PrintStatement(Value("x")))
    assert(presenter.steps.contains(printPresenter))
  }

  test("test print statement initialized with value") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val printPresenter = mock[PrintStatementPresenter[PrintStatementView]]
    when[PrintStatementPresenter[_]](view.addPrintStatement()).thenReturn(printPresenter)
    val statement = new PrintStatement(Value("x"))
    presenter.add(statement)
    verify(printPresenter).initialize(statement)
  }

  test("test function reference added to view") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val functionPresenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    when[FunctionReferencePresenter[_]](view.addFunctionReference()).thenReturn(functionPresenter)
    presenter.add(new FunctionReference("funName"))
    verify(view).addFunctionReference()
  }

  test("test function reference added to presenter") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val functionPresenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    when[FunctionReferencePresenter[_]](view.addFunctionReference()).thenReturn(functionPresenter)
    presenter.add(new FunctionReference("funName"))
    assert(presenter.steps.contains(functionPresenter))
  }

  test("test function reference initialized with value") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val functionPresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
    when[FunctionReferencePresenter[_]](view.addFunctionReference()).thenReturn(functionPresenter)
    val statement = new FunctionReference("funName")
    presenter.add(statement)
    verify(functionPresenter).initialize(statement)
  }

}
