import { Point } from "@/types";
import { useEffect, useRef, useState } from "react";
import { Card, CardContent } from "./ui/card";
import { Trash2 } from "lucide-react";
import { Button } from "./ui/button";

export function PolyBezierCurvesEditor() {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const [points, setPoints] = useState<Point[]>([]);
  const [draggingIndex, setDraggingIndex] = useState<number | null>(null);
  const [canvasSize, setCanvasSize] = useState({ width: 800, height: 400 });

  // Handle window resize
  useEffect(() => {
    const handleResize = () => {
      const container = canvasRef.current?.parentElement;
      if (container) {
        const width = container.clientWidth;
        const height = Math.min(400, window.innerHeight - 200);
        setCanvasSize({ width, height });
      }
    };

    handleResize();
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  // Handle keyboard events
  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key.toLowerCase() === "r") {
        setPoints((prev) => prev.slice(0, -1));
      }
    };

    document.addEventListener("keydown", handleKeyDown);
    return () => document.removeEventListener("keydown", handleKeyDown);
  }, []);

  // Draw the canvas
  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext("2d");
    if (!ctx) return;

    // Clear canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Draw control polygon
    if (points.length > 0) {
      ctx.beginPath();
      ctx.moveTo(points[0].x, points[0].y);
      for (let i = 1; i < points.length; i++) {
        ctx.lineTo(points[i].x, points[i].y);
      }
      ctx.strokeStyle = "#94a3b8";
      ctx.setLineDash([5, 5]);
      ctx.stroke();
      ctx.setLineDash([]);
    }

    // Draw Bezier curves (every 3 points)
    if (points.length >= 4) {
      ctx.beginPath();
      ctx.moveTo(points[0].x, points[0].y);

      // Draw a curve for every 3 points (with shared endpoints)
      for (let i = 0; i < points.length - 3; i += 3) {
        // Each curve uses 4 points: start point, 2 control points, end point
        // The start point is either the first point or the end point of the previous curve
        const startPoint = i === 0 ? points[0] : points[i];
        const controlPoint1 = points[i + 1];
        const controlPoint2 = points[i + 2];
        const endPoint = points[i + 3];

        ctx.bezierCurveTo(
          controlPoint1.x,
          controlPoint1.y,
          controlPoint2.x,
          controlPoint2.y,
          endPoint.x,
          endPoint.y
        );
      }

      ctx.strokeStyle = "#3b82f6";
      ctx.lineWidth = 3;
      ctx.stroke();
      ctx.lineWidth = 1;
    }

    // Draw control points
    points.forEach((point, index) => {
      ctx.beginPath();
      ctx.arc(point.x, point.y, 8, 0, Math.PI * 2);

      // Color coding:
      // - Green for start/end points (every 3rd point starting from 0)
      // - Orange for control points
      const isEndpoint = index % 3 === 0;
      ctx.fillStyle = isEndpoint ? "#10b981" : "#f97316";
      ctx.fill();

      ctx.beginPath();
      ctx.arc(point.x, point.y, 8, 0, Math.PI * 2);
      ctx.strokeStyle = "#ffffff";
      ctx.stroke();

      // Add point number
      ctx.fillStyle = "#ffffff";
      ctx.font = "10px Arial";
      ctx.textAlign = "center";
      ctx.textBaseline = "middle";
      ctx.fillText(index.toString(), point.x, point.y);
    });
  }, [points, canvasSize]);

  // Handle mouse events
  const handleCanvasClick = (e: React.MouseEvent<HTMLCanvasElement>) => {
    if (draggingIndex !== null) return;

    const canvas = canvasRef.current;
    if (!canvas) return;

    const rect = canvas.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    // Check if clicked near an existing point
    const clickedPointIndex = points.findIndex(
      (point) => Math.sqrt((point.x - x) ** 2 + (point.y - y) ** 2) < 10
    );

    if (clickedPointIndex === -1) {
      // Add new point if not clicking on existing point
      setPoints([...points, { x, y }]);
    }
  };

  const handleMouseDown = (e: React.MouseEvent<HTMLCanvasElement>) => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const rect = canvas.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    // Find if we're clicking on a point
    const pointIndex = points.findIndex(
      (point) => Math.sqrt((point.x - x) ** 2 + (point.y - y) ** 2) < 10
    );

    if (pointIndex !== -1) {
      setDraggingIndex(pointIndex);
    }
  };

  const handleMouseMove = (e: React.MouseEvent<HTMLCanvasElement>) => {
    if (draggingIndex === null) return;

    const canvas = canvasRef.current;
    if (!canvas) return;

    const rect = canvas.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    const newPoints = [...points];
    newPoints[draggingIndex] = { x, y };
    setPoints(newPoints);
  };

  const handleMouseUp = () => {
    setDraggingIndex(null);
  };

  const handleReset = () => {
    setPoints([]);
  };

  return (
    <Card className="w-full">
      <CardContent className="p-4">
        <div className="flex justify-between items-center mb-4">
          <div className="text-sm text-muted-foreground">
            {points.length === 0
              ? "Click to add points."
              : `${points.length} points (${Math.floor(
                  (points.length - 1) / 3
                )} curves)`}
          </div>
          <div className="flex gap-2">
            <Button
              variant="outline"
              size="sm"
              onClick={() => setPoints((prev) => prev.slice(0, -1))}
              disabled={points.length === 0}
            >
              Remove Last Point
            </Button>
            <Button
              variant="destructive"
              size="sm"
              onClick={handleReset}
              disabled={points.length === 0}
            >
              <Trash2 className="h-4 w-4 mr-1" /> Reset
            </Button>
          </div>
        </div>

        <div className="relative border rounded-md overflow-hidden">
          <canvas
            ref={canvasRef}
            width={canvasSize.width}
            height={canvasSize.height}
            onClick={handleCanvasClick}
            onMouseDown={handleMouseDown}
            onMouseMove={handleMouseMove}
            onMouseUp={handleMouseUp}
            onMouseLeave={handleMouseUp}
            className="bg-slate-50 dark:bg-slate-900 cursor-crosshair"
          />
        </div>

        <div className="mt-4 text-sm">
          <p className="font-medium">Instructions:</p>
          <ul className="list-disc pl-5 mt-1 text-muted-foreground">
            <li>Click on the canvas to add control points</li>
            <li>Drag points to modify the curves</li>
            <li>
              Press{" "}
              <kbd className="px-1 py-0.5 bg-gray-100 dark:bg-gray-800 rounded border">
                R
              </kbd>{" "}
              to remove the last point
            </li>
          </ul>
        </div>
      </CardContent>
    </Card>
  );
}
