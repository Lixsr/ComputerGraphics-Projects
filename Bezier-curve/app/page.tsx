import BezierCurveEditor from "@/components/bezier-curve-editor"

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-4 md:p-8">
      <div className="w-full max-w-5xl">
        <h1 className="text-3xl font-bold mb-4">Bezier Curve Editor</h1>
        <p className="mb-6 text-muted-foreground">
          Create and manipulate Bezier curves with different control point configurations.
        </p>
        <BezierCurveEditor />
      </div>
    </main>
  )
}
