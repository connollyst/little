package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language._
import com.quane.little.language.data.Value
import com.quane.little.ide.IDEBindingModule

@RunWith(classOf[JUnitRunner])
class TestBlockPresenter extends FunSuite with MockitoSugar {

  implicit val bindingModule = IDEBindingModule

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
    val setView = mock[SetStatementView]
    when(view.addSetStatement()).thenReturn(setView)
    presenter.add(new SetStatement("x", Value("y")))
    verify(view).addSetStatement()
  }

  test("test set statement added to presenter") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val setView = mock[SetStatementView]
    when(view.addSetStatement()).thenReturn(setView)
    presenter.add(new SetStatement("x", Value("y")))
    // TODO test isn't applicable as presenter comes from factory
    assert(presenter.steps.contains(setView))
  }

  test("test set statement initialized") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val setView = mock[SetStatementView]
    val setPresenter = mock[SetStatementPresenter[SetStatementView]]
    when(view.addSetStatement()).thenReturn(setView)
    val statement = new SetStatement("x", Value("y"))
    presenter.add(statement)
    // TODO test isn't applicable as presenter comes from factory
    verify(setPresenter).initialize(statement)
  }

  test("test get statement added to view") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val getView = mock[GetStatementView]
    when(view.addGetStatement()).thenReturn(getView)
    presenter.add(new GetStatement("x"))
    verify(view).addGetStatement()
  }

  test("test get statement added to presenter") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val getView = mock[GetStatementView]
    val getPresenter = mock[GetterPresenter[GetStatementView]]
    when(view.addGetStatement()).thenReturn(getView)
    presenter.add(new GetStatement("x"))
    // TODO test isn't applicable as presenter comes from factory
    assert(presenter.steps.contains(getPresenter))
  }

  test("test get statement initialized") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val getView = mock[GetStatementView]
    val getPresenter = mock[GetterPresenter[GetStatementView]]
    when(view.addGetStatement()).thenReturn(getView)
    val statement = new GetStatement("x")
    presenter.add(statement)
    // TODO test isn't applicable as presenter comes from factory
    verify(getPresenter).initialize(statement)
  }

  test("test print statement added to view") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val printView = mock[PrintStatementView]
    when(view.addPrintStatement()).thenReturn(printView)
    presenter.add(new PrintStatement(Value("x")))
    verify(view).addPrintStatement()
  }

  test("test print statement added to presenter") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val printView = mock[PrintStatementView]
    val printPresenter = mock[PrintStatementPresenter[PrintStatementView]]
    when(view.addPrintStatement()).thenReturn(printView)
    presenter.add(new PrintStatement(Value("x")))
    // TODO test isn't applicable as presenter comes from factory
    assert(presenter.steps.contains(printPresenter))
  }

  test("test print statement initialized with value") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val printView = mock[PrintStatementView]
    val printPresenter = mock[PrintStatementPresenter[PrintStatementView]]
    when(view.addPrintStatement()).thenReturn(printView)
    val statement = new PrintStatement(Value("x"))
    presenter.add(statement)
    // TODO test isn't applicable as presenter comes from factory
    verify(printPresenter).initialize(statement)
  }

  test("test function reference added to view") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val functionView = mock[FunctionReferenceView]
    when(view.addFunctionReference()).thenReturn(functionView)
    presenter.add(new FunctionReference("funName"))
    verify(view).addFunctionReference()
  }

  test("test function reference added to presenter") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val functionView = mock[FunctionReferenceView]
    val functionPresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
    when(view.addFunctionReference()).thenReturn(functionView)
    presenter.add(new FunctionReference("funName"))
    // TODO test isn't applicable as presenter comes from factory
    assert(presenter.steps.contains(functionPresenter))
  }

  test("test function reference initialized with value") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val functionView = mock[FunctionReferenceView]
    val functionPresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
    when(view.addFunctionReference()).thenReturn(functionView)
    val statement = new FunctionReference("funName")
    presenter.add(statement)
    // TODO test isn't applicable as presenter comes from factory
    verify(functionPresenter).initialize(statement)
  }

}
