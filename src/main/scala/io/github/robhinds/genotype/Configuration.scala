package io.github.robhinds.genotype

object Configuration {
  case class PopulationConfig(size: Int)
  case class EvolutionConfig(generations: Int)
  case class LearningRate(alpha: Double)
  object LearningRate {
    implicit val defaultLearningRate = LearningRate(0.3)
  }
}
