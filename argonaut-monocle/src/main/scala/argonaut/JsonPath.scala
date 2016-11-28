package argonaut

import argonaut.JsonMonocle._
import argonaut.JsonObjectMonocle._
import monocle.function.{At, FilterIndex, Index}
import monocle.std.list._
import monocle.{Fold, Optional, Prism, Traversal}

import scala.language.dynamics

final case class JsonPath(json: Optional[Json, Json]) extends Dynamic {
  def `null`: Optional[Json, Unit] = json composePrism jNullPrism
  def boolean: Optional[Json, Boolean] = json composePrism jBoolPrism
  def byte: Optional[Json, Byte] = json composePrism jBytePrism
  def short: Optional[Json, Short] = json composePrism jShortPrism
  def int: Optional[Json, Int] = json composePrism jIntPrism
  def long: Optional[Json, Long] = json composePrism jLongPrism
  def bigInt: Optional[Json, BigInt] = json composePrism jBigIntPrism
  def bigDecimal: Optional[Json, BigDecimal] = json composePrism jBigDecimalPrism
  def number: Optional[Json, JsonNumber] = json composePrism jNumberPrism
  def string: Optional[Json, String] = json composePrism jStringPrism
  def arr: Optional[Json, List[Json]] = json composePrism jArrayPrism
  def obj: Optional[Json, JsonObject] = json composePrism jObjectPrism

  def at(field: String): Optional[Json, Option[Json]] =
    json composePrism jObjectPrism composeLens At.at(field)

  def selectDynamic(field: String): JsonPath =
    JsonPath(json composePrism jObjectPrism composeOptional Index.index(field))

  def index(i: Int): JsonPath =
    JsonPath(json composePrism jArrayPrism composeOptional Index.index(i))

  def each: JsonTraversalPath =
    JsonTraversalPath(json composeTraversal jDescendants)

  def filterByIndex(p: Int => Boolean): JsonTraversalPath =
    JsonTraversalPath(arr composeTraversal FilterIndex.filterIndex(p))

  def filterByField(p: String => Boolean): JsonTraversalPath =
    JsonTraversalPath(obj composeTraversal FilterIndex.filterIndex(p))

  final def filter(p: Json => Boolean): JsonFoldPath =
    JsonFoldPath(unsafeFilter(p).json.asFold)

  final def unsafeFilter(p: Json => Boolean): JsonPath =
    JsonPath(json composePrism UnsafeOptics.select(p))

  final def unsafeAs[A: CodecJson]: Optional[Json, A] =
    json composePrism UnsafeOptics.parse
}

object JsonPath {
  val root: JsonPath = JsonPath(Optional.id)
}

final case class JsonTraversalPath(json: Traversal[Json, Json]) extends Dynamic {
  def `null`: Traversal[Json, Unit] = json composePrism jNullPrism
  def boolean: Traversal[Json, Boolean] = json composePrism jBoolPrism
  def byte: Traversal[Json, Byte] = json composePrism jBytePrism
  def short: Traversal[Json, Short] = json composePrism jShortPrism
  def int: Traversal[Json, Int] = json composePrism jIntPrism
  def long: Traversal[Json, Long] = json composePrism jLongPrism
  def bigInt: Traversal[Json, BigInt] = json composePrism jBigIntPrism
  def bigDecimal: Traversal[Json, BigDecimal] = json composePrism jBigDecimalPrism
  def number: Traversal[Json, JsonNumber] = json composePrism jNumberPrism
  def string: Traversal[Json, String] = json composePrism jStringPrism
  def arr: Traversal[Json, List[Json]] = json composePrism jArrayPrism
  def obj: Traversal[Json, JsonObject] = json composePrism jObjectPrism

  def at(field: String): Traversal[Json, Option[Json]] =
    json composePrism jObjectPrism composeLens At.at(field)

  def selectDynamic(field: String): JsonTraversalPath =
    JsonTraversalPath(json composePrism jObjectPrism composeOptional Index.index(field))

  def index(i: Int): JsonTraversalPath =
    JsonTraversalPath(json composePrism jArrayPrism composeOptional Index.index(i))

  def each: JsonTraversalPath =
    JsonTraversalPath(json composeTraversal jDescendants)

  def filterByIndex(p: Int => Boolean): JsonTraversalPath =
    JsonTraversalPath(arr composeTraversal FilterIndex.filterIndex(p))

  def filterByField(p: String => Boolean): JsonTraversalPath =
    JsonTraversalPath(obj composeTraversal FilterIndex.filterIndex(p))

  final def filter(p: Json => Boolean): JsonFoldPath =
    JsonFoldPath(unsafeFilter(p).json.asFold)

  final def unsafeFilter(p: Json => Boolean): JsonTraversalPath =
    JsonTraversalPath(json composePrism UnsafeOptics.select(p))

  final def unsafeAs[A: CodecJson]: Traversal[Json, A] =
    json composePrism UnsafeOptics.parse
}

