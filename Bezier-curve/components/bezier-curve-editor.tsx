"use client";

import type React from "react";

import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";

import { PolyBezierCurvesEditor } from "./poly-bezier-curves-editor";
import { FixedPointsCurveEditor } from "./fixed-bezier-curve";

export default function BezierCurveEditor() {
  return (
    <Tabs defaultValue="continuous" className="w-full">
      <TabsList className="grid w-full grid-cols-2">
        <TabsTrigger value="continuous">Poly Bezier Curves</TabsTrigger>
        <TabsTrigger value="fixed">Fixed Control Points</TabsTrigger>
      </TabsList>

      <TabsContent value="continuous" className="mt-4">
        <PolyBezierCurvesEditor />
      </TabsContent>

      <TabsContent value="fixed" className="mt-4">
        <FixedPointsCurveEditor />
      </TabsContent>
    </Tabs>
  );
}
