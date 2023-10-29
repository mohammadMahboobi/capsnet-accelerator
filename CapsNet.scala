import chisel3._
import chisel3.util.experimental.loadMemoryFromFileInline
import firrtl.annotations.MemoryLoadFileType

class CapsNet extends Module {
  val io = IO(new Bundle {
    val conv1Ready = Output(Bool())
    val primaryCapsReady = Output(Bool())
    val predictionVectorsReady = Output(Bool())
    val softmaxReady = Output(Bool())
    val weightedSumReady = Output(Bool())
    val squashReady = Output(Bool())
    val agreementReady = Output(Bool())
    val updateBiasesReady = Output(Bool())
    val ready = Output(Bool())
    val output = Output(Vec(10, SInt(32.W)))
  })

  val dataMemory =
    Mem(256 * 20 * 20, SInt(24.W))
  val weightMemory =
    Mem((256 * 9 * 9) + (256 * 256 * 9 * 9) + (10 * 8 * 16), SInt(24.W))
  val biasMemory = Mem(256 + 256, SInt(24.W))
  val dynamicRoutingBiasesMemory =
    Mem(
      1152,
      SInt(32.W)
    )

  loadMemoryFromFileInline(
    dataMemory,
    "src/resources/dataBinary.txt",
    MemoryLoadFileType.Binary
  )
  loadMemoryFromFileInline(
    weightMemory,
    "src/resources/weightsBinary.txt",
    MemoryLoadFileType.Binary
  )
  loadMemoryFromFileInline(
    biasMemory,
    "src/resources/biasesBinary.txt",
    MemoryLoadFileType.Binary
  )

  val weightCache = Reg(Vec(256 * 9 * 9, SInt(24.W)))

  val controller = Module(new Controller)
  val softmax = Module(new Softmax(binaryPoint = 8, inputLength = 32))
  val systolicArray = Module(
    new SystolicArray(
      binaryPoint = 8,
      kernelLength = 24,
      inputLength = 24,
      partialSumLength = 32
    )
  )
  val accumulator = Module(new Accumulator(inputLength = 32, biasLength = 24))
  val relu = Module(
    new ReLU2D(inputWidth = 1, inputHeight = 1, inputLength = 32)
  )
  val squash = Module(
    new Squash(binaryPoint = 8, inputLength = 32, inputDimension = 8)
  )

  io.conv1Ready := controller.io.conv1Ready
  io.primaryCapsReady := controller.io.primaryCapsReady
  io.predictionVectorsReady := controller.io.predictionVectorsReady
  io.softmaxReady := controller.io.softmaxReady
  io.weightedSumReady := controller.io.weightedSumReady
  io.squashReady := controller.io.squashReady
  io.agreementReady := controller.io.agreementReady
  io.updateBiasesReady := controller.io.updateBiasesReady
  io.ready := controller.io.ready

  softmax.io.input := dynamicRoutingBiasesMemory(
    controller.io.dynamicRoutingMemAddress
  )
  softmax.io.expoControl := controller.io.softmaxExpoControl
  softmax.io.regControl := controller.io.softmaxRegControl
  softmax.io.adderControl := controller.io.softmaxAdderControl
  softmax.io.dividerControl := controller.io.softmaxDividerControl
  softmax.io.isFirstRoundControl := controller.io.softmaxIsFirstRoundControl

