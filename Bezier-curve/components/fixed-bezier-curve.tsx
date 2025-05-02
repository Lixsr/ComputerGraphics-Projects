import { Point } from "@/types";
import { useEffect, useRef, useState } from "react";
import { Card, CardContent } from "./ui/card";
import { Trash2 } from "lucide-react";
import { Button } from "./ui/button";
import { getBezierPoint } from "@/lib/bezier";
import { Tabs, TabsList, TabsTrigger } from "@/components/ui/tabs";

export function FixedPointsCurveEditor() {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const [points, setPoints] = useState<Point[]>([]);
  const [draggingIndex, setDraggingIndex] = useState<number | null>(null);
  const [canvasSize, setCanvasSize] = useState({ width: 800, height: 400 });
  const [maxPoints, setMaxPoints] = useState(3);

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

  // Reset points when max points changes
  useEffect(() => {
    setPoints([]);
  }, [maxPoints]);

  // Draw the canvas
  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext("2d");
    if (!ctx) return;

    // Clear canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Draw control polygon
    if (points.length > 1) {
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

    // Draw Bezier curve
    if (points.length > 1) {
      ctx.beginPath();
      ctx.moveTo(points[0].x, points[0].y);

      if (points.length === 2) {
        // Linear
        ctx.lineTo(points[1].x, points[1].y);
      } else if (points.length === 3) {
        // Quadratic
        ctx.quadraticCurveTo(
          points[1].x,
          points[1].y,
          points[2].x,
          points[2].y
        );
      } else if (points.length === 4) {
        // Cubic
        ctx.bezierCurveTo(
          points[1].x,
          points[1].y,
          points[2].x,
          points[2].y,
          points[3].x,
          points[3].y
        );
      } else if (points.length > 4) {
        const steps = 100;
        for (let i = 0; i <= steps; i++) {
          const t = i / steps;
          const point = getBezierPoint(points, t);
          ctx.lineTo(point.x, point.y);
        }
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
      ctx.fillStyle =
        index === 0 || index === points.length - 1 ? "#10b981" : "#f97316";
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
    if (draggingIndex !== null || points.length >= maxPoints) return;

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
              ? `Select number of control points and click to add (${points.length}/${maxPoints})`
              : `${points.length}/${maxPoints} points`}
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

        <div className="mb-4">
          <Tabs>
            <TabsList className="grid grid-cols-4">
              {[3, 4, 5, 6].map((num) => (
                <TabsTrigger
                  key={num}
                  value={num.toString()}
                  onClick={() => setMaxPoints(num)}
                  className={
                    maxPoints === num
                      ? "bg-primary text-primary-foreground"
                      : ""
                  }
                >
                  {num} Points
                </TabsTrigger>
              ))}
            </TabsList>
          </Tabs>
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
            <li>Select the number of control points using the tabs above</li>
            <li>Click on the canvas to add points</li>
            <li>Drag points to modify the curve</li>
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
