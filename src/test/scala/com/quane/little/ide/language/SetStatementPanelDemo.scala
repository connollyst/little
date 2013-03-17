package com.quane.little.ide.language

import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication
import com.quane.little.ide.language.data.TextFieldPanel
import com.quane.little.ide.language.memory.PointerPanel
import com.quane.little.ide.language.statement.SetPointerStatementPanel

object SetStatementPanelDemo extends SimpleSwingApplication {

    def top = new MainFrame {
        contents = initContents
    }

    private def initContents: FunctionPanel = {
        val fun = new FunctionPanel
        fun.contents += new SetPointerStatementPanel(new PointerPanel, new TextFieldPanel)
        fun
    }
}