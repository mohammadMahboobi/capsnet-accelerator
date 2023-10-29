import chisel3._
import chisel3.util.log2Ceil

class Squash(
    binaryPoint: Int,
    inputLength: Int,
    inputDimension: Int
) extends Module {
  val io = IO(new Bundle {
    val input = Input(Vec(inputDimension, SInt(inputLength.W)))
    val startControl = Input(Bool())
    val output =
      Output(
        Vec(
          inputDimension,
          SInt((1 + log2Ceil(Math.pow(10, binaryPoint).toInt)).W)
        )
      )
  })

  val squaredNorm = Module(
    new SquaredNorm(
      binaryPoint = binaryPoint,
      inputLength = inputLength,
      inputDimension = inputDimension
    )
  )

  squaredNorm.io.input := io.input
  val squaredNormOut = squaredNorm.io.output

  val sqrt = Module(new Sqrt(binaryPoint))
  sqrt.io.input := squaredNormOut

  when(io.startControl) {
    val lhs = (squaredNormOut *
      (Math.pow(10, binaryPoint).toInt).U) /
      ((Math.pow(10, binaryPoint).toInt).U + squaredNormOut)

    val norm = sqrt.io.output / (Math.pow(10, (binaryPoint / 2)).toInt).U

    val rhs = Wire(Vec(inputDimension, SInt(inputLength.W)))

    for (i <- 0 until inputDimension) {
      rhs(i) := (io
        .input(i) * (Math.pow(10, binaryPoint).toInt).S) / (norm.asSInt)
    }

    for (i <- 0 until inputDimension) {
      io.output(i) := (lhs * rhs(i)) / ((Math.pow(10, binaryPoint).toInt).S)
    }
  }
}
