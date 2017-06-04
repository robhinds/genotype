package io.github.robhinds.genotype

object Genes {

  sealed trait Gene[A] { val value: Option[A] }
  case class IntGene(min: Int = Int.MinValue/2, max: Int = Int.MaxValue/2, value: Option[Int] = None) extends Gene[Int]
  case class DoubleGene(min: Double = Double.MinValue/2, max: Double = Double.MaxValue/2, value: Option[Double] = None) extends Gene[Double]
  case class BooleanGene(value: Option[Boolean] = None) extends Gene[Boolean]

}