final case class JsonFoldPath(json: Fold[Json, Json]) extends Dynamic {
  final def `null`: Fold[Json, Unit] = json composePrism jNullPrism
  final def boolean: Fold[Json, Boolean] = json composePrism jBoolPrism
  final def byte: Fold[Json, Byte] = json composePrism jBytePrism
  final def short: Fold[Json, Short] = json composePrism jShortPrism
  final def int: Fold[Json, Int] = json composePrism jIntPrism
  final def long: Fold[Json, Long] = json composePrism jLongPrism
  final def bigInt: Fold[Json, BigInt] = json composePrism jBigIntPrism
  final def bigDecimal: Fold[Json, BigDecimal] = json composePrism jBigDecimalPrism
  final def number: Fold[Json, JsonNumber] = json composePrism jNumberPrism
  final def string: Fold[Json, String] = json composePrism jStringPrism
  final def arr: Fold[Json, List[Json]] = json composePrism jArrayPrism
  final def obj: Fold[Json, JsonObject] = json composePrism jObjectPrism

  final def at(field: String): Fold[Json, Option[Json]] =
    json composePrism jObjectPrism composeLens At.at(field)

  final def selectDynamic(field: String): JsonFoldPath =
    JsonFoldPath(json composePrism jObjectPrism composeOptional Index.index(field))

  final def index(i: Int): JsonFoldPath =
    JsonFoldPath(json composePrism jArrayPrism composeOptional Index.index(i))

  final def each: JsonFoldPath =
    JsonFoldPath(json composeTraversal jDescendants)

  final def filterByIndex(p: Int => Boolean): JsonFoldPath =
    JsonFoldPath(arr composeTraversal FilterIndex.filterIndex(p))

  final def filterByField(p: String => Boolean): JsonFoldPath =
    JsonFoldPath(obj composeTraversal FilterIndex.filterIndex(p))

  final def filter(p: Json => Boolean): JsonFoldPath =
    JsonFoldPath(json composePrism UnsafeOptics.select(p))

  final def as[A: CodecJson]: Fold[Json, A] =
    json composePrism UnsafeOptics.parse
}

object UnsafeOptics {
  /**
    * Decode a value at the current location.
    *
    * Note that this operation is not lawful, since decoding is not injective.
    */
  def parse[A](implicit codec: CodecJson[A]): Prism[Json, A] =
    Prism[Json, A](codec.decodeJson(_).toOption)(codec.encode)

  /**
    * Select if a value matches a predicate
    *
    * Note that this operation is not lawful because the predicate could be invalidated with set or modify.
    * However `select(_.a > 10) composeLens b` is safe because once we zoom into `b`, we cannot change `a` anymore.
    */
  def select[A](p: A => Boolean): Prism[A, A] =
    Prism[A, A](a => if (p(a)) Some(a) else None)(identity)
}