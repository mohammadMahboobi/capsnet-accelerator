import chisel3._
import chisel3.util.log2Ceil

class SquaredNorm(
    binaryPoint: Int,
    inputLength: Int,
    inputDimension: Int
) extends Module {
  val io = IO(new Bundle {
    val input = Input(Vec(inputDimension, SInt(inputLength.W)))
    val output = Output(
      UInt((10 + log2Ceil(Math.pow(10, binaryPoint).toInt)).W)
    )
  })

  val tmp = Wire(Vec(inputDimension, UInt((inputLength * 2).W)))

  for (i <- 0 until inputDimension) {
    tmp(i) := (io.input(i) * io
      .input(i) / (Math.pow(10, binaryPoint).toInt).S).asUInt
  }

  io.output := tmp.reduce(_ + _)
}
