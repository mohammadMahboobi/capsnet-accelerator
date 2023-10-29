import chisel3._
import chisel3.util.log2Ceil

class ControllerPrimaryCaps extends Module {
  val io = IO(new Bundle {
    val dynamicRoutingMemAddress = Output(UInt(11.W))
    val softmaxExpoControl = Output(Bool())
    val softmaxRegControl = Output(Bool())
    val softmaxAdderControl = Output(Bool())
    val softmaxDividerControl = Output(Bool())
    val softmaxIsFirstRoundControl = Output(Bool())
    val sysArrIn1 = Output(UInt(17.W))
    val sysArrIn2 = Output(UInt(17.W))
    val sysArrIn3 = Output(UInt(17.W))
    val sysArrIn4 = Output(UInt(17.W))
    val sysArrIn5 = Output(UInt(17.W))
    val sysArrIn6 = Output(UInt(17.W))
    val sysArrIn7 = Output(UInt(17.W))
    val sysArrIn8 = Output(UInt(17.W))
    val sysArrIn9 = Output(UInt(17.W))
    val isDataMemOrReuse = Output(Bool())
    val sysArrRegControl = Output(Bool())
    val sysArrAddControl = Output(Bool())
    val sysArrIsWeightCache = Output(Bool())
    val sysArrWeightCache1 = Output(UInt(15.W))
    val sysArrWeightCache2 = Output(UInt(15.W))
    val sysArrWeightCache3 = Output(UInt(15.W))
    val sysArrWeightCache4 = Output(UInt(15.W))
    val sysArrWeightCache5 = Output(UInt(15.W))
    val sysArrWeightCache6 = Output(UInt(15.W))
    val sysArrWeightCache7 = Output(UInt(15.W))
    val sysArrWeightCache8 = Output(UInt(15.W))
    val sysArrWeightCache9 = Output(UInt(15.W))
    val isSoftmaxOrSquash = Output(Bool())
    val accumulatorBiasAddress = Output(UInt(9.W))
    val accumulatorRegControl = Output(Bool())
    val accumulatorAdderControl = Output(Bool())
    val accumulatorIsBiasControl = Output(Bool())
    val storeInWeightCache = Output(Bool())
    val weightMemAddress = Output(UInt(23.W))
    val saveReluInDataMem = Output(Bool())
    val saveSquashInDataMem = Output(Bool())
    val saveSysArrInDataMem = Output(Bool())
    val dataMemAddress = Output(UInt(17.W))
    val conv1Ready = Output(Bool())
    val primaryCapsReady = Output(Bool())
    val predictionVectorsReady = Output(Bool())
    val softmaxReady = Output(Bool())
    val weightedSumReady = Output(Bool())
    val squashReady = Output(Bool())
    val agreementReady = Output(Bool())
    val updateBiasesReady = Output(Bool())
    val ready = Output(Bool())
  })

  val channel = RegInit(0.U(log2Ceil(256).W))
  val innerChannel = RegInit(0.U(log2Ceil(256).W))
  val row = RegInit(0.U(log2Ceil(6).W))
  val column = RegInit(0.U(log2Ceil(6).W))

  io.isDataMemOrReuse := false.B
  io.sysArrIsWeightCache := true.B
  io.sysArrRegControl := true.B
  io.sysArrAddControl := true.B
  io.accumulatorBiasAddress := channel
  io.accumulatorRegControl := true.B
  io.accumulatorAdderControl := false.B

  val kernelBaseAddress = channel * 20736.U + innerChannel * 81.U
  io.sysArrIn1 := (innerChannel * 81.U) + ((row) * 20.U) + (column)
  io.sysArrIn2 := (innerChannel * 81.U) + ((row + 1.U) * 20.U) + (column + 1.U)
  io.sysArrIn3 := (innerChannel * 81.U) + ((row + 2.U) * 20.U) + (column + 2.U)
  io.sysArrIn4 := (innerChannel * 81.U) + ((row + 3.U) * 20.U) + (column + 3.U)
  io.sysArrIn5 := (innerChannel * 81.U) + ((row + 4.U) * 20.U) + (column + 4.U)
  io.sysArrIn6 := (innerChannel * 81.U) + ((row + 5.U) * 20.U) + (column + 5.U)
  io.sysArrIn7 := (innerChannel * 81.U) + ((row + 6.U) * 20.U) + (column + 6.U)
  io.sysArrIn8 := (innerChannel * 81.U) + ((row + 7.U) * 20.U) + (column + 7.U)
  io.sysArrIn9 := (innerChannel * 81.U) + ((row + 8.U) * 20.U) + (column + 8.U)
  io.sysArrWeightCache1 := (kernelBaseAddress)
  io.sysArrWeightCache2 := (kernelBaseAddress + (1 * 9).U + 1.U)
  io.sysArrWeightCache3 := (kernelBaseAddress + (2 * 9).U + 2.U)
  io.sysArrWeightCache4 := (kernelBaseAddress + (3 * 9).U + 3.U)
  io.sysArrWeightCache5 := (kernelBaseAddress + (4 * 9).U + 4.U)
  io.sysArrWeightCache6 := (kernelBaseAddress + (5 * 9).U + 5.U)
  io.sysArrWeightCache7 := (kernelBaseAddress + (6 * 9).U + 6.U)
  io.sysArrWeightCache8 := (kernelBaseAddress + (7 * 9).U + 7.U)
  io.sysArrWeightCache9 := (kernelBaseAddress + (8 * 9).U + 8.U)

  when(
    channel === 255.U && innerChannel === 255.U &&
      row === (2 * 5).U && column === (2 * 5).U
  ) {
    io.primaryCapsReady := true.B
  }.elsewhen(
    innerChannel === 255.U && row === (2 * 5).U &&
      column === (2 * 5).U
  ) {
    channel := channel + 1.U
    innerChannel := 0.U
    row := 0.U
    column := 0.U
    io.accumulatorRegControl := false.B
    io.accumulatorAdderControl := true.B
  }.elsewhen(row === (2 * 5).U && column === (2 * 5).U) {
    io.storeInWeightCache := true.B
    io.weightMemAddress := 256.U * 81.U + channel * 256.U * 81.U
    innerChannel := innerChannel + 1.U
    row := 0.U
    column := 0.U
  }.elsewhen(column === (2 * 5).U) {
    row := row + 2.U
    column := 0.U
  }.otherwise {
    io.accumulatorIsBiasControl := true.B
    io.saveSquashInDataMem := true.B
    io.dataMemAddress := channel * 256.U * 20.U * 20.U + innerChannel * 20.U * 20.U + row * 20.U + column
    column := column + 2.U
  }
}