  systolicArray.io.in1 := Mux(
    controller.io.isDataMemOrReuse,
    dataMemory(controller.io.sysArrIn1),
    systolicArray.io.outIn1
  )
  systolicArray.io.in2 := Mux(
    controller.io.isDataMemOrReuse,
    dataMemory(controller.io.sysArrIn2),
    systolicArray.io.outIn2
  )
  systolicArray.io.in3 := Mux(
    controller.io.isDataMemOrReuse,
    dataMemory(controller.io.sysArrIn3),
    systolicArray.io.outIn3
  )
  systolicArray.io.in4 := Mux(
    controller.io.isDataMemOrReuse,
    dataMemory(controller.io.sysArrIn4),
    systolicArray.io.outIn4
  )
  systolicArray.io.in5 := Mux(
    controller.io.isDataMemOrReuse,
    dataMemory(controller.io.sysArrIn5),
    systolicArray.io.outIn5
  )
  systolicArray.io.in6 := Mux(
    controller.io.isDataMemOrReuse,
    dataMemory(controller.io.sysArrIn6),
    systolicArray.io.outIn6
  )
  systolicArray.io.in7 := Mux(
    controller.io.isDataMemOrReuse,
    dataMemory(controller.io.sysArrIn7),
    systolicArray.io.outIn7
  )
  systolicArray.io.in8 := Mux(
    controller.io.isDataMemOrReuse,
    dataMemory(controller.io.sysArrIn8),
    systolicArray.io.outIn8
  )
  systolicArray.io.in9 := Mux(
    controller.io.isDataMemOrReuse,
    dataMemory(controller.io.sysArrIn9),
    systolicArray.io.outIn9
  )
  systolicArray.io.regControl := controller.io.sysArrRegControl
  systolicArray.io.addControl := controller.io.sysArrAddControl
  systolicArray.io.kernel1 := Mux(
    controller.io.sysArrIsWeightCache,
    weightCache(controller.io.sysArrWeightCache1),
    Mux(controller.io.isSoftmaxOrSquash, softmax.io.output, squash.io.output)
  )
  systolicArray.io.kernel2 := Mux(
    controller.io.sysArrIsWeightCache,
    weightCache(controller.io.sysArrWeightCache2),
    Mux(controller.io.isSoftmaxOrSquash, softmax.io.output, squash.io.output)
  )
  systolicArray.io.kernel3 := Mux(
    controller.io.sysArrIsWeightCache,
    weightCache(controller.io.sysArrWeightCache3),
    Mux(controller.io.isSoftmaxOrSquash, softmax.io.output, squash.io.output)
  )
  systolicArray.io.kernel4 := Mux(
    controller.io.sysArrIsWeightCache,
    weightCache(controller.io.sysArrWeightCache4),
    Mux(controller.io.isSoftmaxOrSquash, softmax.io.output, squash.io.output)
  )
  systolicArray.io.kernel5 := Mux(
    controller.io.sysArrIsWeightCache,
    weightCache(controller.io.sysArrWeightCache5),
    Mux(controller.io.isSoftmaxOrSquash, softmax.io.output, squash.io.output)
  )
  systolicArray.io.kernel6 := Mux(
    controller.io.sysArrIsWeightCache,
    weightCache(controller.io.sysArrWeightCache6),
    Mux(controller.io.isSoftmaxOrSquash, softmax.io.output, squash.io.output)
  )
  systolicArray.io.kernel7 := Mux(
    controller.io.sysArrIsWeightCache,
    weightCache(controller.io.sysArrWeightCache7),
    Mux(controller.io.isSoftmaxOrSquash, softmax.io.output, squash.io.output)
  )
  systolicArray.io.kernel8 := Mux(
    controller.io.sysArrIsWeightCache,
    weightCache(controller.io.sysArrWeightCache8),
    Mux(controller.io.isSoftmaxOrSquash, softmax.io.output, squash.io.output)
  )
  systolicArray.io.kernel9 := Mux(
    controller.io.sysArrIsWeightCache,
    weightCache(controller.io.sysArrWeightCache9),
    Mux(controller.io.isSoftmaxOrSquash, softmax.io.output, squash.io.output)
  )

  accumulator.io.input := systolicArray.io.out
  accumulator.io.bias := biasMemory(controller.io.accumulatorBiasAddress)
  accumulator.io.regControl := controller.io.accumulatorRegControl
  accumulator.io.adderControl := controller.io.accumulatorAdderControl
  accumulator.io.isBiasControl := controller.io.accumulatorIsBiasControl

  relu.io.input := accumulator.io.out
  squash.io.input := accumulator.io.out

  when(controller.io.storeInWeightCache) {
    weightCache := weightMemory(controller.io.weightMemAddress)
  }

  when(controller.io.saveReluInDataMem) {
    dataMemory(controller.io.dataMemAddress) := relu.io.output
  }

  when(controller.io.saveSquashInDataMem) {
    dataMemory(controller.io.dataMemAddress) := squash.io.output
  }

  when(controller.io.saveSysArrInDataMem) {
    dataMemory(controller.io.dataMemAddress) := systolicArray.io.out
  }
}
