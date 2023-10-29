import chisel3._

class Accumulator(
    inputLength: Int,
    biasLength: Int
) extends Module {
  val io = IO(new Bundle {
    val input = Input(SInt(inputLength.W))
    val bias = Input(SInt(biasLength.W))
    val regControl = Input(Bool())
    val adderControl = Input(Bool())
    val isBiasControl = Input(Bool())
    val out = Output(SInt(32.W))
  })

  val register = RegInit(0.S(32.W))
  val muxOut = Mux(io.isBiasControl, io.bias, io.input)

  when(io.regControl) {
    register := register + muxOut
  }

  when(io.adderControl) {
    io.out := register + muxOut
  }.otherwise {
    io.out := DontCare
  }
}
