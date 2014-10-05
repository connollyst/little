package com.quane.little.ide.presenter

import com.quane.little.ide.MockIDEBindingModule
import com.quane.little.ide.view._
import com.quane.little.language._
import com.quane.little.language.data.Value
import com.quane.little.language.math._
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

/** Test cases for the [[com.quane.little.ide.presenter.ConditionalPresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionArgumentPresenter extends WordSpec with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  "FunctionArgumentPresenter" should {
    "store name changes" in {
      val presenter = new FunctionArgumentPresenter(MockFunctionArgumentView.mocked)
      presenter.name = "sean is cool"
      assert(presenter.name == "sean is cool")
    }
    "register itself with view immediately" in {
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
      verify(view).registerViewPresenter(presenter)
    }

    /* Test View */

    "propagate name change to its view" in {
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
      presenter.name = "sean is cool"
      verify(view).setName("sean is cool")
    }

    /* Assert views are created for new expression values.. */

    "create a view for a new Value expression" in {
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
      presenter.value = Value("abc")
      verify(view).createValueExpression()
    }
    "create a view for a new Getter expression" in {
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
      presenter.value = new Getter("x")
      verify(view).createGetterExpression()
    }
    "create a view for a new Logic expression" in {
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
      presenter.value = new Logic(Value("x"), LogicOperation.GreaterThan, Value("y"))
      verify(view).createLogicExpression()
    }
    "create a view for a new Math expression" in {
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
      presenter.value = new Division(Value(137), Value(42))
      verify(view).createMathExpression()
    }
    "create a view for a new FunctionReference expression" in {
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
      presenter.value = new FunctionReference("MyFunction")
      verify(view).createFunctionReference()
    }

    /* Assert views are initialized for new expression values.. */

    "initialize the view for a new Value expression" in {
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
      val valueView = mock[ValueView]
      when(view.createValueExpression()).thenReturn(valueView)
      presenter.value = Value("abc")
      verify(valueView).setValue("abc")
    }
    "initialize the view for a new Getter expression" in {
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
      val valueView = mock[GetterView]
      when(view.createGetterExpression()).thenReturn(valueView)
      presenter.value = new Getter("x")
      verify(valueView).setName("x")
    }
    "initialize the view for a new Logic expression" in {
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
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
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
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
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
      val valueView = mock[FunctionReferenceView]
      when(view.createFunctionReference()).thenReturn(valueView)
      presenter.value = new FunctionReference("MyFunction")
      verify(valueView).setName("MyFunction")
    }

    /* Assert compilation values.. */

    "compile with Value expression" in {
      assertCompiledValue(Value("text"))
    }
    "compile with Getter expression" in {
      assertCompiledValue(new Getter("MyVariable"))
    }
    "compile with FunctionReference" in {
      assertCompiledValue(new FunctionReference("MyFunction"))
    }
    "error if compiled without expression" in {
      val view = MockFunctionArgumentView.mocked
      val presenter = new FunctionArgumentPresenter(view)
      intercept[IllegalAccessException] {
        presenter.compile()
      }
    }
  }

  private def assertCompiledValue(value: Expression) = {
    val presenter = new FunctionArgumentPresenter(MockFunctionArgumentView.mocked)
    presenter.value = value
    val compiled = presenter.compile()
    assert(compiled == value)
  }

}
