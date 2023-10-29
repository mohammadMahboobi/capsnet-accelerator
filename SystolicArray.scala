import chisel3._

class SystolicArray(
    binaryPoint: Int,
    kernelLength: Int,
    inputLength: Int,
    partialSumLength: Int
) extends Module {
  val io = IO(new Bundle {
    val in1 = Input(SInt(inputLength.W))
    val in2 = Input(SInt(inputLength.W))
    val in3 = Input(SInt(inputLength.W))
    val in4 = Input(SInt(inputLength.W))
    val in5 = Input(SInt(inputLength.W))
    val in6 = Input(SInt(inputLength.W))
    val in7 = Input(SInt(inputLength.W))
    val in8 = Input(SInt(inputLength.W))
    val in9 = Input(SInt(inputLength.W))
    val regControl = Input(Bool())
    val addControl = Input(Bool())
    val kernel1 = Input(SInt(kernelLength.W))
    val kernel2 = Input(SInt(kernelLength.W))
    val kernel3 = Input(SInt(kernelLength.W))
    val kernel4 = Input(SInt(kernelLength.W))
    val kernel5 = Input(SInt(kernelLength.W))
    val kernel6 = Input(SInt(kernelLength.W))
    val kernel7 = Input(SInt(kernelLength.W))
    val kernel8 = Input(SInt(kernelLength.W))
    val kernel9 = Input(SInt(kernelLength.W))
    val outIn1 = Input(SInt(inputLength.W))
    val outIn2 = Input(SInt(inputLength.W))
    val outIn3 = Input(SInt(inputLength.W))
    val outIn4 = Input(SInt(inputLength.W))
    val outIn5 = Input(SInt(inputLength.W))
    val outIn6 = Input(SInt(inputLength.W))
    val outIn7 = Input(SInt(inputLength.W))
    val outIn8 = Input(SInt(inputLength.W))
    val outIn9 = Input(SInt(inputLength.W))
    val out = Output(SInt(32.W))
  })

  val PEs1 = Seq.tabulate(9) { c =>
    Module(
      new PE(
        binaryPoint = binaryPoint,
        inputLength = inputLength,
        kernelLength = kernelLength,
        partialSumLength = partialSumLength
      )
    ).io
  }
  val PEs2 = Seq.tabulate(9) { c =>
    Module(
      new PE(
        binaryPoint = binaryPoint,
        inputLength = inputLength,
        kernelLength = kernelLength,
        partialSumLength = partialSumLength
      )
    ).io
  }
  val PEs3 = Seq.tabulate(9) { c =>
    Module(
      new PE(
        binaryPoint = binaryPoint,
        inputLength = inputLength,
        kernelLength = kernelLength,
        partialSumLength = partialSumLength
      )
    ).io
  }
  val PEs4 = Seq.tabulate(9) { c =>
    Module(
      new PE(
        binaryPoint = binaryPoint,
        inputLength = inputLength,
        kernelLength = kernelLength,
        partialSumLength = partialSumLength
      )
    ).io
  }
  val PEs5 = Seq.tabulate(9) { c =>
    Module(
      new PE(
        binaryPoint = binaryPoint,
        inputLength = inputLength,
        kernelLength = kernelLength,
        partialSumLength = partialSumLength
      )
    ).io
  }
  val PEs6 = Seq.tabulate(9) { c =>
    Module(
      new PE(
        binaryPoint = binaryPoint,
        inputLength = inputLength,
        kernelLength = kernelLength,
        partialSumLength = partialSumLength
      )
    ).io
  }
  val PEs7 = Seq.tabulate(9) { c =>
    Module(
      new PE(
        binaryPoint = binaryPoint,
        inputLength = inputLength,
        kernelLength = kernelLength,
        partialSumLength = partialSumLength
      )
    ).io
  }
  val PEs8 = Seq.tabulate(9) { c =>
    Module(
      new PE(
        binaryPoint = binaryPoint,
        inputLength = inputLength,
        kernelLength = kernelLength,
        partialSumLength = partialSumLength
      )
    ).io
  }
  val PEs9 = Seq.tabulate(9) { c =>
    Module(
      new PE(
        binaryPoint = binaryPoint,
        inputLength = inputLength,
        kernelLength = kernelLength,
        partialSumLength = partialSumLength
      )
    ).io
  }

  PEs1(0).in := io.in1
  PEs1(0).kernel := io.kernel1
  PEs1(0).partialSum := 0.S
  PEs1(0).regControl := io.regControl
  PEs1(0).addControl := io.addControl
  PEs1(1).in := PEs1(0).outIn
  PEs1(1).kernel := io.kernel2
  PEs1(1).partialSum := 0.S
  PEs1(1).regControl := io.regControl
  PEs1(1).addControl := io.addControl
  PEs1(2).in := PEs1(1).outIn
  PEs1(2).kernel := io.kernel3
  PEs1(2).partialSum := 0.S
  PEs1(2).regControl := io.regControl
  PEs1(2).addControl := io.addControl
  PEs1(3).in := PEs1(2).outIn
  PEs1(3).kernel := io.kernel4
  PEs1(3).partialSum := 0.S
  PEs1(3).regControl := io.regControl
  PEs1(3).addControl := io.addControl
  PEs1(4).in := PEs1(3).outIn
  PEs1(4).kernel := io.kernel5
  PEs1(4).partialSum := 0.S
  PEs1(4).regControl := io.regControl
  PEs1(4).addControl := io.addControl
  PEs1(5).in := PEs1(4).outIn
  PEs1(5).kernel := io.kernel6
  PEs1(5).partialSum := 0.S
  PEs1(5).regControl := io.regControl
  PEs1(5).addControl := io.addControl
  PEs1(6).in := PEs1(5).outIn
  PEs1(6).kernel := io.kernel7
  PEs1(6).partialSum := 0.S
  PEs1(6).regControl := io.regControl
  PEs1(6).addControl := io.addControl
  PEs1(7).in := PEs1(6).outIn
  PEs1(7).kernel := io.kernel8
  PEs1(7).partialSum := 0.S
  PEs1(7).regControl := io.regControl
  PEs1(7).addControl := io.addControl
  PEs1(8).in := PEs1(7).outIn
  PEs1(8).kernel := io.kernel9
  PEs1(8).partialSum := 0.S
  PEs1(8).regControl := io.regControl
  PEs1(8).addControl := io.addControl

  for (i <- 0 until 9) {
    if (i == 0) {
      PEs2(i).in := io.in2
    } else {
      PEs2(i).in := PEs2(i - 1).outIn
    }
    PEs2(i).kernel := PEs1(i).outKernel
    PEs2(i).partialSum := PEs1(i).outPartialSum
    PEs2(i).regControl := io.regControl
    PEs2(i).addControl := io.addControl
  }
  for (i <- 0 until 9) {
    if (i == 0) {
      PEs3(i).in := io.in3
    } else {
      PEs3(i).in := PEs3(i - 1).outIn
    }
    PEs3(i).kernel := PEs2(i).outKernel
    PEs3(i).partialSum := PEs2(i).outPartialSum
    PEs3(i).regControl := io.regControl
    PEs3(i).addControl := io.addControl
  }
  for (i <- 0 until 9) {
    if (i == 0) {
      PEs4(i).in := io.in4
    } else {
      PEs4(i).in := PEs4(i - 1).outIn
    }
    PEs4(i).kernel := PEs3(i).outKernel
    PEs4(i).partialSum := PEs3(i).outPartialSum
    PEs4(i).regControl := io.regControl
    PEs4(i).addControl := io.addControl
  }
  for (i <- 0 until 9) {
    if (i == 0) {
      PEs5(i).in := io.in5
    } else {
      PEs5(i).in := PEs5(i - 1).outIn
    }
    PEs5(i).kernel := PEs4(i).outKernel
    PEs5(i).partialSum := PEs4(i).outPartialSum
    PEs5(i).regControl := io.regControl
    PEs5(i).addControl := io.addControl
  }
  for (i <- 0 until 9) {
    if (i == 0) {
      PEs6(i).in := io.in6
    } else {
      PEs6(i).in := PEs6(i - 1).outIn
    }
    PEs6(i).kernel := PEs5(i).outKernel
    PEs6(i).partialSum := PEs5(i).outPartialSum
    PEs6(i).regControl := io.regControl
    PEs6(i).addControl := io.addControl
  }
  for (i <- 0 until 9) {
    if (i == 0) {
      PEs7(i).in := io.in7
    } else {
      PEs7(i).in := PEs7(i - 1).outIn
    }
    PEs7(i).kernel := PEs6(i).outKernel
    PEs7(i).partialSum := PEs6(i).outPartialSum
    PEs7(i).regControl := io.regControl
    PEs7(i).addControl := io.addControl
  }
  for (i <- 0 until 9) {
    if (i == 0) {
      PEs8(i).in := io.in8
    } else {
      PEs8(i).in := PEs8(i - 1).outIn
    }
    PEs8(i).kernel := PEs7(i).outKernel
    PEs8(i).partialSum := PEs7(i).outPartialSum
    PEs8(i).regControl := io.regControl
    PEs8(i).addControl := io.addControl
  }
  for (i <- 0 until 9) {
    if (i == 0) {
      PEs9(i).in := io.in9
    } else {
      PEs9(i).in := PEs9(i - 1).outIn
    }
    PEs9(i).kernel := PEs8(i).outKernel
    PEs9(i).partialSum := PEs8(i).outPartialSum
    PEs9(i).regControl := io.regControl
    PEs9(i).addControl := io.addControl
  }

  io.out := PEs9(0).outPartialSum + PEs9(1).outPartialSum +
    PEs9(2).outPartialSum + PEs9(3).outPartialSum +
    PEs9(4).outPartialSum + PEs9(5).outPartialSum +
    PEs9(6).outPartialSum + PEs9(7).outPartialSum + PEs9(8).outPartialSum

  io.outIn1 := PEs1(8).outIn
  io.outIn2 := PEs2(8).outIn
  io.outIn3 := PEs3(8).outIn
  io.outIn4 := PEs4(8).outIn
  io.outIn5 := PEs5(8).outIn
  io.outIn6 := PEs6(8).outIn
  io.outIn7 := PEs7(8).outIn
  io.outIn8 := PEs8(8).outIn
  io.outIn9 := PEs9(8).outIn
}
