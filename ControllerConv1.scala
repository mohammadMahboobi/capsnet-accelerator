import chisel3._
import chisel3.util.log2Ceil

class ControllerConv1 extends Module {
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
  val row = RegInit(0.U(log2Ceil(20).W))
  val column = RegInit(0.U(log2Ceil(20).W))

  io.isDataMemOrReuse := false.B
  io.sysArrIsWeightCache := true.B
  io.sysArrRegControl := true.B
  io.sysArrAddControl := true.B
  io.accumulatorBiasAddress := channel
  io.accumulatorRegControl := true.B
  io.accumulatorAdderControl := false.B
  io.storeInWeightCache := true.B
  io.weightMemAddress := 0.U

  io.sysArrIn1 := ((row) * 28.U) + (column)
  io.sysArrIn2 := ((row + 1.U) * 28.U) + (column + 1.U)
  io.sysArrIn3 := ((row + 2.U) * 28.U) + (column + 2.U)
  io.sysArrIn4 := ((row + 3.U) * 28.U) + (column + 3.U)
  io.sysArrIn5 := ((row + 4.U) * 28.U) + (column + 4.U)
  io.sysArrIn6 := ((row + 5.U) * 28.U) + (column + 5.U)
  io.sysArrIn7 := ((row + 6.U) * 28.U) + (column + 6.U)
  io.sysArrIn8 := ((row + 7.U) * 28.U) + (column + 7.U)
  io.sysArrIn9 := ((row + 8.U) * 28.U) + (column + 8.U)
  io.sysArrWeightCache1 := (channel * 81.U)
  io.sysArrWeightCache2 := (channel * 81.U + (1 * 9 + 1).U)
  io.sysArrWeightCache3 := (channel * 81.U + (2 * 9 + 2).U)
  io.sysArrWeightCache4 := (channel * 81.U + (3 * 9 + 3).U)
  io.sysArrWeightCache5 := (channel * 81.U + (4 * 9 + 4).U)
  io.sysArrWeightCache6 := (channel * 81.U + (5 * 9 + 5).U)
  io.sysArrWeightCache7 := (channel * 81.U + (6 * 9 + 6).U)
  io.sysArrWeightCache8 := (channel * 81.U + (7 * 9 + 7).U)
  io.sysArrWeightCache9 := (channel * 81.U + (8 * 9 + 8).U)

  when(
    channel === 255.U && row === 19.U && column === 19.U
  ) {
    io.conv1Ready := true.B
  }.elsewhen(row === 19.U && column === 19.U) {
    channel := channel + 1.U
    row := 0.U
    column := 0.U
    io.accumulatorRegControl := false.B
    io.accumulatorAdderControl := true.B
  }.elsewhen(column === 19.U) {
    row := row + 1.U
    column := 0.U
  }.otherwise {
    io.accumulatorIsBiasControl := true.B
    io.saveReluInDataMem := true.B
    io.dataMemAddress := channel * 20.U * 20.U + row * 20.U + column
    column := column + 1.U
  }
}
