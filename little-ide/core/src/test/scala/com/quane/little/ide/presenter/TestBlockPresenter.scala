package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view.{PrintStatementView, GetStatementView, SetStatementView, BlockView}
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.language.{PrintStatement, GetStatement, Scope, SetStatement}

@RunWith(classOf[JUnitRunner])
class TestBlockPresenter extends FunSuite with MockitoSugar {

  test("test listener registered") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    verify(view).addViewListener(presenter)
  }

  /* Test adding steps to the block */

  test("test set statement added to view") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val setPresenter = mock[SetStatementPresenter[SetStatementView]]
    when[SetStatementPresenter[_]](view.addSetStatement()).thenReturn(setPresenter)
    presenter.add(new SetStatement(mock[Scope], "x", "y"))
    verify(view).addSetStatement()
  }

  test("test set statement added to presenter") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val setPresenter = mock[SetStatementPresenter[SetStatementView]]
    when[SetStatementPresenter[_]](view.addSetStatement()).thenReturn(setPresenter)
    presenter.add(new SetStatement(mock[Scope], "x", "y"))
    assert(presenter.steps.contains(setPresenter))
  }

  test("test set statement initialized with name") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val setPresenter = mock[SetStatementPresenter[SetStatementView]]
    when[SetStatementPresenter[_]](view.addSetStatement()).thenReturn(setPresenter)
    presenter.add(new SetStatement(mock[Scope], "x", "y"))
    verify(setPresenter).name = "x"
  }

  test("test set statement initialized with value") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val setPresenter = mock[SetStatementPresenter[SetStatementView]]
    when[SetStatementPresenter[_]](view.addSetStatement()).thenReturn(setPresenter)
    presenter.add(new SetStatement(mock[Scope], "x", "y"))
    // TODO when we figure out what should really be passed, update this test
    verify(setPresenter).value = "Value{value=y, type=StringValueType$}"
  }

  test("test get statement added to view") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val getPresenter = mock[GetStatementPresenter[GetStatementView]]
    when[GetStatementPresenter[_]](view.addGetStatement()).thenReturn(getPresenter)
    presenter.add(new GetStatement(mock[Scope], "x"))
    verify(view).addGetStatement()
  }

  test("test get statement added to presenter") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val getPresenter = mock[GetStatementPresenter[GetStatementView]]
    when[GetStatementPresenter[_]](view.addGetStatement()).thenReturn(getPresenter)
    presenter.add(new GetStatement(mock[Scope], "x"))
    assert(presenter.steps.contains(getPresenter))
  }

  test("test get statement initialized with name") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val getPresenter = mock[GetStatementPresenter[GetStatementView]]
    when[GetStatementPresenter[_]](view.addGetStatement()).thenReturn(getPresenter)
    presenter.add(new GetStatement(mock[Scope], "x"))
    verify(getPresenter).name = "x"
  }

  test("test print statement added to view") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val getPresenter = mock[GetStatementPresenter[GetStatementView]]
    when[GetStatementPresenter[_]](view.addGetStatement()).thenReturn(getPresenter)
    presenter.add(new GetStatement(mock[Scope], "x"))
    verify(view).addGetStatement()
  }

  test("test print statement added to presenter") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val printPresenter = mock[PrintStatementPresenter[PrintStatementView]]
    when[PrintStatementPresenter[_]](view.addPrintStatement()).thenReturn(printPresenter)
    presenter.add(new PrintStatement("x"))
    assert(presenter.steps.contains(printPresenter))
  }

  test("test print statement initialized with value") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    val printPresenter = mock[PrintStatementPresenter[PrintStatementView]]
    when[PrintStatementPresenter[_]](view.addPrintStatement()).thenReturn(printPresenter)
    presenter.add(new PrintStatement("x"))
    // TODO when we figure out what should really be passed, update this test
    verify(printPresenter).value = "Value{value=x, type=StringValueType$}"
  }

}
