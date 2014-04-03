package example

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper._
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
import com.fasterxml.jackson.databind.{JavaType, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

class Bar

trait Baz

class Foo {
  val list = List[String]()
}

class FooBar {
  val list = List[Bar]()
}

class FooBaz {
  val list = List[Baz]()
}

@RunWith(classOf[JUnitRunner])
class JacksonBugTest extends FlatSpec with ShouldMatchers {

  val alwaysUse = true
  val neverUse = false

  "jackson" should "serialize String with basic mapper" in {
    serialize("foo", basicMapper)
  }
  it should "serialize String with mapper with default typing enabled" in {
    serialize("foo", defaultTypeMapper)
  }
  it should "serialize String with mapper with custom 'java lang object' typing" in {
    serialize("foo", customTypeMapper(DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize String with mapper with custom 'java lang object' typing (always)" in {
    serialize("foo", customTypeMapper(alwaysUse, DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize String with mapper with custom 'java lang object' typing (never)" in {
    serialize("foo", customTypeMapper(neverUse, DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize String with mapper with custom 'java lang object' typing (disabled)" in {
    serialize("foo", customTypeMapperDisabled(DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize String with mapper with custom 'object and non-concrete' typing" in {
    serialize("foo", customTypeMapper(DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize String with mapper with custom 'object and non-concrete' typing (always)" in {
    serialize("foo", customTypeMapper(alwaysUse, DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize String with mapper with custom 'object and non-concrete' typing (never)" in {
    serialize("foo", customTypeMapper(neverUse, DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize String with mapper with custom 'object and non-concrete' typing (disabled)" in {
    serialize("foo", customTypeMapperDisabled(DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize String with mapper with custom 'non-concrete and arrays' typing" in {
    serialize("foo", customTypeMapper(DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize String with mapper with custom 'non-concrete and arrays' typing (always)" in {
    serialize("foo", customTypeMapper(alwaysUse, DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize String with mapper with custom 'non-concrete and arrays' typing (never)" in {
    serialize("foo", customTypeMapper(neverUse, DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize String with mapper with custom 'non-concrete and arrays' typing disabled" in {
    serialize("foo", customTypeMapperDisabled(DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize String with mapper with custom 'non-final' typing" in {
    serialize("foo", customTypeMapper(DefaultTyping.NON_FINAL))
  }
  it should "serialize String with mapper with custom 'non-final' typing (always)" in {
    serialize("foo", customTypeMapper(alwaysUse, DefaultTyping.NON_FINAL))
  }
  it should "serialize String with mapper with custom 'non-final' typing (never)" in {
    serialize("foo", customTypeMapper(neverUse, DefaultTyping.NON_FINAL))
  }
  it should "serialize String with mapper with custom 'non-final' typing disabled" in {
    serialize("foo", customTypeMapperDisabled(DefaultTyping.NON_FINAL))
  }
  it should "serialize List[String] with basic mapper" in {
    serialize(List[String]("foo"), basicMapper)
  }
  it should "serialize List[String] with mapper with default typing enabled" in {
    serialize(List[String]("foo"), defaultTypeMapper)
  }
  it should "serialize List[String] with mapper with custom 'java lang object' typing" in {
    serialize(List[String]("foo"), customTypeMapper(DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize List[String] with mapper with custom 'java lang object' typing (always)" in {
    serialize(List[String]("foo"), customTypeMapper(alwaysUse, DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize List[String] with mapper with custom 'java lang object' typing (never)" in {
    serialize(List[String]("foo"), customTypeMapper(neverUse, DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize List[String] with mapper with custom 'java lang object' typing (disabled)" in {
    serialize(List[String]("foo"), customTypeMapperDisabled(DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize List[String] with mapper with custom 'object and non-concrete' typing" in {
    serialize(List[String]("foo"), customTypeMapper(DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize List[String] with mapper with custom 'object and non-concrete' typing (always)" in {
    serialize(List[String]("foo"), customTypeMapper(alwaysUse, DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize List[String] with mapper with custom 'object and non-concrete' typing (never)" in {
    serialize(List[String]("foo"), customTypeMapper(neverUse, DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize List[String] with mapper with custom 'object and non-concrete' typing (disabled)" in {
    serialize(List[String]("foo"), customTypeMapperDisabled(DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize List[String] with mapper with custom 'non-concrete and arrays' typing" in {
    serialize(List[String]("foo"), customTypeMapper(DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize List[String] with mapper with custom 'non-concrete and arrays' typing (always)" in {
    serialize(List[String]("foo"), customTypeMapper(alwaysUse, DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize List[String] with mapper with custom 'non-concrete and arrays' typing (never)" in {
    serialize(List[String]("foo"), customTypeMapper(neverUse, DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize List[String] with mapper with custom 'non-concrete and arrays' typing disabled" in {
    serialize(List[String]("foo"), customTypeMapperDisabled(DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize List[String] with mapper with custom 'non-final' typing" in {
    serialize(List[String]("foo"), customTypeMapper(DefaultTyping.NON_FINAL))
  }
  it should "serialize List[String] with mapper with custom 'non-final' typing (always)" in {
    serialize(List[String]("foo"), customTypeMapper(alwaysUse, DefaultTyping.NON_FINAL))
  }
  it should "serialize List[String] with mapper with custom 'non-final' typing (never)" in {
    serialize(List[String]("foo"), customTypeMapper(neverUse, DefaultTyping.NON_FINAL))
  }
  it should "serialize List[String] with mapper with custom 'non-final' typing disabled" in {
    serialize(List[String]("foo"), customTypeMapperDisabled(DefaultTyping.NON_FINAL))
  }
  it should "serialize Foo with basic mapper" in {
    serialize(new Foo, basicMapper)
  }
  it should "serialize Foo with mapper with default typing enabled" in {
    serialize(new Foo, defaultTypeMapper)
  }
  it should "serialize Foo with mapper with custom 'java lang object' typing" in {
    serialize(new Foo, customTypeMapper(DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize Foo with mapper with custom 'java lang object' typing (always)" in {
    serialize(new Foo, customTypeMapper(alwaysUse, DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize Foo with mapper with custom 'java lang object' typing (never)" in {
    serialize(new Foo, customTypeMapper(neverUse, DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize Foo with mapper with custom 'java lang object' typing (disabled)" in {
    serialize(new Foo, customTypeMapperDisabled(DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize Foo with mapper with custom 'object and non-concrete' typing" in {
    serialize(new Foo, customTypeMapper(DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize Foo with mapper with custom 'object and non-concrete' typing (always)" in {
    serialize(new Foo, customTypeMapper(alwaysUse, DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize Foo with mapper with custom 'object and non-concrete' typing (never)" in {
    serialize(new Foo, customTypeMapper(neverUse, DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize Foo with mapper with custom 'object and non-concrete' typing (disabled)" in {
    serialize(new Foo, customTypeMapperDisabled(DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize Foo with mapper with custom 'non-concrete and arrays' typing" in {
    serialize(new Foo, customTypeMapper(DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize Foo with mapper with custom 'non-concrete and arrays' typing (always)" in {
    serialize(new Foo, customTypeMapper(alwaysUse, DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize Foo with mapper with custom 'non-concrete and arrays' typing (never)" in {
    serialize(new Foo, customTypeMapper(neverUse, DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize Foo with mapper with custom 'non-concrete and arrays' typing disabled" in {
    serialize(new Foo, customTypeMapperDisabled(DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize Foo with mapper with custom 'non-final' typing" in {
    serialize(new Foo, customTypeMapper(DefaultTyping.NON_FINAL))
  }
  it should "serialize Foo with mapper with custom 'non-final' typing (always)" in {
    serialize(new Foo, customTypeMapper(alwaysUse, DefaultTyping.NON_FINAL))
  }
  it should "serialize Foo with mapper with custom 'non-final' typing (never)" in {
    serialize(new Foo, customTypeMapper(neverUse, DefaultTyping.NON_FINAL))
  }
  it should "serialize Foo with mapper with custom 'non-final' typing disabled" in {
    serialize(new Foo, customTypeMapperDisabled(DefaultTyping.NON_FINAL))
  }
  it should "serialize FooBar with basic mapper" in {
    serialize(new FooBar, basicMapper)
  }
  it should "serialize FooBar with mapper with default typing enabled" in {
    serialize(new FooBar, defaultTypeMapper)
  }
  it should "serialize FooBar with mapper with custom 'java lang object' typing" in {
    serialize(new FooBar, customTypeMapper(DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize FooBar with mapper with custom 'java lang object' typing (always)" in {
    serialize(new FooBar, customTypeMapper(alwaysUse, DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize FooBar with mapper with custom 'java lang object' typing (never)" in {
    serialize(new FooBar, customTypeMapper(neverUse, DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize FooBar with mapper with custom 'java lang object' typing (disabled)" in {
    serialize(new FooBar, customTypeMapperDisabled(DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize FooBar with mapper with custom 'object and non-concrete' typing" in {
    serialize(new FooBar, customTypeMapper(DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBar with mapper with custom 'object and non-concrete' typing (always)" in {
    serialize(new FooBar, customTypeMapper(alwaysUse, DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBar with mapper with custom 'object and non-concrete' typing (never)" in {
    serialize(new FooBar, customTypeMapper(neverUse, DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBar with mapper with custom 'object and non-concrete' typing (disabled)" in {
    serialize(new FooBar, customTypeMapperDisabled(DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBar with mapper with custom 'non-concrete and arrays' typing" in {
    serialize(new FooBar, customTypeMapper(DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBar with mapper with custom 'non-concrete and arrays' typing (always)" in {
    serialize(new FooBar, customTypeMapper(alwaysUse, DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBar with mapper with custom 'non-concrete and arrays' typing (never)" in {
    serialize(new FooBar, customTypeMapper(neverUse, DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBar with mapper with custom 'non-concrete and arrays' typing disabled" in {
    serialize(new FooBar, customTypeMapperDisabled(DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBar with mapper with custom 'non-final' typing" in {
    serialize(new FooBar, customTypeMapper(DefaultTyping.NON_FINAL))
  }
  it should "serialize FooBar with mapper with custom 'non-final' typing (always)" in {
    serialize(new FooBar, customTypeMapper(alwaysUse, DefaultTyping.NON_FINAL))
  }
  it should "serialize FooBar with mapper with custom 'non-final' typing (never)" in {
    serialize(new FooBar, customTypeMapper(neverUse, DefaultTyping.NON_FINAL))
  }
  it should "serialize FooBar with mapper with custom 'non-final' typing disabled" in {
    serialize(new FooBar, customTypeMapperDisabled(DefaultTyping.NON_FINAL))
  }
  it should "serialize FooBaz with basic mapper" in {
    serialize(new FooBaz, basicMapper)
  }
  it should "serialize FooBaz with mapper with default typing enabled" in {
    serialize(new FooBaz, defaultTypeMapper)
  }
  it should "serialize FooBaz with mapper with custom 'java lang object' typing" in {
    serialize(new FooBaz, customTypeMapper(DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize FooBaz with mapper with custom 'java lang object' typing (always)" in {
    serialize(new FooBaz, customTypeMapper(alwaysUse, DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize FooBaz with mapper with custom 'java lang object' typing (never)" in {
    serialize(new FooBaz, customTypeMapper(neverUse, DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize FooBaz with mapper with custom 'java lang object' typing (disabled)" in {
    serialize(new FooBaz, customTypeMapperDisabled(DefaultTyping.JAVA_LANG_OBJECT))
  }
  it should "serialize FooBaz with mapper with custom 'object and non-concrete' typing" in {
    serialize(new FooBaz, customTypeMapper(DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBaz with mapper with custom 'object and non-concrete' typing (always)" in {
    serialize(new FooBaz, customTypeMapper(alwaysUse, DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBaz with mapper with custom 'object and non-concrete' typing (never)" in {
    serialize(new FooBaz, customTypeMapper(neverUse, DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBaz with mapper with custom 'object and non-concrete' typing (disabled)" in {
    serialize(new FooBaz, customTypeMapperDisabled(DefaultTyping.OBJECT_AND_NON_CONCRETE))
  }
  it should "serialize FooBaz with mapper with custom 'non-concrete and arrays' typing" in {
    serialize(new FooBaz, customTypeMapper(DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBaz with mapper with custom 'non-concrete and arrays' typing (always)" in {
    serialize(new FooBaz, customTypeMapper(alwaysUse, DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBaz with mapper with custom 'non-concrete and arrays' typing (never)" in {
    serialize(new FooBaz, customTypeMapper(neverUse, DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBaz with mapper with custom 'non-concrete and arrays' typing disabled" in {
    serialize(new FooBaz, customTypeMapperDisabled(DefaultTyping.NON_CONCRETE_AND_ARRAYS))
  }
  it should "serialize FooBaz with mapper with custom 'non-final' typing" in {
    serialize(new FooBaz, customTypeMapper(DefaultTyping.NON_FINAL))
  }
  it should "serialize FooBaz with mapper with custom 'non-final' typing (always)" in {
    serialize(new FooBaz, customTypeMapper(alwaysUse, DefaultTyping.NON_FINAL))
  }
  it should "serialize FooBaz with mapper with custom 'non-final' typing (never)" in {
    serialize(new FooBaz, customTypeMapper(neverUse, DefaultTyping.NON_FINAL))
  }
  it should "serialize FooBaz with mapper with custom 'non-final' typing disabled" in {
    serialize(new FooBaz, customTypeMapperDisabled(DefaultTyping.NON_FINAL))
  }

  private def serialize(value: Any, mapper: ObjectMapper) = {
    mapper.writeValueAsString(value)
  }

  private def basicMapper: ObjectMapper = {
    val mapper = new ObjectMapper with ScalaObjectMapper
    mapper.registerModule(new DefaultScalaModule)
    mapper
  }

  private def defaultTypeMapper: ObjectMapper = {
    val mapper = new ObjectMapper with ScalaObjectMapper
    mapper.registerModule(new DefaultScalaModule)
    mapper.enableDefaultTyping()
    mapper
  }

  private def customTypeMapper(typing: DefaultTyping): ObjectMapper = {
    val mapper = new ObjectMapper with ScalaObjectMapper
    mapper.setDefaultTyping(customTypeResolver(typing))
    mapper.registerModule(new DefaultScalaModule)
    mapper
  }

  private def customTypeMapperDisabled(typing: DefaultTyping): ObjectMapper = {
    val mapper = customTypeMapper(typing)
    mapper.disableDefaultTyping()
    mapper
  }


  private def customTypeResolver(typing: DefaultTyping): TypeResolverBuilder[_] = {
    var typer: TypeResolverBuilder[_] = new DefaultTypeResolverBuilder(typing)
    typer = typer.init(JsonTypeInfo.Id.CLASS, null).asInstanceOf[TypeResolverBuilder[_]]
    typer = typer.inclusion(JsonTypeInfo.As.WRAPPER_ARRAY).asInstanceOf[TypeResolverBuilder[_]]
    typer
  }

  private def customTypeMapper(doUseForType: Boolean, typing: DefaultTyping): ObjectMapper = {
    val mapper = new ObjectMapper with ScalaObjectMapper
    mapper.setDefaultTyping(customTypeResolver(doUseForType, typing))
    mapper.registerModule(new DefaultScalaModule)
    mapper
  }

  private def customTypeResolver(doUseForType: Boolean, typing: DefaultTyping): TypeResolverBuilder[_] = {
    var typer: TypeResolverBuilder[_] = new DefaultTypeResolverBuilder(typing) {
      override def useForType(t: JavaType): Boolean = doUseForType
    }
    typer = typer.init(JsonTypeInfo.Id.CLASS, null).asInstanceOf[TypeResolverBuilder[_]]
    typer = typer.inclusion(JsonTypeInfo.As.WRAPPER_ARRAY).asInstanceOf[TypeResolverBuilder[_]]
    typer
  }

}