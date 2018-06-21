package com.sizmek.rtree2d.benchmark

import archery._
import org.openjdk.jmh.annotations._

class Archery extends BenchmarkBase {
  private[benchmark] var rtree: RTree[PointOfInterest] = _
  private[benchmark] var rtreeEntries: Array[Entry[PointOfInterest]] = _
  private[benchmark] var entriesToAdd: Array[Entry[PointOfInterest]] = _
  private[benchmark] var entriesToRemove: Array[Entry[PointOfInterest]] = _
  private[this] var xys: Array[Float] = _
  private[this] var curr: Int = _
  private[this] var eps: Float = _
  private[this] var rectEps: Float = _

  @Setup
  def setup(): Unit = {
    val points = genPoints
    if (shuffle) doShuffle(points)
    val sizeX = Math.sqrt(size).toFloat
    eps = overlap / (sizeX * 2)
    rectEps = rectSize / (sizeX * 2)
    rtreeEntries = new Array[Entry[PointOfInterest]](points.length)
    var i = 0
    while (i < points.length) {
      val p = points(i)
      rtreeEntries(i) = Entry(Box(p.x - eps, p.y - eps, p.x + eps, p.y + eps), p)
      i += 1
    }
    rtree = RTree(rtreeEntries:_*)
    doShuffle(points)
    xys = genRequests(points)
    curr = 0
    if (!shuffle) rtreeEntries = rtree.entries.toArray
    entriesToAdd = java.util.Arrays.copyOf(rtreeEntries, (size * partToAddOrRemove).toInt)
    entriesToRemove = rtreeEntries.slice(size - (size * partToAddOrRemove).toInt, size)
  }

  @Benchmark
  def apply: RTree[PointOfInterest] = RTree(rtreeEntries:_*)

  @Benchmark
  def entries: Seq[Entry[PointOfInterest]] = {
    val res = rtree.entries.toArray
    res
  }

  @Benchmark
  def nearest: Option[Entry[PointOfInterest]] = {
    val i = curr
    curr = if (i + 2 < xys.length) i + 2 else 0
    rtree.nearest(Point(xys(i), xys(i + 1)))
  }

  @Benchmark
  def nearestK: Seq[Entry[PointOfInterest]] = {
    val i = curr
    curr = if (i + 2 < xys.length) i + 2 else 0
    rtree.nearestK(Point(xys(i), xys(i + 1)), nearestMax)
  }

  @Benchmark
  def searchByPoint: Seq[Entry[PointOfInterest]] = {
    val i = curr
    curr = if (i + 2 < xys.length) i + 2 else 0
    rtree.searchIntersection(Point(xys(i), xys(i + 1)))
  }

  @Benchmark
  def searchByRectangle: Seq[Entry[PointOfInterest]] = {
    val i = curr
    curr = if (i + 2 < xys.length) i + 2 else 0
    val x = xys(i)
    val y = xys(i + 1)
    val e = rectEps
    rtree.searchIntersection(Box(x - e, y - e, x + e, y + e))
  }

  @Benchmark
  def update: RTree[PointOfInterest] = RTree(rtree.entries.toArray.diff(entriesToRemove) ++ entriesToAdd:_*)
}
