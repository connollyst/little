package com.quane.little.web.view

import vaadin.scala.Button
import vaadin.scala.Component
import vaadin.scala.CssLayout
import vaadin.scala.HorizontalLayout
import vaadin.scala.Label
import vaadin.scala.Measure
import vaadin.scala.TextField
import vaadin.scala.Units
import vaadin.scala.VerticalLayout
import vaadin.scala.Button.ClickEvent
import com.quane.little.view.LittleArgumentNumber

object FunctionDefinition {
  val Style = "l-function";
  val StyleBody = Style + "-body";
  val StyleHeadLeft = StyleBody + "-left";
  val StyleFoot = Style + "-foot";
  val StyleHead = Style + "-head";
  val StyleHeadNameField = StyleHead + "-name";
}

class FunctionDefinition(name: String) extends VerticalLayout {

  spacing = false;
  add(header());
  add(body());
  add(footer());

  def header(): Component = {
    new FunctionDefinitionHeader(name)
  }

  def body(): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    val bodyInner = new ExpressionList
    bodyLeft.height = new Measure(100, Units.pct)
    bodyLeft.styleName = FunctionDefinition.StyleHeadLeft
    body.addComponent(bodyLeft);
    body.addComponent(bodyInner);
    body.styleName = FunctionDefinition.StyleBody;
    body.spacing = false;
    initMockData(bodyInner);
    body;
  }

  def footer(): Component = {
    val footer = new CssLayout();
    footer.styleName = FunctionDefinition.StyleFoot;
    footer;
  }

  def initMockData(stepList: ExpressionList) = {
    stepList.add(
      new FunctionReference("point toward",
        new FunctionArgument("x"),
        new FunctionArgument("y")));
    stepList.add(
      new FunctionReference("move forward",
        new FunctionArgument("speed")));
    val ifElse = new Conditional("touching [location]");
    ifElse.addThen(new Expression("done"));
    ifElse.addElse(
      new FunctionReference("move toward",
        new FunctionArgument("x"),
        new FunctionArgument("y")));
    stepList.add(ifElse);
    stepList.add(new Expression("done"));
  }
}

class FunctionDefinitionHeader(name: String) extends HorizontalLayout {

  // TODO should be a label that, when clicked, becomes a text field

  def this() {
    this("");
  }

  styleName = FunctionDefinition.StyleHead;
  spacing = true
  initNameField
  initAddArgumentButton

  def initNameField = {
    val nameField = new TextField;
    nameField.value = name;
    nameField.styleName = FunctionDefinition.StyleHeadNameField;
    addComponent(nameField);
  }

  def initAddArgumentButton = {

    val addArgumentButton = Button(
      "+", {
        val header = FunctionDefinitionHeader.this;
        val children = header.components.size
        header.add(new FunctionDefinitionArgument, children - 1);
        () // how do I avoid this?
      })
    addComponent(addArgumentButton);
  }

}