package com.sizmek.rtree2d.benchmark

import org.scalatest.{Matchers, WordSpec}

class GeoRTree2DTest extends WordSpec with Matchers {
  private def benchmark = new GeoRTree2D {
    setup()
  }
  "GeoRTree2D" should {
    "return proper values" in {
      benchmark.apply.entries shouldBe benchmark.rtree.entries
      benchmark.entries should contain allElementsOf benchmark.rtreeEntries
      benchmark.update.entries should contain allElementsOf benchmark.rtreeEntries.diff(benchmark.entriesToRemove) ++ benchmark.entriesToAdd
      benchmark.nearest should contain oneElementOf benchmark.rtreeEntries
      benchmark.nearestK.size shouldBe benchmark.nearestMax
      (1 to benchmark.size * 2).foreach(_ => benchmark.searchByPoint.size shouldBe 1)
      (1 to benchmark.size * 2).foreach(_ => benchmark.searchByRectangle.size should be >= 1)
    }
  }
}
