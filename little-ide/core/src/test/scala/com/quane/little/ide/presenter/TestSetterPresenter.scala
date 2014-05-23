package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language.data.Value
import com.quane.little.language._
import com.quane.little.ide.MockIDEBindingModule
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.math.{BasicMathOperation, Multiplication, Division}

/** Test cases for the [[com.quane.little.ide.presenter.SetterPresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestSetterPresenter extends WordSpec with ShouldMatchers with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  "SetterPresenter" should {
    "store name changes" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.name = "sean is cool"
      assert(presenter.name == "sean is cool")
    }

    /* Test View */

    "register itself with view immediately" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      verify(view).registerViewPresenter(presenter)
    }
    "initialize name in its view" in {
      val view = mockSetterView
      new SetterPresenter(view)
      verify(view).setName(anyString())
    }
    "propagate name change to its view" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.name = "sean is cool"
      verify(view).setName("sean is cool")
    }
    "update name when view is modified" in {
      val presenter = new SetterPresenter(mockSetterView)
      presenter.onNameChange("sean is cool")
      assert(presenter.name == "sean is cool")
    }
    "not propagated name change to view when view is modified" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.onNameChange("sean is cool")
      verify(view, never()).setName("sean is cool")
    }

    /* Test setting expressions for the value */

    "error when adding unknown expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      val error = intercept[IllegalArgumentException] {
        presenter.value = mock[Expression]
      }
      error.getMessage should startWith("Expression not supported")
    }

    /* Assert views are created for new expression values.. */

    "create a view for a new Value expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.value = Value("abc")
      verify(view).createValueExpression()
    }
    "create a view for a new Getter expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.value = new Getter("x")
      verify(view).createGetterExpression()
    }
    "create a view for a new Logic expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.value = new Logic(Value("x"), LogicOperation.GreaterThan, Value("y"))
      verify(view).createLogicExpression()
    }
    "create a view for a new Math expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.value = new Division(Value(137), Value(42))
      verify(view).createMathExpression()
    }
    "create a view for a new FunctionReference expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.value = new FunctionReference("MyFunction")
      verify(view).createFunctionReference()
    }

    /* Assert views are initialized for new expression values.. */

    "initialize the view for a new Value expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      val valueView = mock[ValueView]
      when(view.createValueExpression()).thenReturn(valueView)
      presenter.value = Value("abc")
      verify(valueView).setValue("abc")
    }
    "initialize the view for a new Getter expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      val valueView = mock[GetterView]
      when(view.createGetterExpression()).thenReturn(valueView)
      presenter.value = new Getter("x")
      verify(valueView).setName("x")
    }
    "initialize the view for a new Logic expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      val valueView = mock[LogicView]
      when(view.createLogicExpression()).thenReturn(valueView)
      when(valueView.createLeftLiteral()).thenReturn(new MockValueView)
      when(valueView.createRightLiteral()).thenReturn(new MockValueView)
      presenter.value = new Logic(Value("x"), LogicOperation.GreaterThan, Value("y"))
      verify(valueView).createLeftLiteral()
      verify(valueView).setOperation(LogicOperation.GreaterThan)
      verify(valueView).createRightLiteral()
    }
    "initialize the view for a new Math expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      val valueView = mock[MathView]
      when(view.createMathExpression()).thenReturn(valueView)
      when(valueView.createLeftLiteral()).thenReturn(new MockValueView)
      when(valueView.createRightLiteral()).thenReturn(new MockValueView)
      presenter.value = new Multiplication(Value(137), Value(42))
      verify(valueView).createLeftLiteral()
      verify(valueView).setOperation(BasicMathOperation.Multiply)
      verify(valueView).createRightLiteral()
    }
    "initialize the view for a new FunctionReference expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      val valueView = mock[FunctionReferenceView]
      when(view.createFunctionReference()).thenReturn(valueView)
      presenter.value = new FunctionReference("MyFunction")
      verify(valueView).setName("MyFunction")
    }

    /* Assert compilation values.. */

    "compile with correct name" in {
      val presenter = new SetterPresenter(mockSetterView)
      presenter.name = "abc"
      presenter.value = Value("xyz")
      val compiled = presenter.compile()
      assert(compiled.name == "abc")
    }
    "compile with Value expression" in {
      assertCompiledValue(Value("text"))
    }
    "compile with Getter expression" in {
      assertCompiledValue(new Getter("varName"))
    }
    "compile with FunctionReference expression" in {
      assertCompiledValue(new FunctionReference("funName"))
    }
    "error if compiled without expression" in {
      val presenter = new SetterPresenter(mockSetterView)
      val error = intercept[IllegalAccessException] {
        presenter.compile()
      }
      error.getMessage should be("No value expression set.")
    }

  }

  private def assertCompiledValue(value: Expression) = {
    val presenter = new SetterPresenter(mockSetterView)
    presenter.value = value
    val compiled = presenter.compile()
    assert(compiled.value == value)
  }

  /** Utility function for mocking out a [[com.quane.little.ide.view.FunctionArgumentView]]
    * which returns instances of the appropriate [[com.quane.little.ide.view.MockView]]
    * when asked to.
    *
    * @return the mock conditional view
    */
  private def mockSetterView: SetterView = {
    val view = mock[SetterView]
    when(view.createMathExpression()).thenReturn(new MockMathView)
    when(view.createLogicExpression()).thenReturn(new MockLogicView)
    when(view.createGetterExpression()).thenReturn(new MockGetterView)
    when(view.createValueExpression()).thenReturn(new MockValueView)
    when(view.createFunctionReference()).thenReturn(new MockFunctionReferenceView)
    view
  }


}
