import { Point } from "@/types";

// Helper function to calculate a point on a Bezier curve using De Casteljau's algorithm
export function getBezierPoint(points: Point[], t: number): Point {
  if (points.length === 1) {
    return points[0];
  }

  const newPoints: Point[] = [];
  for (let i = 0; i < points.length - 1; i++) {
    newPoints.push({
      x: (1 - t) * points[i].x + t * points[i + 1].x,
      y: (1 - t) * points[i].y + t * points[i + 1].y,
    });
  }

  return getBezierPoint(newPoints, t);
}
