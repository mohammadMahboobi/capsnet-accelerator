import chisel3._

class PE(
    binaryPoint: Int,
    inputLength: Int,
    kernelLength: Int,
    partialSumLength: Int
) extends Module {
  val io = IO(new Bundle {
    val in = Input(SInt(inputLength.W))
    val kernel = Input(SInt(kernelLength.W))
    val partialSum = Input(SInt(partialSumLength.W))
    val regControl = Input(Bool())
    val addControl = Input(Bool())
    val outIn = Output(SInt(inputLength.W))
    val outKernel = Output(SInt(kernelLength.W))
    val outPartialSum = Output(SInt(partialSumLength.W))
  })

  val register = RegInit(0.S(kernelLength.W))

  io.outIn := io.in
  io.outKernel := io.kernel

  when(io.regControl) {
    register := io.kernel
  }

  when(io.addControl) {
    io.outPartialSum := io.partialSum + (io.in * register /
      (Math.pow(10, binaryPoint).toInt).S)
  }.otherwise {
    io.outPartialSum := DontCare
  }
}
