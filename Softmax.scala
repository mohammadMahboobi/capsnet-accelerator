import chisel3._

class Softmax(
    binaryPoint: Int,
    inputLength: Int
) extends Module {
  val io = IO(new Bundle {
    val input = Input(SInt(inputLength.W))
    val expoControl = Input(Bool())
    val regControl = Input(Bool())
    val adderControl = Input(Bool())
    val dividerControl = Input(Bool())
    val isFirstRoundControl = Input(Bool())
    val output = Output(SInt(32.W))
  })

  val sumReg = RegInit(0.S(32.W))

  val expo = Module(
    new Exponential(binaryPoint = binaryPoint, inputLength = inputLength)
  )

  when(io.expoControl) {
    expo.io.input := io.input
  }.otherwise {
    expo.io.input := DontCare
  }

  when(io.regControl && io.adderControl) {
    sumReg := sumReg + expo.io.output
  }

  when(io.dividerControl) {
    io.output := expo.io.output * Math
      .pow(10, binaryPoint)
      .toInt
      .S / Mux(io.isFirstRoundControl, 1152.S, sumReg)
  }.otherwise {
    io.output := DontCare
  }
}
