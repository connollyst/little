package com.quane.vaadin.scala

import com.vaadin.ui.ComboBox


/** A combobox whose options are the values of the given enumeration.
  *
  * @author Sean Connolly
  */
class EnumerationComboBox(enum: Enumeration) extends ComboBox {

  enum.values foreach {
    value => addItem(value.toString)
  }

}
