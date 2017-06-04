package io.github.robhinds.genotype

import scala.util.Random

trait Mutant[A] {
  def mutate(a: A): A
}

object Mutant {
  implicit def doubleGaussianMutator = new Mutant[Double] {
    override def mutate(a: Double): Double = Math.abs(1 - Random.nextGaussian()) * a
  }

  implicit def booleanFlipMutator = new Mutant[Boolean] {
    override def mutate(a: Boolean): Boolean = Random.nextBoolean
  }

  implicit def intBitFlipMutator = new Mutant[Int] {
    override def mutate(a: Int): Int = (Math.abs(1 - Random.nextGaussian()) * a).toInt
  }
}
