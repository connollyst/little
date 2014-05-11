package com.quane.little.ide.view.html

import org.junit.runner.RunWith
import org.junit.Assert._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfter, FlatSpec}
import org.mockito.Mockito._
import com.quane.little.data.model.RecordId
import com.quane.little.ide.presenter.{PresenterAcceptsStatement, PresenterAcceptsExpression}
import com.quane.little.ide.view.{View, ViewPresenter}
import com.quane.little.data.service.{ExpressionService, StatementService}

@RunWith(classOf[JUnitRunner])
class TestExpressionMenu extends FlatSpec with ShouldMatchers with BeforeAndAfter with MockitoSugar {

  var indexCalled: Boolean = _

  before {
    indexCalled = false
  }

  "ExpressionMenu" should "not resolve index until 'add expression' clicked" in {
    val menu = mockMenu()
    assertFalse("index should not be resolved yet", indexCalled)
    menu.addExpressionClicked(new RecordId(ExpressionService.PrimitiveGet))
    assertTrue("index should have been resolved", indexCalled)
  }
  it should "not resolve index until 'add statement' clicked" in {
    val menu = mockMenu()
    assertFalse("index should not be resolved yet", indexCalled)
    menu.addStatementClicked(new RecordId(StatementService.PrimitiveSet))
    assertTrue("index should have been resolved", indexCalled)
  }
  it should "not resolve index until 'add function' clicked" in {
    val menu = mockMenu()
    assertFalse("index should not be resolved yet", indexCalled)
    menu.addFunctionClicked(new RecordId("1234"))
    assertTrue("index should have been resolved", indexCalled)
  }

  /** Mock up a menu for testing, including the view and presenter it is
    * attached to.
    *
    * @return a mock menu
    */
  def mockMenu(): ExpressionMenu[_] = {
    val presenter = mock[Presenter]
    val view = mock[View[Presenter]]
    when(view.presenter).thenReturn(presenter)
    new ExpressionMenu(view, index)
  }

  /** An 'index' function which can be passed to the [[ExpressionMenu]] for
    * testing, letting us verify that it was called.
    *
    * @return 0, every time
    */
  def index: Int = {
    indexCalled = true
    0
  }

  /** A presenter trait that can be mocked out in testing.
    *
    * @author Sean Connolly
    */
  trait Presenter
    extends ViewPresenter
    with PresenterAcceptsExpression
    with PresenterAcceptsStatement

  // TODO with PresenterAcceptsFunctionReference

}
