import chisel3._

class Exponential(
    binaryPoint: Int,
    inputLength: Int
) extends Module {
  val io = IO(new Bundle {
    val input = Input(SInt(inputLength.W))
    val output = Output(SInt((5 * inputLength + 1).W))
  })

  val inputSquare = (io.input * io.input) / (Math.pow(10, binaryPoint).toInt).S
  val inputPowFour =
    (inputSquare * inputSquare) / (Math.pow(10, binaryPoint).toInt).S
  val inputPowSix =
    (inputPowFour * inputSquare) / (Math.pow(10, binaryPoint).toInt).S
  val inputPowEight =
    (inputPowFour * inputPowFour) / (Math.pow(10, binaryPoint).toInt).S
  val inputPowTen =
    (inputPowEight * inputSquare) / (Math.pow(10, binaryPoint).toInt).S
  val inputPowTwelve =
    (inputPowTen * inputSquare) / (Math.pow(10, binaryPoint).toInt).S
  val inputPowFourteen =
    (inputPowTwelve * inputSquare) / (Math.pow(10, binaryPoint).toInt).S
  val inputPowSixteen =
    (inputPowFourteen * inputSquare) / (Math.pow(10, binaryPoint).toInt).S
  // val inputPowEighteen =
  //   (inputPowSixteen * inputSquare) / (Math.pow(10, binaryPoint).toInt).S
  // val inputPowTwenty =
  //   (inputPowEighteen * inputSquare) / (Math.pow(10, binaryPoint).toInt).S

  io.output := (Math.pow(10, binaryPoint).toInt).S +
    (io.input) +
    (inputSquare / 2.S) +
    (((inputSquare * io.input) /
      (Math.pow(10, binaryPoint).toInt).S) / 6.S) +
    (inputPowFour / 24.S) +
    (((inputPowFour * io.input) /
      (Math.pow(10, binaryPoint).toInt).S) / 120.S) +
    ((inputPowSix / 720.S)) +
    (((inputPowSix * io.input) /
      (Math.pow(10, binaryPoint).toInt).S) / 5040.S) +
    (inputPowEight / 40320.S) +
    (((inputPowEight * io.input) /
      (Math.pow(10, binaryPoint).toInt).S) / 362880.S) +
    (inputPowTen / 3638800.S) +
    (((inputPowTen * io.input) /
      (Math.pow(10, binaryPoint).toInt).S) / 40026800.S) +
    (inputPowTwelve / 480321600.S) +
    ((((inputPowTwelve * io.input) /
      (Math.pow(10, binaryPoint).toInt).S) / 480321600.S) / 13.S) +
    ((inputPowFourteen / 480321600.S) / 182.S) +
    ((((inputPowFourteen * io.input) /
      (Math.pow(10, binaryPoint).toInt).S) / 480321600.S) / 2730.S) +
    ((inputPowSixteen / 480321600.S) / 43680.S)
}
