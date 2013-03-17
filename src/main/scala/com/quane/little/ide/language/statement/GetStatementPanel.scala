package com.quane.little.ide.language.statement

import com.quane.little.ide.language.memory.PointerPanel

/** A Swing panel to represent a [[GetStatementExpression]].
  *
  * @author Sean Connolly
  */
abstract class GetStatementPanel extends StatementPanel

/** A Swing panel to represent a [[GetStatementExpression]] whose input is a
  * provided by a [[Pointer]].
  *
  * @author Sean Connolly
  */
class GetPointerStatementPanel(pointerPanel: PointerPanel)
        extends GetStatementPanel {

    contents += pointerPanel
}