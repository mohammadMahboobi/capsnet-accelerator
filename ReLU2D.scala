import chisel3._

class ReLU2D(
    inputWidth: Int,
    inputHeight: Int,
    inputLength: Int
) extends Module {
  val io = IO(new Bundle {
    val input = Input(Vec(inputWidth, Vec(inputHeight, SInt(inputLength.W))))
    val output = Output(Vec(inputWidth, Vec(inputHeight, SInt(inputLength.W))))
  })

  for (i <- 0 until inputWidth) {
    for (j <- 0 until inputHeight) {
      io.output(i)(j) := Mux(io.input(i)(j) <= 0.S, 0.S, io.input(i)(j))
    }
  }
}
