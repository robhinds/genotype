package io.github.robhinds.genotype

object Configuration {
  case class PopulationConfig(size: Int)
  case class EvolutionConfig(alpha: Double, generations: Int)
}
