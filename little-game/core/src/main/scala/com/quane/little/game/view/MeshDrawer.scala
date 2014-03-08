package com.quane.little.game.view

import com.quane.little.game.entity.WorldEdge

class MeshDrawer {

  //  private val whiteShader = createShader()
  //  private val allMeshes = new ListBuffer[Mesh]

  def begin() {
    //    whiteShader.begin()
    throw new NotImplementedError("TODO implement MeshDrawer")
  }

  def end() {
    //    whiteShader.end()
    throw new NotImplementedError("TODO implement MeshDrawer")
  }

  def dispose() {
    //    whiteShader.dispose()
    //    allMeshes.foreach(_.dispose())
    throw new NotImplementedError("TODO implement MeshDrawer")
  }

  //  def createMesh(isStatic: Boolean, vertices: Array[Float]): Mesh = {
  //    val mesh = new Mesh(isStatic, vertices.length, 0,
  //      new VertexAttribute(Usage.Position, 2, "a_position"))
  //    mesh.setVertices(vertices)
  //    allMeshes += mesh
  //    mesh
  //    throw new NotImplementedError("TODO implement MeshDrawer")
  //  }

  //  def createShader(): ShaderProgram = {
  //    // this shader tells opengl where to put things
  //    val vertexShader =
  //      "attribute vec4 a_position;    \n" +
  //        "void main()                   \n" +
  //        "{                             \n" +
  //        "   gl_Position = a_position;  \n" +
  //        "}                             \n"
  //
  //    // this one tells it what goes in between the points (i.e
  //    // colour/texture)
  //    val fragmentShader =
  //      "#ifdef GL_ES                \n" +
  //        "precision mediump float;    \n" +
  //        "#endif                      \n" +
  //        "void main()                 \n" +
  //        "{                           \n" +
  //        "  gl_FragColor = vec4(1.0,1.0,1.0,0.2);	\n" +
  //        "}"
  //
  //    // make an actual shader from our strings
  //    val meshShader = new ShaderProgram(vertexShader, fragmentShader)
  //    //    allShaders += meshShader
  //
  //    // check there's no shader compile errors
  //    if (!meshShader.isCompiled)
  //      throw new IllegalStateException(meshShader.getLog)
  //
  //    meshShader
  //  }

  def drawWall(wall: WorldEdge) {
    // TODO how to draw rectangles with triangles
    // http://openglbook.com/the-book/chapter-2-vertices-and-shapes/
    //    drawMesh(wall.mesh, whiteShader)
    throw new NotImplementedError("TODO implement MeshDrawer")
  }

  //  def drawMesh(mesh: Mesh, shader: ShaderProgram) {
  //    mesh.render(shader, GL20.GL_TRIANGLES)
  //  }


}
